package main.java.views;

import main.java.controllers.AuthController;
import main.java.utils.Actions;

import java.sql.SQLException;

public class AuditorMenu extends BaseMenu{
    public AuditorMenu(AuthController authController) {
        super(authController);
    }

    @Override
    protected void showRoleMenu() throws SQLException {
        System.out.println("1. ExportCsv");
        System.out.println("2. logout");
        System.out.print("Select an Option : ");
        String choice = input.nextLine();
        switch (choice) {
            case "1" -> Actions.exportCsv();
            case "2" -> logout();
        }
    }
}
