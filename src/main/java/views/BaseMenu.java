package main.java.views;

import main.java.application.Main;
import main.java.controllers.AuthController;
import main.java.entities.User;

import java.sql.SQLException;
import java.util.Optional;
import java.util.Scanner;

public abstract class BaseMenu {
    protected Scanner input = new Scanner(System.in);
    protected AuthController authController;

    public BaseMenu(AuthController authController) {
        this.authController = authController;
    }

    public void show() throws SQLException {
        while (true) {
            if (Main.USER.isEmpty()) {
                showLoginMenu();
            } else {
                showRoleMenu();
            }
        }
    }

    private void showLoginMenu() throws SQLException {
        System.out.println("1. Login");
        System.out.println("2. Exit");
        System.out.print("Select an Option : ");
        String choice = input.nextLine();
        switch (choice) {
            case "1" -> handleLogin();
            case "2" -> {
                System.out.println("Goodbye :-)");
                System.exit(0);
            }
            default -> System.out.println("Invalid option");
        }
    }

    private void handleLogin() throws SQLException {
        System.out.print("Email: ");
        String email = input.nextLine();
        System.out.print("Password: ");
        String password = input.nextLine();

        Optional<User> logged = authController.login(email, password);
        if (logged.isPresent()) {
            Main.USER = logged;
            System.out.println("✅ Logged as " + Main.USER.get().getName());
        } else {
            System.out.println("❌ Invalid Credentials");
        }
    }

    protected void logout() {
        Main.USER = Optional.empty();
        System.out.println("Logged out successfully.");
    }

    protected abstract void showRoleMenu() throws SQLException;
}
