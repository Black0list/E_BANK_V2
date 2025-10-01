package main.java.repositories;

import main.java.entities.*;
import main.java.entities.enums.TransactionStatus;
import main.java.entities.enums.TransactionType;
import main.java.repositories.interfaces.TransactionRepositoryIntf;
import main.java.utils.DbManager;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TransactionRepository implements TransactionRepositoryIntf {
    private final Connection connection;

    public TransactionRepository() {
        this.connection = DbManager.getInstance().getConnection();
    }

    // ✅ Insert or Update
    @Override
    public void save(Transaction tx) throws SQLException {
        String checkQuery = "SELECT id FROM transactions WHERE id = ?";
        try (PreparedStatement checkStmt = connection.prepareStatement(checkQuery)) {
            checkStmt.setObject(1, tx.getId());
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                String updateSql = """
                        UPDATE transactions
                        SET amount = ?, sender_id = ?, fee_rule_id = ?, status = ?, type = ?
                        WHERE id = ?
                        """;
                try (PreparedStatement stmt = connection.prepareStatement(updateSql)) {
                    stmt.setBigDecimal(1, tx.getAmount());
                    stmt.setObject(2, tx.getSender().getId());
                    if (tx.getFeeRule() != null) {
                        stmt.setObject(3, tx.getFeeRule().getId());
                    } else {
                        stmt.setNull(3, Types.OTHER);
                    }
                    stmt.setString(4, tx.getStatus().name());
                    stmt.setString(5, tx.getType().name());
                    stmt.setObject(6, tx.getId());
                    stmt.executeUpdate();
                }
            } else {
                String insertSql = """
                        INSERT INTO transactions (id, amount, sender_id, fee_rule_id, status, type)
                        VALUES (?, ?, ?, ?, ?, ?)
                        """;
                try (PreparedStatement stmt = connection.prepareStatement(insertSql)) {
                    stmt.setObject(1, tx.getId());
                    stmt.setBigDecimal(2, tx.getAmount());
                    stmt.setObject(3, tx.getSender().getId());
                    if (tx.getFeeRule() != null) {
                        stmt.setObject(4, tx.getFeeRule().getId());
                    } else {
                        stmt.setNull(4, Types.OTHER);
                    }
                    stmt.setString(5, tx.getStatus().name());
                    stmt.setString(6, tx.getType().name());
                    stmt.executeUpdate();
                }
            }
        }
    }

    // ✅ Find by ID
    @Override
    public Optional<Transaction> findById(UUID id) throws SQLException {
        String sql = "SELECT * FROM transactions WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setObject(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(mapToTransaction(rs));
            }
        }
        return Optional.empty();
    }

    // ✅ Find all
    @Override
    public List<Transaction> findAll() throws SQLException {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                transactions.add(mapToTransaction(rs));
            }
        }
        return transactions;
    }

    // ✅ Find by status
    @Override
    public List<Transaction> findByStatus(TransactionStatus status) throws SQLException {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions WHERE status = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, status.name());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                transactions.add(mapToTransaction(rs));
            }
        }
        return transactions;
    }

    // ✅ Delete
    @Override
    public void delete(UUID id) throws SQLException {
        String sql = "DELETE FROM transactions WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setObject(1, id);
            stmt.executeUpdate();
        }
    }

    // ✅ Helper: map DB → Transaction object
    private Transaction mapToTransaction(ResultSet rs) throws SQLException {
        UUID id = (UUID) rs.getObject("id");
        BigDecimal amount = rs.getBigDecimal("amount");

        Account sender = new Account(rs.getString("sender_id"));

        UUID feeRuleId = (UUID) rs.getObject("fee_rule_id");
        FeeRule feeRule = (feeRuleId != null) ? new FeeRule(feeRuleId) : null;

        TransactionStatus status = TransactionStatus.valueOf(rs.getString("status"));
        TransactionType type = TransactionType.valueOf(rs.getString("type"));

        return new Transaction(id, amount, sender, feeRule, status, type);
    }
}
