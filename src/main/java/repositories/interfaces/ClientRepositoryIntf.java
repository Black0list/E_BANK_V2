package main.java.repositories.interfaces;

import main.java.entities.Client;

import java.sql.SQLException;

public interface ClientRepositoryIntf {
    public void save(Client client) throws SQLException;
}
