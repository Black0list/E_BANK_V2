package main.java.utils;

import main.java.entities.User;

import java.sql.SQLException;
import java.util.Optional;
import java.util.Scanner;

public class Display {

    // Menu principal (non connecté)
    public static void menu(){
        Display.clear();
        System.out.println("================ BANKING SYSTEM ================");
        System.out.println("1. Login");
        System.out.println("2. Exit");
        System.out.println("========================================");
        System.out.print("Select option: ");
    }

    // Menu TELLER - Opérations courantes
    public static void tellerMenu(String userName){
        Display.clear();
        System.out.println("================ BANKING SYSTEM - TELLER ================");
        System.out.println("Logged in as: " + userName + " - TELLER");
        System.out.println();

        System.out.println("💳 COMPTES:");
        System.out.println("  1. Create Account (for clients)");
        System.out.println("  2. List My Managed Accounts");
        System.out.println("  3. Update Client Profile");
        System.out.println();

        System.out.println("💰 TRANSACTIONS:");
        System.out.println("  4. Deposit");
        System.out.println("  5. Withdraw");
        System.out.println("  6. Internal Transfer");
        System.out.println("  7. Transaction History");
        System.out.println();

        System.out.println("💳 CREDITS:");
        System.out.println("  8. Submit Credit Request");
        System.out.println("  9. Credit Follow-up");
        System.out.println();

        System.out.println("💸 FEES:");
        System.out.println("  10. List Fee Rules");
        System.out.println();

        System.out.println("🔐 ACCOUNT:");
        System.out.println("  11. Change Password");
        System.out.println("  12. Logout");
        System.out.println("  0. Exit");
        System.out.println("========================================");
        System.out.print("Select option: ");
    }

    // Menu MANAGER - Validation + supervision
    public static void managerMenu(String userName){
        Display.clear();
        System.out.println("================ BANKING SYSTEM - MANAGER ================");
        System.out.println("Logged in as: " + userName + " - MANAGER");
        System.out.println();

        System.out.println("💳 COMPTES:");
        System.out.println("  1. Create Account (for clients)");
        System.out.println("  2. List All Accounts");
        System.out.println("  3. Update Client Profile");
        System.out.println("  4. Close Account");
        System.out.println();

        System.out.println("💰 TRANSACTIONS:");
        System.out.println("  5. Deposit");
        System.out.println("  6. Withdraw");
        System.out.println("  7. Internal Transfer");
        System.out.println("  8. External Transfer");
        System.out.println("  9. Transaction History");
        System.out.println();

        System.out.println("💳 CREDITS:");
        System.out.println("  10. Process Credit Request");
        System.out.println("  11. Credit Follow-up");
        System.out.println("  12. Credit Repayment");
        System.out.println("  13. Approve/Reject Credits");
        System.out.println();

        System.out.println("💸 FEES:");
        System.out.println("  14. List Fee Rules");
        System.out.println("  15. Update Fee Rule");
        System.out.println();

        System.out.println("📊 REPORTS:");
        System.out.println("  16. View Statistics");
        System.out.println("  17. Export Report");
        System.out.println("  18. Fees Revenue");
        System.out.println();

        System.out.println("🔐 ACCOUNT:");
        System.out.println("  19. Change Password");
        System.out.println("  20. Logout");
        System.out.println("  0. Exit");
        System.out.println("========================================");
        System.out.print("Select option: ");
    }

    // Menu ADMIN - Tous les droits
    public static void adminMenu(String userName){
        Display.clear();
        System.out.println("================ BANKING SYSTEM - ADMIN ================");
        System.out.println("Logged in as: " + userName + " - ADMIN");
        System.out.println();

        System.out.println("👥 USERS MANAGEMENT:");
        System.out.println("1. Create User");
        System.out.println("2. List All Users");
        System.out.println("3. Update User Profile");
        System.out.println("========================================");
        System.out.print("Select option: ");
    }

    // Menu AUDITOR - Lecture seule
    public static void auditorMenu(String userName){
        Display.clear();
        System.out.println("================ BANKING SYSTEM - AUDITOR ================");
        System.out.println("Logged in as: " + userName + " - AUDITOR");
        System.out.println();

        System.out.println("👁️ AUDIT & CONTROL (Read-Only):");
        System.out.println("  1. View All Accounts");
        System.out.println("  2. View All Transactions");
        System.out.println("  3. View All Credits");
        System.out.println("  4. View All Users");
        System.out.println();

        System.out.println("📊 REPORTS & STATISTICS:");
        System.out.println("  5. View Bank Statistics");
        System.out.println("  6. Export Audit Report");
        System.out.println("  7. Fees Revenue Report");
        System.out.println("  8. Transaction Analysis");
        System.out.println("  9. Credit Risk Report");
        System.out.println();

        System.out.println("🔍 MONITORING:");
        System.out.println("  10. Suspicious Transactions");
        System.out.println("  11. AML Compliance Report");
        System.out.println("  12. System Activity Log");
        System.out.println();

        System.out.println("🔐 ACCOUNT:");
        System.out.println("  13. Change Password");
        System.out.println("  14. Logout");
        System.out.println("  0. Exit");
        System.out.println("========================================");
        System.out.print("Select option: ");
    }

    // Menu de sélection des rôles (pour création d'utilisateur)
    public static void RoleMenu(){
        System.out.println("================ SELECT ROLE ================");
        System.out.println("1. TELLER");
        System.out.println("2. MANAGER");
        System.out.println("3. AUDITOR");
        System.out.println("========================================");
        System.out.print("Select role: ");
    }

    // Affichage du menu selon le rôle
    public static void displayMenuForRole(Optional<User> user) throws SQLException {
        if (user.isEmpty()) {
            System.out.println("No user logged in. Returning to main menu...");
            menu();
            return;
        }

        Scanner sc = new Scanner(System.in);
        User u = user.get();
        String role = u.getRole().toString().toUpperCase();
        boolean loggedIn = true;

        while (loggedIn) {
            switch (role) {
                case "TELLER":
                    tellerMenu(u.getName());
                    int tellerChoice = sc.nextInt();
                    sc.nextLine();
                    switch (tellerChoice) {
                        case 0:
                            loggedIn = false;
                            break;
                        default:
                            System.out.println("Invalid choice.");
                    }
                    break;

                case "MANAGER":
                    managerMenu(u.getName());
                    int managerChoice = sc.nextInt();
                    sc.nextLine();
                    switch (managerChoice) {
                        case 0:
                            loggedIn = false;
                            break;
                        default:
                            System.out.println("Invalid choice.");
                    }
                    break;

                case "ADMIN":
                    adminMenu(u.getName());
                    int adminChoice = sc.nextInt();
                    sc.nextLine();
                    switch (adminChoice) {
                        case 1:
                            Actions.createUserAbility();
                            break;
                        case 2:
                            Actions.createClient();
                            break;
                        case 3:
                            System.out.println("Updating user profile...");
                            break;
                        case 0:
                            loggedIn = false;
                            break;
                        default:
                            System.out.println("Invalid choice.");
                    }
                    break;

                case "AUDITOR":
                    auditorMenu(u.getName());
                    int auditorChoice = sc.nextInt();
                    sc.nextLine();
                    switch (auditorChoice) {
                        case 0:
                            loggedIn = false;
                            break;
                        default:
                            System.out.println("Invalid choice.");
                    }
                    break;

                default:
                    System.out.println("Unknown role: " + role);
                    loggedIn = false;
                    break;
            }
        }
    }



    public static void showSuccessMessage(String message) {
        System.out.println("\n✅ SUCCESS: " + message);
    }

    public static void showErrorMessage(String message) {
        System.err.println("\n❌ ERROR: " + message);
    }

    public static void showInfoMessage(String message) {
        System.out.println("\n💡 INFO: " + message);
    }

    public static void clear(){
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    }

    public static void showHeader(String title) {
        Display.clear();
        System.out.println("================ " + title.toUpperCase() + " ================");
    }
}