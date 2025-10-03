package main.java.repositories.interfaces;

import main.java.entities.Account;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AccountRepositoryIntf {
    void save(Account account) throws SQLException;
    Optional<Account> findAccountById(String accountId) throws SQLException;
    Optional<List<Account>> getClientAccounts(String clientId) throws SQLException;
}
