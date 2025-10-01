package main.java.repositories.interfaces;

import main.java.entities.Client;

import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

public interface ClientRepositoryIntf {
    Optional<Client> save(Client client) throws SQLException;
    Optional<Client> findClientById(UUID clientId) throws SQLException;
}
