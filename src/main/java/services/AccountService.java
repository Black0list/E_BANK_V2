package main.java.services;

import main.java.entities.Account;
import main.java.entities.Client;
import main.java.entities.User;
import main.java.entities.enums.Accountype;
import main.java.entities.enums.Currency;
import main.java.repositories.interfaces.AccountRepositoryIntf;
import main.java.repositories.interfaces.ClientRepositoryIntf;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class AccountService {
    private AccountRepositoryIntf accountRepo;
    private ClientService clientService;

    public AccountService(AccountRepositoryIntf accountRepo, ClientService clientService){
        this.accountRepo = accountRepo;
        this.clientService = clientService;
    }

    public void createBankAccount(Accountype type, Currency currency, Client owner) throws SQLException {
        Account account = new Account(type, currency, owner);
        accountRepo.save(account);
    }

    public Optional<Account> findAccountById(String accountId) throws SQLException {
        Optional<Account> account = accountRepo.findAccountById(accountId);
        if(account.isPresent()){
            Optional<Client> owner = clientService.findClientById(account.get().getOwner().getId());
            account.get().setOwner(owner.get());
        }
        return account;
    }

    public void depositMoney(Account account, BigDecimal amount) throws SQLException {
        account.setBalance(account.getBalance().add(amount.setScale(2, RoundingMode.DOWN)));
        accountRepo.save(account);
    }

    public void withdrawMoney(Account account, BigDecimal amount) throws SQLException {
        if(account.getBalance().compareTo(amount.setScale(2, RoundingMode.DOWN)) >= 0){
            account.setBalance(account.getBalance().subtract(amount).setScale(2, RoundingMode.DOWN));
        } else {
            System.out.println("Client doesnt have enough money, has only : "+account.getBalance()+" "+account.getCurrency());
        }
        accountRepo.save(account);
    }

    public Optional<List<Account>> getClientAccounts(String clientId) throws SQLException {
        return accountRepo.getClientAccounts(clientId);
    }
}
