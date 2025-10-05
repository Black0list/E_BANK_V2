package main.java.views;

import main.java.controllers.AuthController;
import main.java.entities.Client;
import main.java.utils.Actions;
import main.java.utils.Validation;

import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

public class TellerMenu extends BaseMenu {
    public TellerMenu(AuthController authController) {
        super(authController);
    }

    @Override
    protected void showRoleMenu() throws SQLException {
        System.out.println("1. Create Client");
        System.out.println("2. Create an Account");
        System.out.println("3. Deposit");
        System.out.println("4. Withdraw");
        System.out.println("5. Transfer Money");
        System.out.println("6. Request Credit");
        System.out.println("7. Logout");
        System.out.print("Select an Option : ");
        String choice = input.nextLine();
        switch (choice) {
            case "1" -> Actions.createClient();
            case "2" -> {
                System.out.print("Enter The ClientId You want to create the account For : ");
                String clientId = input.nextLine();
                Optional<UUID> inputReturned = Validation.safeParseUUID(clientId);
                if(inputReturned.isEmpty()){
                    System.out.println("Invalid ClientId");
                    return;
                }
                Optional<Client> client = Actions.clientController.findClientId(inputReturned.get());
                Actions.createBankAccount(client.get());
            }
            case "3" -> Actions.depositAbility();
            case "4" -> Actions.withdrawAbility();
            case "5" -> Actions.TransferMoney();
            case "6" -> Actions.RequestCredit();
            case "7" -> logout();
            default -> System.out.println("Invalid option");
        }
    }
}
