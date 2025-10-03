package main.java.repositories;

import main.java.entities.FeeRule;
import main.java.entities.enums.Currency;
import main.java.entities.enums.Mode;
import main.java.entities.enums.OperationType;
import main.java.entities.enums.TransactionType;
import main.java.repositories.interfaces.FeeRuleRepositoryIntf;
import main.java.utils.DbManager;

import java.math.BigDecimal;
import java.sql.*;
import java.util.Optional;
import java.util.UUID;

public class FeeRuleRepository implements FeeRuleRepositoryIntf {
    private final Connection connection;

    public FeeRuleRepository() {
        this.connection = DbManager.getInstance().getConnection();
    }

    public void save(FeeRule rule) throws SQLException {
        String checkQuery = "SELECT id FROM fee_rules WHERE id = ?";
        try (PreparedStatement checkStmt = connection.prepareStatement(checkQuery)) {
            checkStmt.setObject(1, rule.getId());
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                String updateSql = """
                        UPDATE fee_rules
                        SET mode = ?, operation_type = ?, currency = ?, fee = ?, is_active = ?
                        WHERE id = ?
                        """;
                try (PreparedStatement updateStmt = connection.prepareStatement(updateSql)) {
                    updateStmt.setString(1, rule.getMode().name());
                    updateStmt.setString(2, rule.getOperationType().name());
                    updateStmt.setString(3, rule.getCurrency().name());
                    updateStmt.setBigDecimal(4, rule.getFee());
                    updateStmt.setBoolean(5, rule.getIs_active());
                    updateStmt.setObject(6, rule.getId());
                    updateStmt.executeUpdate();
                }
            } else {
                String insertSql = """
                        INSERT INTO fee_rules (id, mode, operation_type, currency, fee, is_active)
                        VALUES (?, ?, ?, ?, ?, ?)
                        """;
                try (PreparedStatement insertStmt = connection.prepareStatement(insertSql)) {
                    insertStmt.setObject(1, rule.getId());
                    insertStmt.setString(2, rule.getMode().name());
                    insertStmt.setString(3, rule.getOperationType().name());
                    insertStmt.setString(4, rule.getCurrency().name());
                    insertStmt.setBigDecimal(5, rule.getFee());
                    insertStmt.setBoolean(6, rule.getIs_active());
                    insertStmt.executeUpdate();
                }
            }
        }
    }

    public Optional<FeeRule> findById(UUID id) throws SQLException {
        String findQuery = "SELECT * FROM fee_rules WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(findQuery)) {
            stmt.setObject(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(mapToFeeRule(rs));
            }
        }
        return Optional.empty();
    }

    public void delete(UUID id) throws SQLException {
        String sql = "DELETE FROM fee_rules WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setObject(1, id);
            stmt.executeUpdate();
        }
    }

    private FeeRule mapToFeeRule(ResultSet rs) throws SQLException {
        UUID id = (UUID) rs.getObject("id");
        Mode mode = Mode.valueOf(rs.getString("mode"));
        OperationType operationType = OperationType.valueOf(rs.getString("operation_type"));
        Currency currency = Currency.valueOf(rs.getString("currency"));
        BigDecimal fee = rs.getBigDecimal("fee");
        boolean isActive = rs.getBoolean("is_active");
        System.out.println("id : "+id);
        return new FeeRule(id, mode, operationType, currency, fee, isActive);
    }

    public Optional<FeeRule> findActiveRuleByOperationType(OperationType type) throws SQLException {
        String sql = "SELECT * FROM fee_rules WHERE operation_type = ? AND is_active = TRUE LIMIT 1";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, type.name());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(mapToFeeRule(rs));
            }
        }
        return Optional.empty();
    }

}
