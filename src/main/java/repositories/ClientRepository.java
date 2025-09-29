package main.java.repositories;

import main.java.entities.Client;
import main.java.repositories.interfaces.ClientRepositoryIntf;
import main.java.utils.DbManager;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public class ClientRepository implements ClientRepositoryIntf {
    private final Connection connection;

    public ClientRepository() {
        this.connection = DbManager.getInstance().getConnection();
    }

    public void save(Client client) throws SQLException {
        String checkQuery = "SELECT id FROM clients WHERE id = ?";
        try (var CheckStatement = connection.prepareStatement(checkQuery)){
            CheckStatement.setObject(1, client.getId());
            var result = CheckStatement.executeQuery();

            if(result.next()){
                String updateSql = "UPDATE clients SET name = ?, email = ?, phone = ?, cin = ?, helper_id = ?";
                try(var updateStatement = connection.prepareStatement(updateSql)){
                    updateStatement.setObject(1, client.getName());
                    updateStatement.setObject(2, client.getEmail());
                    updateStatement.setObject(3, client.getPhone());
                    updateStatement.setObject(4, client.getCin());
                    updateStatement.setObject(5, client.getHelper().getId());
                    updateStatement.executeUpdate();
                }

            } else {
                String insertSql = "INSERT INTO clients (id, name, email, phone, cin, helper_id) VALUES (?, ?, ?, ?, ?, ?)";
                try (var stmt = connection.prepareStatement(insertSql)) {
                    stmt.setObject(1, client.getId());
                    stmt.setString(2, client.getName());
                    stmt.setString(3, client.getEmail());
                    stmt.setString(4, client.getPhone());
                    stmt.setString(5, client.getCin());
                    stmt.setObject(6, client.getHelper().getId());

                    stmt.executeUpdate();
                }
            }
        }
    }
}
