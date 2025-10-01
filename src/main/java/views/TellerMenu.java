package main.java.views;

import main.java.controllers.AuthController;

public class TellerMenu extends BaseMenu {
    public TellerMenu(AuthController authController) {
        super(authController);
    }

    @Override
    protected void showRoleMenu() {
        System.out.println("1. Deposit");
        System.out.println("2. Withdraw");
        System.out.println("3. Logout");

        String choice = input.nextLine();
        switch (choice) {
            case "1" -> System.out.println("Teller → Deposit");
            case "2" -> System.out.println("Teller → Withdraw");
            case "3" -> logout();
            default -> System.out.println("Invalid option");
        }
    }
}
