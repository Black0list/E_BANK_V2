package main.java.repositories;
import main.java.entities.User;
import main.java.entities.enums.Role;
import main.java.repositories.interfaces.*;
import main.java.utils.DbManager;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

public class UserRepository implements UserRepositoryIntf{
    private final Connection connection;

    public UserRepository() {
        this.connection = DbManager.getInstance().getConnection();
    }

    public Optional<User> save(User user) throws SQLException {
        String checkQuery = "SELECT id FROM users WHERE id = ?";
        try (var CheckStatement = connection.prepareStatement(checkQuery)){
            CheckStatement.setObject(1, user.getId());
            var result = CheckStatement.executeQuery();

            if(result.next()){
                String updateSql = "UPDATE users SET name = ?, email = ?, password = ?, role = ?";
                try(var updateStatement = connection.prepareStatement(updateSql)){
                    updateStatement.setObject(1, user.getName());
                    updateStatement.setObject(2, user.getEmail());
                    updateStatement.setObject(3, BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
                    updateStatement.setObject(4, user.getRole().name());

                    int updated = updateStatement.executeUpdate();
                    if(updated > 0){
                        return Optional.of(user);
                    }
                }

            } else {
                String insertSql = "INSERT INTO users (id, name, email, password, role) VALUES (?, ?, ?, ?, ?)";
                try (var stmt = connection.prepareStatement(insertSql)) {
                    stmt.setObject(1, user.getId());
                    stmt.setString(2, user.getName());
                    stmt.setString(3, user.getEmail());
                    stmt.setString(4, BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
                    stmt.setString(5, user.getRole().name());

                    int inserted = stmt.executeUpdate();
                    if(inserted > 0){
                        return Optional.of(user);
                    }
                }
            }
        }
        return Optional.empty();
    }

    public Optional<User> findByEmail(String email) throws SQLException {
        String findQuery = "SELECT * FROM users WHERE email = ?";
        try (PreparedStatement findStatement = connection.prepareStatement(findQuery)) {
            findStatement.setString(1, email);
            ResultSet result = findStatement.executeQuery();
            if (result.next()) {
                UUID id = (UUID) result.getObject("id");
                String name = result.getString("name");
                String emailed = result.getString("email");
                String password = result.getString("password");
                String role = result.getString("role").toUpperCase();
                switch (role) {
                    case "TELLER":
                        return Optional.of(new User(id, name, emailed, password, Role.TELLER));
                    case "MANAGER":
                        return Optional.of(new User(id, name, emailed, password, Role.MANAGER));
                    case "AUDITOR":
                        return Optional.of(new User(id, name, emailed, password, Role.AUDITOR));
                    case "ADMIN":
                        return Optional.of(new User(id, name, emailed, password, Role.ADMIN));
                    default:
                        throw new IllegalArgumentException("Unknown role: " + role);
                }
            }
        }
        return Optional.empty();
    }

    public Optional<User> findById(UUID id) throws SQLException {
        String findQuery = "SELECT * FROM users WHERE id = ?";
        try (PreparedStatement findStatement = connection.prepareStatement(findQuery)) {
            findStatement.setObject(1, id);
            ResultSet result = findStatement.executeQuery();

            if (result.next()) {
                UUID userId = (UUID) result.getObject("id");
                String name = result.getString("name");
                String email = result.getString("email");
                String password = result.getString("password");
                String role = result.getString("role").toUpperCase();

                switch (role) {
                    case "TELLER":
                        return Optional.of(new User(userId, name, email, password, Role.TELLER));
                    case "MANAGER":
                        return Optional.of(new User(userId, name, email, password, Role.MANAGER));
                    case "AUDITOR":
                        return Optional.of(new User(userId, name, email, password, Role.AUDITOR));
                    case "ADMIN":
                        return Optional.of(new User(userId, name, email, password, Role.ADMIN));
                    default:
                        throw new IllegalArgumentException("Unknown role: " + role);
                }
            }
        }
        return Optional.empty();
    }


}
