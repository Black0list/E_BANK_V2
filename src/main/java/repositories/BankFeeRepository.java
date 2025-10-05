package main.java.repositories;

import main.java.entities.BankFee;
import main.java.entities.enums.Currency;
import main.java.entities.enums.OperationType;
import main.java.repositories.interfaces.BankFeeRepositoryIntf;
import main.java.utils.DbManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.util.UUID;

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

    public void exportCreditsToCsv(String filePath) throws SQLException, IOException {
        String sql = """
        SELECT id, executor, sourcetype, sourceid, amount, currency, created_at
        FROM bankfees
    """;

        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery();
             FileWriter writer = new FileWriter(filePath)) {

            // Write CSV header
            writer.append("id,executor,sourceType,SourceId(Credit/Transaction),amount,currency,date\n");

            // Write each row
            while (rs.next()) {
                UUID id = UUID.fromString(rs.getString("id"));
                String executor = rs.getString("executor");
                OperationType sourceType = OperationType.valueOf(rs.getString("sourcetype"));
                UUID sourceId = (UUID)rs.getObject("sourceid");
                BigDecimal amount = rs.getBigDecimal("amount");
                Currency currency = Currency.valueOf(rs.getString("currency"));
                Timestamp date = (Timestamp) rs.getObject("created_at");

                writer.append(String.format(
                        "%s,%s,%s,%s,%s,%s,%s\n",
                        id,
                        executor,
                        amount,
                        sourceType,
                        sourceId,
                        amount,
                        date
                ));
            }

            System.out.println("successfully exported to: " + filePath);
        }
    }

}
