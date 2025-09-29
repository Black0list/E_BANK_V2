package main.java.application;

import main.java.controllers.AuthController;
import main.java.entities.User;
import main.java.entities.enums.Role;
import main.java.repositories.UserRepository;
import main.java.services.UserService;
import main.java.utils.DbManager;
import main.java.utils.Display;
import main.java.utils.Validation;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

public class Main {
    public static Optional<User> USER = Optional.empty();
    private Connection connection;

    public static void main(String[] args) throws SQLException {
        DbManager.getInstance().initDb();
//      Controllers
        UserRepository userRepo = new UserRepository();
        UserService userService = new UserService(userRepo);
        AuthController authController = new AuthController(userService);

        int choice = 100;
        Scanner input  = new Scanner(System.in);
        while(choice != 10) {
            Display.displayMenuForRole(USER);
            String line = input.nextLine();
            try {
                choice = Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("That wasn't a number.");
                continue;
            }

            switch (choice) {
                case 1 : {
                    Display.clear();
                    System.out.println("=============================================");
                    System.out.println("                    Login                    ");
                    System.out.println("=============================================");
                    System.out.print("email : ");
                    String email = input.nextLine();
                    System.out.print("Password : ");
                    String password = input.nextLine();
                    if(authController.login(email,password).isPresent()){
                        USER = authController.login(email,password);
                        Display.showSuccessMessage("Logged as "+USER.get().getName());
                    } else {
                        System.out.println("Invalid Credentials");
                    }
                    break;
                }

                case 2 : {
                    System.out.println("==========================================");
                    System.out.println("                Good Bye :-)              ");
                    System.out.println("==========================================");
                    return;
                }
            }
        }
    }
}
