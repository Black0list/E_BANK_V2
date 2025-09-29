package main.java.services;

import main.java.entities.Client;
import main.java.entities.User;
import main.java.repositories.interfaces.ClientRepositoryIntf;

import java.sql.SQLException;

public class ClientService {
    private ClientRepositoryIntf clientRepo;

    public ClientService(ClientRepositoryIntf clientRepo){
        this.clientRepo = clientRepo;
    }

    public void clientCreate(String name, String email, String phone, String cin, User user) throws SQLException {
        Client client = new Client(name, email, phone, cin, user);
        clientRepo.save(client);
    }
}
