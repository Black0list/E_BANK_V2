package main.java.services;

import main.java.entities.Account;
import main.java.entities.FeeRule;
import main.java.entities.Transaction;
import main.java.entities.enums.Accountype;
import main.java.entities.enums.Mode;
import main.java.entities.enums.TransactionStatus;
import main.java.entities.enums.TransactionType;
import main.java.repositories.TransactionRepository;
import main.java.repositories.interfaces.AccountRepositoryIntf;
import main.java.repositories.interfaces.TransactionRepositoryIntf;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;

public class TransactionService {

    private TransactionRepositoryIntf transactionRepo;
    private AccountService accountService;
    private FeeRuleService feeService;

    public TransactionService(TransactionRepositoryIntf transactionRepo, AccountService accountService,FeeRuleService feeService){
        this.transactionRepo = transactionRepo;
        this.accountService = accountService;
        this.feeService = feeService;
    }

    public void recordTransfer(String senderId, String receiverId, BigDecimal amount) throws SQLException {
        Optional<Account> sender = accountService.findAccountById(senderId);
        Optional<Account> receiver = accountService.findAccountById(receiverId);

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println("Invalid Amount");
            return;
        }

        if (sender.isPresent() && !sender.get().getStatus()) {
            System.out.println("The account is not active");
            return;
        }

        if (sender.isPresent() && sender.get().getBalance().compareTo(amount) < 0) {
            System.out.println("Insufficient Funds, Account has Only : " + sender.get().getBalance()+ " " + sender.get().getCurrency());
            return;
        }

        if (receiver.isPresent() && !receiver.get().getStatus()){
            System.out.println("Receiver's account is not active");
            return;
        }

        if (receiver.isPresent()) {
            if(receiver.get().getType().equals(Accountype.CREDIT) || sender.get().getType().equals(Accountype.CREDIT)){
                System.out.println("Cant send/receive Money to/from Credit Account");
                return;
            }

            if (sender.get().getType().equals(Accountype.SAVINGS) && receiver.get().getType().equals(Accountype.COURANT)) {
                if(Objects.equals(sender.get().getOwner().getId(), receiver.get().getOwner().getId())){
                    saveTransaction(amount, sender.get(), TransactionType.TRANSFER_OUT);
                    saveTransaction(amount, receiver.get(), TransactionType.TRANSFER_IN);
                    return;
                } else {
                    System.out.println("In case of savings account , Must have Both accounts");
                    return;
                }
            }

            saveTransaction(amount, sender.get(), TransactionType.TRANSFER_OUT);
            saveTransaction(amount, receiver.get(), TransactionType.TRANSFER_IN);

        } else {
            BigDecimal amounted = applyFee(sender.get(), amount, TransactionType.TRANSFER_EXTERNAL);
            if(sender.get().getBalance().compareTo(amounted) >= 0){
                saveTransaction(amounted, sender.get(), TransactionType.TRANSFER_EXTERNAL);
            } else {
                System.out.println("You dont Money to pay Fees");
            }
        }

    }

    public void saveTransaction(BigDecimal amount, Account account, TransactionType type) throws SQLException {
        Transaction transaction = new Transaction(amount, account, type);
        switch (type){
            case DEPOSIT, TRANSFER_IN -> accountService.depositMoney(account, amount);
            case WITHDRAW, TRANSFER_OUT, TRANSFER_EXTERNAL -> accountService.withdrawMoney(account, amount);
        }
        transactionRepo.save(transaction);
    }

    public BigDecimal applyFee(Account account, BigDecimal amount, TransactionType type) throws SQLException {
        Optional<FeeRule> feeRule = feeService.findByType(type);
        if(feeRule.isPresent()){
            if(feeRule.get().getMode().equals(Mode.FIX)){
                return amount.add(feeRule.get().getFee());
            } else {
                return amount.multiply(feeRule.get().getFee()).divide(new BigDecimal(100)).setScale(2, RoundingMode.DOWN).add(amount);
            }
        }
        return amount;
    }


    public void history(Account account){

    }
}
