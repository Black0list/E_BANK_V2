package main.java.views;

import main.java.controllers.AuthController;
import main.java.entities.Client;
import main.java.utils.Actions;

import java.sql.SQLException;
import java.util.Optional;

public class AdminMenu extends BaseMenu {

    public AdminMenu(AuthController authController) {
        super(authController);
    }

    @Override
    protected void showRoleMenu() throws SQLException {
        System.out.println("1. Create User System");
        System.out.println("2. Create Client");
        System.out.println("3. Create an Account");
        System.out.println("4. Deposit");
        System.out.println("5. Withdraw");
        System.out.println("6. Transfer Money");
        System.out.print("Select an Option : ");
        String choice = input.nextLine();
        switch (choice) {
            case "1" -> Actions.createUserAbility();
            case "2" -> {
                Optional<Client> client =  Actions.createClient();
                if(client.isPresent()){
                    Actions.createBankAccount(client.get());
                } else {
                    System.out.println("Client Not Found");
                }
            }
            case "3" -> Actions.createAccount();
            case "4" -> Actions.depositAbility();
            case "5" -> Actions.withdrawAbility();
            case "6" -> Actions.TransferMoney();
//            case "5" -> logout();
            default -> System.out.println("Invalid option");
        }
    }
}
