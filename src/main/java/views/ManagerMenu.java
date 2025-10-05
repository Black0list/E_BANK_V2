package main.java.views;

import main.java.controllers.AuthController;
import main.java.entities.Client;
import main.java.utils.Actions;
import main.java.utils.Validation;

import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

public class ManagerMenu extends BaseMenu {

    public ManagerMenu(AuthController authController) {
        super(authController);
    }

    @Override
    protected void showRoleMenu() throws SQLException {
        System.out.println("1. List Credit requests");
        System.out.println("2. Validate a credit request");
        System.out.println("3. Deny a credit request");
        System.out.print("Select an Option : ");
        String choice = input.nextLine();
        switch (choice) {
            case "1" -> Actions.ListCreditRequests();
            case "2" -> Actions.validateCredit();
            case "3" -> Actions.denyCredit();
            default -> System.out.println("Invalid option");
        }
    }
}
