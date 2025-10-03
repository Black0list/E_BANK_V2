package main.java.repositories;

import main.java.entities.Account;
import main.java.entities.Credit;
import main.java.entities.FeeRule;
import main.java.entities.enums.CreditStatus;
import main.java.repositories.interfaces.CreditRepositoryIntf;
import main.java.utils.DbManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

public class CreditRepository implements CreditRepositoryIntf {

    private final Connection connection;

    public CreditRepository(){
        this.connection = DbManager.getInstance().getConnection();
    }

    public void save(Credit credit) throws SQLException {
        String sql = """
        INSERT INTO credits (id, total, amount, income, duration, fee_rule_id, account_id, status, reduce)
        VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
        ON CONFLICT (id) DO UPDATE SET
            total = EXCLUDED.total,
            amount = EXCLUDED.amount,
            income = EXCLUDED.income,
            duration = EXCLUDED.duration,
            fee_rule_id = EXCLUDED.fee_rule_id,
            account_id = EXCLUDED.account_id,
            status = EXCLUDED.status,
            reduce = EXCLUDED.reduce
    """;

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setObject(1, credit.getId());
            ps.setBigDecimal(2, credit.getTotal());
            ps.setBigDecimal(3, credit.getAmount());
            ps.setBigDecimal(4, credit.getIncome());
            ps.setDouble(5, credit.getDuration());
            if (credit.getFeeRule() != null) {
                ps.setObject(6, credit.getFeeRule().getId());
            } else {
                ps.setNull(6, java.sql.Types.OTHER);
            }
            ps.setString(7, credit.getAccount().getId());
            ps.setString(8, credit.getStatus().toString());
            ps.setBigDecimal(9, credit.getReduce());

            ps.executeUpdate();
        }
    }

    public Optional<Credit> findActiveCreditByAccountId(String accountId) throws SQLException {
        String sql = "SELECT * FROM credits WHERE account_id = ? AND status = 'ACTIVE' LIMIT 1";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, accountId);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Credit credit = new Credit(
                        (UUID) rs.getObject("id"),
                        rs.getBigDecimal("amount"),
                        rs.getBigDecimal("total"),
                        rs.getBigDecimal("income"),
                        (float)rs.getDouble("duration"),
                        rs.getObject("fee_rule_id") != null ? new FeeRule((UUID) rs.getObject("fee_rule_id")) : null,
                        CreditStatus.valueOf(rs.getString("status")),
                        rs.getString("account_id") != null ? new Account(rs.getString("account_id")) : null,
                        rs.getBigDecimal("reduce")
                );
                return Optional.of(credit);
            }
        }
        return Optional.empty();
    }


}
