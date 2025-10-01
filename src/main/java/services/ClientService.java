package main.java.services;

import main.java.entities.Account;
import main.java.entities.Client;
import main.java.entities.User;
import main.java.entities.enums.Accountype;
import main.java.entities.enums.Currency;
import main.java.repositories.interfaces.ClientRepositoryIntf;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public class ClientService {
    private ClientRepositoryIntf clientRepo;
    private UserService userService;

    public ClientService(ClientRepositoryIntf clientRepo, UserService userService){
        this.clientRepo = clientRepo;
        this.userService = userService;
    }

    public Optional<Client> clientCreate(String name, String email, String phone, String cin, User user) throws SQLException {
        Client client = new Client(name, email, phone, cin, user);
        return clientRepo.save(client);
    }

    public Optional<Client> findClientById(UUID clientId) throws SQLException {
        Optional<Client> client = clientRepo.findClientById(clientId);
        Optional<User> user = userService.findUserById(client.get().getHelper().getId());
        client.get().setHelper(user.get());
        return client;
    }


}
