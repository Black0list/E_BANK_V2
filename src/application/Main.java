package application;

import java.sql.*;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/E_BANKING";
        Properties props = new Properties();
        props.setProperty("user", "postgres");
        props.setProperty("password", "password");

        try (Connection conn = DriverManager.getConnection(url, props)) {
            System.out.println("✅ Connected to PostgreSQL!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
