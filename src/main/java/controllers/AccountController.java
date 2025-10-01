package main.java.controllers;

import main.java.entities.Account;
import main.java.entities.Client;
import main.java.entities.enums.Accountype;
import main.java.entities.enums.Currency;
import main.java.services.AccountService;
import main.java.utils.Validation;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;

public class AccountController {

    private AccountService accountService;

    public AccountController(AccountService accountService){
        this.accountService = accountService;
    }

    public void createBankAccount(Accountype type, Currency currency, Client owner) throws SQLException {
        accountService.createBankAccount(type, currency, owner);
    }

    public Optional<Account> findAccountById(String accountId) throws SQLException {
        if(Objects.isNull(accountId)) {
            System.out.println("Please specify the account Id!");
            return Optional.empty();
        }

        Optional<Account> account = accountService.findAccountById(accountId);

        if(account.isPresent()){
            return account;
        } else {
            System.out.println("Account with Id ("+accountId+") not found");
            return Optional.empty();
        }
    }

    public void DepositMoney(Account account, BigDecimal amount) throws SQLException {
        if(Objects.isNull(account)){
            System.out.println("Account Not Found");
            return;
        }

        if(Objects.isNull(amount) || !Validation.isValidAmount(amount, 2)){
            System.out.println("Amount is not valid");
            return;
        }

        accountService.depositMoney(account, amount);
    }

    public void withdrawMoney(Account account,BigDecimal amount) throws SQLException {
        if(Objects.isNull(account)){
            System.out.println("Account Not Found");
            return;
        }

        if(Objects.isNull(amount) || !Validation.isValidAmount(amount, 2)){
            System.out.println("Amount is not valid");
            return;
        }

        accountService.withdrawMoney(account, amount);
    }
}
