package main.java.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Client {
    private UUID id;
    private String name;
    private String email;
    private String phone;
    private String cin;
    private List<Account> accounts;
    private User helper;

    public Client(String name, String email, String phone, String cin, User helper){
        this.id = UUID.randomUUID();
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.cin = cin;
        this.accounts = new ArrayList<Account>();
        this.helper = helper;
    }

    public Client(UUID id, String name, String email, String phone, String cin, User helper) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.cin = cin;
        this.helper = helper;
    }

    public Client(UUID id){
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public User getHelper() {
        return helper;
    }

    public void setHelper(User helper) {
        this.helper = helper;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", cin='" + cin + '\'' +
                ", accounts=" + accounts +
                ", helper=" + helper +
                '}';
    }
}
