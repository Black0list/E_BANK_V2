package main.java.utils;

import main.java.entities.Client;
import main.java.entities.User;

import java.sql.SQLException;
import java.util.Optional;
import java.util.Scanner;

public class Display {
    public static void RoleMenu(){
        System.out.println("================ SELECT ROLE ================");
        System.out.println("1. TELLER");
        System.out.println("2. MANAGER");
        System.out.println("3. AUDITOR");
        System.out.println("========================================");
        System.out.print("Select role: ");
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