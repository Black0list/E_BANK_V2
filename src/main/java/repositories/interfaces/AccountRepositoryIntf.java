package main.java.repositories.interfaces;

import main.java.entities.Account;

import java.sql.SQLException;
import java.util.Optional;

public interface AccountRepositoryIntf {
    void save(Account account) throws SQLException;
    Optional<Account> findAccountById(String accountId) throws SQLException;
}
