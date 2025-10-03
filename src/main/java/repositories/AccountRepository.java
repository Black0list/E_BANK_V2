package main.java.repositories;

import main.java.entities.Account;
import main.java.entities.Client;
import main.java.entities.User;
import main.java.entities.enums.Accountype;
import main.java.entities.enums.Currency;
import main.java.entities.enums.Role;
import main.java.repositories.interfaces.AccountRepositoryIntf;
import main.java.utils.DbManager;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.*;

public class AccountRepository implements AccountRepositoryIntf {
    private final Connection connection;

    public AccountRepository() {
        this.connection = DbManager.getInstance().getConnection();
    }

    public void save(Account account) throws SQLException {
        String checkQuery = "SELECT id FROM accounts WHERE id = ?";
        try (PreparedStatement checkStmt = connection.prepareStatement(checkQuery)) {
            checkStmt.setObject(1, account.getId());
            ResultSet result = checkStmt.executeQuery();

            if (result.next()) {
                String updateSql = """
                        UPDATE accounts 
                        SET type = ?, balance = ?, currency = ?, created_at = ?, is_active = ?, owner_id = ?, interest_rate = ? 
                        WHERE id = ?
                        """;
                try (PreparedStatement updateStmt = connection.prepareStatement(updateSql)) {
                    updateStmt.setString(1, account.getType().name());
                    updateStmt.setBigDecimal(2, account.getBalance());
                    updateStmt.setString(3, account.getCurrency().name());
                    updateStmt.setTimestamp(4, java.sql.Timestamp.from(account.getCreatedAt()));
                    updateStmt.setString(5, String.valueOf(account.getStatus()));
                    updateStmt.setObject(6, account.getOwner().getId());
                    updateStmt.setObject(7, account.getInterestRate());
                    updateStmt.setObject(8, account.getId());

                    updateStmt.executeUpdate();
                }
            } else {
                String insertSql = """
                        INSERT INTO accounts (id, type, balance, currency, created_at, is_active, interest_rate, owner_id)
                        VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                        """;
                try (PreparedStatement insertStmt = connection.prepareStatement(insertSql)) {
                    insertStmt.setObject(1, account.getId());
                    insertStmt.setString(2, account.getType().name());
                    insertStmt.setBigDecimal(3, account.getBalance());
                    insertStmt.setString(4, account.getCurrency().name());
                    insertStmt.setTimestamp(5, java.sql.Timestamp.from(account.getCreatedAt()));
                    insertStmt.setString(6, String.valueOf(account.getStatus()));
                    insertStmt.setBigDecimal(7, account.getInterestRate());
                    insertStmt.setObject(8, account.getOwner().getId());

                    insertStmt.executeUpdate();
                }
            }
        }
    }

    public Optional<Account> findAccountById(String accountId) throws SQLException {
        String findQuery = "SELECT * FROM accounts WHERE id = ?";

        try (PreparedStatement findStatement = connection.prepareStatement(findQuery)){
            findStatement.setObject(1, accountId);
            ResultSet result  = findStatement.executeQuery();

            if(result.next()){
                String id = accountId;
                BigDecimal balance = result.getBigDecimal("balance");
                Accountype type = Accountype.valueOf(result.getString("type"));
                Currency currency = Currency.valueOf(result.getString("currency"));
                Instant createdAt = result.getTimestamp("created_at").toInstant();
                boolean is_active = result.getBoolean("is_active");
                BigDecimal interest_rate = result.getBigDecimal("interest_rate");
                Client owner = new Client((UUID)result.getObject("owner_id"));
                return Optional.of(new Account(id, type, currency, balance, interest_rate, is_active, owner, createdAt));
            }
        }
        return Optional.empty();
    }

    public Optional<List<Account>> getClientAccounts(String clientId) throws SQLException {
        String sql = "SELECT * FROM accounts WHERE owner_id = ?::uuid";

        try (PreparedStatement accStatement = connection.prepareStatement(sql)) {
            accStatement.setObject(1, clientId);

            ResultSet result = accStatement.executeQuery();

            List<Account> accounts = new ArrayList<>();

            while (result.next()) {
                Account account = new Account(
                        result.getString("id"),
                        Accountype.valueOf(result.getString("type")),
                        Currency.valueOf(result.getString("currency")),
                        result.getBigDecimal("balance"),
                        result.getBigDecimal("interest_rate"),
                        Boolean.parseBoolean(result.getString("is_active")),
                        new Client((UUID)result.getObject("owner_id")),
                        result.getTimestamp("created_at").toInstant()
                );
                accounts.add(account);
            }

            if (accounts.isEmpty()) {
                return Optional.empty();
            } else {
                return Optional.of(accounts);
            }
        }
    }

}
