package main.java.utils;

import main.java.application.Main;
import main.java.controllers.AuthController;
import main.java.controllers.ClientController;
import main.java.entities.Client;
import main.java.entities.enums.Role;
import main.java.repositories.ClientRepository;
import main.java.repositories.UserRepository;
import main.java.services.ClientService;
import main.java.services.UserService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class Actions {

    private Connection connection;
    private static Scanner input = new Scanner(System.in);


    //      Repositories
    private static UserRepository userRepo = new UserRepository();
    private static ClientRepository clientRepo = new ClientRepository();
    //      Services
    private static UserService userService = new UserService(userRepo);
    private static ClientService clientService = new ClientService(clientRepo);
    //      Controllers
    private static AuthController authController = new AuthController(userService);
    private static ClientController clientController = new ClientController(clientService);

    public static void createUserAbility() throws SQLException {
        String line;
        int choice;
        if(!Validation.AuthCheck())
        {
            System.out.println("You're not authenticated");
            return;
        }
        System.out.println("=============================================");
        System.out.println("               Creating User System          ");
        System.out.println("=============================================");
        System.out.print("name : ");
        String name = input.nextLine();
        System.out.print("Email : ");
        String email = input.nextLine();
        System.out.print("Password : ");
        String password = input.nextLine();
        boolean running = true;
        do {
            Display.RoleMenu();
            line = input.nextLine();
            try {
                choice = Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("That wasn't a number.");
                continue;
            }
            switch (choice) {
                case 1 : {
                    authController.createUser(name, email, password, Role.TELLER);
                    running = false;
                    break;
                }

                case 2 : {
                    authController.createUser(name, email, password, Role.MANAGER);
                    running = false;
                    break;
                }

                case 3 : {
                    authController.createUser(name, email, password, Role.AUDITOR);
                    running = false;
                    break;
                }
            }

        }while (running);
        System.out.println("User System Created Successfully");
    }

    public static void createClient() throws SQLException{
        System.out.println("=============================================");
        System.out.println("               Creating Client               ");
        System.out.println("=============================================");
        System.out.print("name : ");
        String name = input.nextLine();
        System.out.print("Email : ");
        String email = input.nextLine();
        System.out.print("phone : ");
        String phone = input.nextLine();
        System.out.print("cin : ");
        String cin = input.nextLine();
        clientController.createClient(name , email , phone, cin , Main.USER.get());
    }
}
