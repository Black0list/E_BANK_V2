package main.java.utils;

import java.sql.*;
import java.util.Properties;

public class DbManager {
    private static DbManager instance;
    private Connection connection;
    private Properties props;

    private DbManager() {
        props = new Properties();
        props.setProperty("url", "jdbc:postgresql://localhost:5432/");
        props.setProperty("dbname", "E_BANKING");
        props.setProperty("user", "postgres");
        props.setProperty("password", "123123");
    }

    public void initDb() {
        String url = props.getProperty("url");
        String dbName = props.getProperty("dbname");
        String user = props.getProperty("user");
        String password = props.getProperty("password");

        Properties connProps = new Properties();
        connProps.setProperty("user", user);
        connProps.setProperty("password", password);

        try (Connection tempConn = DriverManager.getConnection(url, connProps);
             Statement stmt = tempConn.createStatement()) {
            stmt.executeUpdate("CREATE DATABASE " + dbName);
        } catch (SQLException e) {
            if (!e.getMessage().contains("already exists")) {
                throw new RuntimeException(e);
            }
        }

        try {
            connection = DriverManager.getConnection(url + dbName, connProps);
            try (Statement dbStmt = connection.createStatement()) {
                dbStmt.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS users (
                        id UUID PRIMARY KEY,
                        name VARCHAR(100) NOT NULL,
                        email VARCHAR(100) UNIQUE NOT NULL,
                        password VARCHAR(255) NOT NULL,
                        role VARCHAR(50) NOT NULL
                    )
                """);
                dbStmt.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS clients (
                        id UUID PRIMARY KEY,
                        name VARCHAR(100) NOT NULL,
                        email VARCHAR(100) UNIQUE NOT NULL,
                        phone VARCHAR(255) NOT NULL,
                        cin VARCHAR(255) NOT NULL,
                        helper_id UUID,
                        CONSTRAINT fk_helper FOREIGN KEY (helper_id) REFERENCES users(id) ON DELETE SET NULL
                    )
                """);
                dbStmt.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS accounts (
                        id UUID PRIMARY KEY,
                        type VARCHAR(50) NOT NULL,
                        balance NUMERIC(19,4) NOT NULL DEFAULT 0,
                        currency VARCHAR(50) NOT NULL,
                        created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
                        owner_id UUID,
                        CONSTRAINT fk_owner FOREIGN KEY (owner_id) REFERENCES clients(id) ON DELETE SET NULL
                    )
                """);
                dbStmt.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS fee_rules (
                        id UUID PRIMARY KEY,
                        mode VARCHAR(50) NOT NULL,
                        currency VARCHAR(10) NOT NULL,
                        is_active BOOLEAN NOT NULL DEFAULT TRUE
                    )
                """);
                dbStmt.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS credits (
                        id UUID PRIMARY KEY,
                        amount NUMERIC(19,4) NOT NULL,
                        duration FLOAT NOT NULL,
                        taux NUMERIC(5,2) NOT NULL,
                        justificatif_revenu VARCHAR(255),
                        fee_rule_id UUID REFERENCES fee_rules(id) ON DELETE SET NULL,
                        status VARCHAR(50) NOT NULL
                    )
                """);
                dbStmt.executeUpdate("""
                    CREATE TABLE IF NOT EXISTS transactions (
                        id UUID PRIMARY KEY,
                        amount NUMERIC(19,4) NOT NULL,
                        account_from_id UUID REFERENCES accounts(id) ON DELETE SET NULL,
                        account_to_id UUID REFERENCES accounts(id) ON DELETE SET NULL,
                        fee_rule_id UUID REFERENCES fee_rules(id) ON DELETE SET NULL,
                        status VARCHAR(50) NOT NULL,
                        type VARCHAR(50) NOT NULL
                    )
                """);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static synchronized DbManager getInstance() {
        if (instance == null) {
            instance = new DbManager();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}
