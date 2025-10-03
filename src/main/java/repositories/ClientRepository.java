package main.java.repositories;

import main.java.entities.Client;
import main.java.entities.User;
import main.java.entities.enums.Role;
import main.java.repositories.interfaces.ClientRepositoryIntf;
import main.java.utils.DbManager;
import main.java.utils.Validation;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

public class ClientRepository implements ClientRepositoryIntf {
    private final Connection connection;

    public ClientRepository() {
        this.connection = DbManager.getInstance().getConnection();
    }

    public Optional<Client> save(Client client) throws SQLException {
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
                    int updated = updateStatement.executeUpdate();
                    if(updated > 0){
                        return Optional.of(client);
                    }
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

                    int inserted = stmt.executeUpdate();
                    if(inserted > 0){
                        return Optional.of(client);
                    }
                }
            }
        }
        return Optional.empty();
    }

    public Optional<Client> findClientById(UUID clientId) throws SQLException{
        String findQuery = "SELECT * FROM clients WHERE id = ?";
        try (PreparedStatement findStatement = connection.prepareStatement(findQuery)) {
            findStatement.setObject(1, clientId);
            ResultSet result = findStatement.executeQuery();
            if (result.next()) {
                UUID id = (UUID) result.getObject("id");
                String name = result.getString("name");
                String email = result.getString("email");
                String phone = result.getString("phone");
                String cin = result.getString("cin");
                User user = new User((UUID)result.getObject("helper_id"));

                return Optional.of(new Client(id, name, email, phone, cin, user));
            }
        }
        return Optional.empty();
    }
}