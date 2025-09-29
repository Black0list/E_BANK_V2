package main.java.repositories.interfaces;

import main.java.entities.User;

import java.sql.SQLException;
import java.util.Optional;

public interface UserRepositoryIntf{
    Optional<User> save(User user) throws SQLException;
    Optional<User> findByEmail(String email) throws SQLException;
}
