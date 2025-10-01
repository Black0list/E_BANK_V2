package main.java.entities;

import main.java.entities.enums.Accountype;
import main.java.entities.enums.Currency;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Random;

public class Account {
    private String id;
    private Accountype type;
    private Currency currency;
    private BigDecimal balance;
    private BigDecimal interestRate;
    private boolean is_active;
    private Client owner;
    private Instant createdAt;

    Random random = new Random();

    public Account(Accountype type, Currency currency, Client owner) {
        this.id = "BK-"+ random.nextInt(9000) +"-"+random.nextInt(9000);;
        this.type = type;
        this.currency = currency;
        switch (type) {
            case SAVINGS:
                this.balance = new BigDecimal(500).setScale(2, RoundingMode.DOWN);
                this.interestRate = new BigDecimal("0.03").setScale(2, RoundingMode.DOWN);
                break;
            case COURANT:
                this.balance = new BigDecimal(100).setScale(2, RoundingMode.DOWN);
                this.interestRate = new BigDecimal("0.01").setScale(2, RoundingMode.DOWN);
                break;
            case CREDIT:
                this.balance = new BigDecimal(0).setScale(2, RoundingMode.DOWN);
                this.interestRate = new BigDecimal("0.00").setScale(2, RoundingMode.DOWN);
                break;
        }
        this.is_active = true;
        this.owner = owner;
        this.createdAt = Instant.now();
    }

    public Account(String id, Accountype type, Currency currency, BigDecimal balance, BigDecimal interestRate, boolean is_active, Client owner, Instant createdAt) {
        this.id = id;
        this.type = type;
        this.currency = currency;
        this.balance = balance;
        this.interestRate = interestRate;
        this.is_active = is_active;
        this.owner = owner;
        this.createdAt = createdAt;
    }

    public Account(String id){
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Accountype getType() {
        return type;
    }

    public void setType(Accountype type) {
        this.type = type;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public boolean getStatus() {
        return is_active;
    }

    public void setStatus(boolean status) {
        this.is_active = status;
    }

    public Client getOwner() {
        return owner;
    }

    public void setOwner(Client owner) {
        this.owner = owner;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id='" + id + '\'' +
                ", type=" + type +
                ", currency=" + currency +
                ", balance=" + balance +
                ", interestRate=" + interestRate +
                ", status=" + is_active +
                ", owner=" + owner +
                ", createdAt=" + createdAt +
                '}';
    }
}