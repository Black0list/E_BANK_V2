package main.java.views;

import main.java.controllers.AuthController;
import main.java.entities.Client;
import main.java.utils.Actions;
import main.java.utils.Validation;

import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

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
        System.out.println("7. Request Credit");
        System.out.println("8. Validate Credit");
        System.out.println("9. Deny Credit");
        System.out.println("10. List Credit requests");
        System.out.println("11. Export Data");
        System.out.print("Select an Option : ");
        String choice = input.nextLine();
        switch (choice) {
            case "1" -> Actions.createUserAbility();
            case "2" -> Actions.createClient();
            case "3" -> {
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
            case "4" -> Actions.depositAbility();
            case "5" -> Actions.withdrawAbility();
            case "6" -> Actions.TransferMoney();
            case "7" -> Actions.RequestCredit();
            case "8" -> Actions.validateCredit();
            case "9" -> Actions.denyCredit();
            case "10" -> Actions.ListCreditRequests();
            case "11" -> Actions.exportCsv();
            case "12" -> logout();
            default -> System.out.println("Invalid option");
        }
    }
}
