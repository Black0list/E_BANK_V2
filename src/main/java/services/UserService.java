package main.java.services;

import main.java.entities.User;
import main.java.entities.enums.Role;
import main.java.repositories.interfaces.UserRepositoryIntf;


import org.mindrot.jbcrypt.BCrypt;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class UserService {

    public UserRepositoryIntf userRepo;

    public UserService(UserRepositoryIntf userRepo){
        this.userRepo = userRepo;
    }

    public Optional<User> createUser(String name, String email, String password, Role role) throws SQLException {
        User user = new User(name, email, password, role);
        return this.userRepo.save(user);
    }

    public Optional<User> login(String email, String password) throws SQLException {
        Optional<User> user = this.userRepo.findByEmail(email);
        if(user.isPresent() && BCrypt.checkpw(password, user.get().getPassword())){
            return user;
        }

        return Optional.empty();
    }

    public Optional<User> findUserById(UUID id) throws SQLException {
        return userRepo.findById(id);
    }
}
