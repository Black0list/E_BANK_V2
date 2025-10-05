package main.java.utils;

import main.java.application.Main;
import main.java.controllers.*;
import main.java.entities.Account;
import main.java.entities.Client;
import main.java.entities.Credit;
import main.java.entities.enums.Accountype;
import main.java.entities.enums.Currency;
import main.java.entities.enums.Role;
import main.java.repositories.*;
import main.java.repositories.interfaces.FeeRuleRepositoryIntf;
import main.java.services.*;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;

public class Actions {

    private static Scanner input = new Scanner(System.in);


    //      Repositories
    public static UserRepository userRepo = new UserRepository();
    public static ClientRepository clientRepo = new ClientRepository();
    public static AccountRepository accRepo = new AccountRepository();
    public static CreditRepository creditRepo = new CreditRepository();
    public static TransactionRepository transactRepo = new TransactionRepository();
    public static FeeRuleRepositoryIntf feeRepo = new FeeRuleRepository();
    public static BankFeeRepository bankFeeRepo = new BankFeeRepository();
    //      Services
    public static UserService userService = new UserService(userRepo);
    public static ClientService clientService = new ClientService(clientRepo, userService);
    public static AccountService accService = new AccountService(accRepo, clientService);
    public static FeeRuleService feeRuleService = new FeeRuleService(feeRepo);
    public static CreditService creditService = new CreditService(creditRepo, feeRuleService);
    public static TransactionService transactService = new TransactionService(transactRepo, accService, feeRuleService, bankFeeRepo);
    //      Controllers
    public static AuthController authController = new AuthController(userService);
    public static ClientController clientController = new ClientController(clientService);
    public static AccountController accountController = new AccountController(accService);
    public static TransactionController transactController = new TransactionController(transactService);
    public static CreditController creditController = new CreditController(creditService);

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

    public static void createClient() throws SQLException {
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
        clientController.createClient(name, email, phone, cin, Main.USER.get());
    }

    public static void createBankAccount(Client client) throws SQLException {
        while (true) {
            System.out.println("Account Types : ");
            for (Accountype t : Accountype.values()) {
                System.out.println("- " + t);
            }
            System.out.print("Choose an account type : ");
            String type = input.nextLine().toUpperCase();
            Optional<List<Account>> accounts = accountController.getClientAccounts(client.getId().toString());

            if (accounts.isPresent() &&
                    accounts.get().stream().map(el -> el.getType().toString().toUpperCase()).anyMatch(el -> el.equals(type))) {

                System.out.println("Client already has a " + type + " account");
                return;
            }


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

    public static void TransferMoney() throws SQLException {
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

    public static void RequestCredit() throws SQLException {
        System.out.print("Enter The accountId You want to request Credit from : ");
        String accountId = input.nextLine();
        Optional<Account> account = accountController.findAccountById(accountId);
        if(account.isEmpty()){
            return;
        }
        System.out.print("Enter the Monthly Salary : ");
        BigDecimal salary = input.nextBigDecimal();
        input.nextLine();
        System.out.print("Enter the Credit amount requested : ");
        BigDecimal credit = input.nextBigDecimal();
        input.nextLine();
        System.out.print("Enter the number of months : ");
        float duration = input.nextFloat();
        input.nextLine();
        creditController.requestCredit(account.get(), salary, credit, duration);
    }

    public static void ListCreditRequests() throws SQLException {
        Optional<List<Credit>> requests =  creditController.displayCreditReq();
        if (requests.isEmpty()) {
            System.out.println("There is no requests for The moment");
            return;
        }

        System.out.println("Here is the list of requests : ");
        requests.get().forEach(System.out::println);
    }

    public static void validateCredit(){
        try {
            System.out.print("Credit Id that you want to validate : ");
            UUID creditId = UUID.fromString(input.nextLine());

            creditController.validateCredit(creditId);
        } catch (Exception e){
            System.out.println("Enter a valid Credit Id");
        }
    }

    public static void denyCredit(){
        try {
            System.out.print("Credit Id that you want to deny : ");
            UUID creditId = UUID.fromString(input.nextLine());

            creditController.denyCredit(creditId);
        } catch (Exception e){
            System.out.println("Enter a valid Credit Id");
        }
    }
}
