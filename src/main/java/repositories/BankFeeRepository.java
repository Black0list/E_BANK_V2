package main.java.repositories;

import main.java.entities.BankFee;
import main.java.repositories.interfaces.BankFeeRepositoryIntf;
import main.java.utils.DbManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BankFeeRepository implements BankFeeRepositoryIntf {

    private final Connection connection;

    public BankFeeRepository() {
        this.connection = DbManager.getInstance().getConnection();
    }

    public void save(BankFee bankFee) throws SQLException {
        String sql = "INSERT INTO bankfees (id, executor, sourceType, sourceId, amount, currency) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setObject(1, bankFee.getId());
            ps.setString(2, bankFee.getExecutor());
            ps.setString(3, bankFee.getSourceType().toString());
            ps.setObject(4, bankFee.getSourceId());
            ps.setBigDecimal(5, bankFee.getAmount());
            ps.setString(6, bankFee.getCurrency().toString());

            ps.executeUpdate();
        }
    }

}
