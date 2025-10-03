package main.java.controllers;

import main.java.application.Main;
import main.java.entities.Client;
import main.java.entities.User;
import main.java.entities.enums.Accountype;
import main.java.entities.enums.Role;
import main.java.services.ClientService;

import java.sql.SQLException;
import java.util.*;

public class ClientController {
    private ClientService clientService;

    public ClientController(ClientService clientService){
        this.clientService = clientService;
    }

    public Optional<Client> createClient(String name, String email, String phone, String cin, User user) throws SQLException {
        List<Role> permittedRoles = Arrays.asList(Role.MANAGER, Role.TELLER, Role.ADMIN);
        if(permittedRoles.stream().noneMatch(role -> role == Main.USER.get().getRole())) {
            System.out.println("You dont have The ability to create client account");
            return Optional.empty();
        }

        if(Objects.isNull(name)){
            System.out.println("name is not valid");
            return Optional.empty();
        }

        if(Objects.isNull(email)){
            System.out.println("email is not valid");
            return Optional.empty();
        }

        if(Objects.isNull(phone)){
            System.out.println("phone is not valid");
            return Optional.empty();
        }

        if(Objects.isNull(cin)){
            System.out.println("cin is not valid");
            return Optional.empty();
        }

        if(Objects.isNull(user)){
            System.out.println("Authenticated user is not valid");
            return Optional.empty();
        }

        return clientService.clientCreate(name,email,phone,cin,user);
    }

    public Optional<Client> findClientId(UUID clientId) throws SQLException {
        if(Objects.isNull(clientId)){
            System.out.println("Invalid Input");
            return Optional.empty();
        }

        return clientService.findClientById(clientId);
    }

}
