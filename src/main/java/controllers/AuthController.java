package main.java.controllers;

import main.java.application.Main;
import main.java.entities.User;
import main.java.entities.enums.Role;
import main.java.services.UserService;

import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;

public class AuthController {

    public UserService userService;

    public AuthController(UserService userService){
        this.userService = userService;
    }

    public Optional<User> createUser(String name, String email, String password, Role role) throws SQLException {
        if(!Objects.equals(Main.USER.get().getRole(), Role.ADMIN)) {
            System.out.println("Only ADMIN can create users");
            return Optional.empty();
        }
        return userService.createUser(name,email,password,role);
    }

    public Optional<User> login(String email, String password) throws SQLException {
        Optional<User> user = this.userService.login(email,password);
        if(user.isPresent()){
            return this.userService.login(email,password);
        } else {
            return Optional.empty();
        }
    }
}
