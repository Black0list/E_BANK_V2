package main.java.utils;

import main.java.application.Main;
import main.java.controllers.AccountController;
import main.java.controllers.AuthController;
import main.java.controllers.ClientController;
import main.java.controllers.TransactionController;
import main.java.entities.Account;
import main.java.entities.Client;
import main.java.entities.enums.Accountype;
import main.java.entities.enums.Currency;
import main.java.entities.enums.Role;
import main.java.repositories.*;
import main.java.repositories.interfaces.FeeRuleRepositoryIntf;
import main.java.services.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Optional;
import java.util.Scanner;

public class Actions {

    private static Scanner input = new Scanner(System.in);


    //      Repositories
    private static UserRepository userRepo = new UserRepository();
    private static ClientRepository clientRepo = new ClientRepository();
    private static AccountRepository accRepo = new AccountRepository();
    private static CreditRepository creditRepo = new CreditRepository();
    private static TransactionRepository transactRepo = new TransactionRepository();
    private static FeeRuleRepositoryIntf feeRepo = new FeeRuleRepository();
    //      Services
    private static UserService userService = new UserService(userRepo);
    private static ClientService clientService = new ClientService(clientRepo, userService);
    private static AccountService accService = new AccountService(accRepo, clientService);
    private static CreditService creditService = new CreditService();
    private static FeeRuleService feeRuleService = new FeeRuleService(feeRepo);
    private static TransactionService transactService = new TransactionService(transactRepo, accService, feeRuleService);
    //      Controllers
    private static AuthController authController = new AuthController(userService);
    private static ClientController clientController = new ClientController(clientService);
    private static AccountController accountController = new AccountController(accService);
    private static TransactionController transactController = new TransactionController(transactService);

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

    public static Optional<Client> createClient() throws SQLException {
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
        return clientController.createClient(name , email , phone, cin , Main.USER.get());
    }

    public static void createBankAccount(Client client) throws SQLException {

        while (true) {
            System.out.println("Account Types : ");
            for (Accountype t : Accountype.values()) {
                System.out.println("- " + t);
            }
            System.out.print("Choose an account type : ");
            String type = input.nextLine().toUpperCase();



            try {
                Accountype selected = Accountype.valueOf(type);
                switch (selected) {
                    case SAVINGS:
                        accountController.createBankAccount(Accountype.SAVINGS, Currency.MAD, client);
                        break;
                    case COURANT:
                        accountController.createBankAccount(Accountype.COURANT, Currency.MAD, client);
                        break;
                    case CREDIT:
                        accountController.createBankAccount(Accountype.CREDIT, Currency.MAD, client);
                        break;
                }
                System.out.println("Account Created for Client : "+client.getName());
                break;
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid option, try again.");
            }
        }
    }

    public static void depositAbility() throws SQLException {
        System.out.print("Enter the Bank Account ID You want to deposit to : ");
        String accountId = input.nextLine();
        Optional<Account> account = accountController.findAccountById(accountId);
        if(account.isPresent()){
            System.out.print("Enter Amount You want to Deposit : ");
            BigDecimal amount  = input.nextBigDecimal();
            input.nextLine();
            accountController.DepositMoney(account.get(), amount);
        }
    }

    public static void withdrawAbility() throws SQLException {
        System.out.print("Enter the Bank Account ID You want to withdraw from : ");
        String accountId = input.nextLine();
        Optional<Account> account = accountController.findAccountById(accountId);
        if(account.isPresent()){
            System.out.print("Enter Amount You want to Withdraw : ");
            BigDecimal amount  = input.nextBigDecimal();
            input.nextLine();
            accountController.withdrawMoney(account.get(), amount);
        }
    }

    public static void TransferMoney() throws SQLException{
        System.out.print("Enter the accountId you want to transfer Money From : ");
        String senderId = input.nextLine();
        Optional<Account> sender = accountController.findAccountById(senderId);
        if(sender.isEmpty()) return;
        System.out.print("Enter The receiver's accountId : ");
        String receiver = input.nextLine();
        System.out.print("Enter the amount you want to send : ");
        BigDecimal amount = input.nextBigDecimal();
        input.nextLine();
        transactController.Transfer(amount, sender.get().getId(), receiver);
    }

    public static void createAccount() {
        System.out.print("Enter The ClientId You want to create the account For : ");
        String clientId = input.nextLine();

    }
}
