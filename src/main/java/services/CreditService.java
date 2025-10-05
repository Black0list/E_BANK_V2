package main.java.services;

import main.java.application.Main;
import main.java.entities.Account;
import main.java.entities.BankFee;
import main.java.entities.Credit;
import main.java.entities.FeeRule;
import main.java.entities.enums.*;
import main.java.repositories.CreditRepository;
import main.java.repositories.interfaces.BankFeeRepositoryIntf;
import main.java.repositories.interfaces.CreditRepositoryIntf;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class CreditService {
    private CreditRepositoryIntf creditRepo;
    private FeeRuleService feeService;
    private BankFeeRepositoryIntf bankFeeRepo;

    public CreditService(CreditRepositoryIntf creditRepo, FeeRuleService feeService, BankFeeRepositoryIntf bankFeeRepo) {
        this.creditRepo = creditRepo;
        this.feeService = feeService;
        this.bankFeeRepo = bankFeeRepo;
    }

    public void requestCredit(Account account, BigDecimal salary, BigDecimal credit, float duration) throws SQLException {
        Credit creditR = new Credit(credit, duration, salary, account);
        Optional<FeeRule> feeRule = feeService.findByType(OperationType.CREDIT);
        System.out.println(feeRule);
        creditR.setFeeRule(feeRule.get());

        if(!account.getType().equals(Accountype.CREDIT)){
            System.out.println("You cant request credit with "+account.getType()+" account");
            return;
        }

        Optional<Credit> founded = creditRepo.findActiveCreditByAccountId(account.getId());
        if(founded.isPresent()){
            System.out.println("You already have an active credit");
            creditR.setStatus(CreditStatus.FAILED);
            creditRepo.save(creditR);
            return;
        }

        BigDecimal InterestRate = BigDecimal.ZERO;
        if (feeRule.get().getMode().equals(Mode.FIX)) {
            InterestRate = feeRule.get().getFee();
        } else {
            InterestRate = feeRule.get().getFee().multiply(credit).divide(new BigDecimal(100));
        }

        BigDecimal validIncome = salary.multiply(new BigDecimal("0.40"));
        BigDecimal creditValid = credit.add(InterestRate).divide(BigDecimal.valueOf(duration), 2, RoundingMode.DOWN);
        creditR.setReduce(creditValid);
        System.out.println("Credit : "+creditValid);
        System.out.println("40% of income : "+validIncome);
        System.out.println("Interests : "+InterestRate);
        if(creditValid.compareTo(validIncome) >= 0){
            creditR.setStatus(CreditStatus.FAILED);
            creditRepo.save(creditR);
            System.out.println("You cant pay more than 40% of Your salary");
            return;
        }

        creditRepo.save(creditR);
    }

    public Optional<List<Credit>> getCreditRequests() throws SQLException {
        return Optional.of(creditRepo.findAllByStatus(CreditStatus.PENDING.name()));
    }

    public void validateCredit(UUID creditId) throws SQLException {
        creditRepo.validateCredit(creditId);
    }

    public void denyCredit(UUID creditId) throws SQLException {
        creditRepo.denyCredit(creditId);
    }

    public void updateAllCredits() throws SQLException {
        List<Credit> credits = creditRepo.findAllByStatus("ACTIVE");

        for (Credit c : credits) {
            BigDecimal income = c.getIncome();
            BigDecimal total = c.getTotal();
            BigDecimal reduce = c.getReduce();
            float duration = c.getDuration();

            total =  total.add(income);
            total = total.subtract(reduce);
            --duration;


            CreditStatus status = c.getStatus();
            if (total.compareTo(BigDecimal.ZERO) <= 0) {
                status = CreditStatus.LATE;
            }

            if(Objects.equals(duration, 0)){
                status = CreditStatus.CLOSED;
            }

            creditRepo.updateCreditBalanceAndStatus(c.getId(), total, duration, status);
            BankFee bankFee = new BankFee("System", OperationType.CREDIT, c.getId(), reduce, Currency.MAD);
            bankFeeRepo.save(bankFee);

            System.out.printf("Updated credit %s | Total: %s Reduce: %s | Status: %s%n", c.getId(), total, reduce, status);
        }
    }
}
