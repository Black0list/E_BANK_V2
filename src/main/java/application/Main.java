package main.java.application;

import main.java.controllers.AuthController;
import main.java.entities.User;
import main.java.entities.enums.Role;
import main.java.repositories.UserRepository;
import main.java.services.UserService;
import main.java.utils.DbManager;
import main.java.views.AdminMenu;
import main.java.views.BaseMenu;
import main.java.views.ManagerMenu;
import main.java.views.TellerMenu;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public class Main {
    public static Optional<User> USER = Optional.empty();
    private Connection connection;

    public static void main(String[] args) throws SQLException {
        DbManager.getInstance().initDb();
        UserRepository userRepo = new UserRepository();
        UserService userService = new UserService(userRepo);
        AuthController authController = new AuthController(userService);

        BaseMenu menu = new BaseMenu(authController) {
            @Override
            protected void showRoleMenu() throws SQLException {
                if (USER.isEmpty()) return;
                Role role = USER.get().getRole();
                switch (role) {
                    case ADMIN -> new AdminMenu(authController).show();
                    case TELLER -> new TellerMenu(authController).show();
                    default -> System.out.println("Role not implemented yet");
                }
            }
        };

        menu.show();
    }
}
