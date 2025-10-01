package main.java.entities;

import main.java.entities.enums.Currency;
import main.java.entities.enums.Mode;
import main.java.entities.enums.TransactionType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FeeRule {
    private UUID id;
    private Mode mode;
    private TransactionType operationType;
    private Currency currency;
    private BigDecimal fee;
    private boolean is_active;
    private List<Credit> credits;
    private List<Transaction> transactions;

    public FeeRule(UUID id, Mode mode, TransactionType operationType, Currency currency, BigDecimal fee, boolean is_active) {
        this.id = UUID.randomUUID();
        this.mode = mode;
        this.operationType = operationType;
        this.currency = currency;
        this.fee = fee;
        this.is_active = is_active;
        this.credits = new ArrayList<Credit>();
        this.transactions = new ArrayList<Transaction>();
    }

    public FeeRule() {
        this.id = UUID.randomUUID();
        this.mode = Mode.FIX;
        this.operationType = TransactionType.DEPOSIT;
        this.currency = Currency.MAD;
        this.fee = new BigDecimal(0);
        this.is_active = false;
        this.credits = new ArrayList<Credit>();
        this.transactions = new ArrayList<Transaction>();
    }

    public FeeRule(UUID id){
        this.id = id;
    }

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public TransactionType getOperationType() {
        return operationType;
    }

    public void setOperationType(TransactionType operationType) {
        this.operationType = operationType;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public boolean getIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

    public List<Credit> getCredits() {
        return credits;
    }

    public void setCredits(List<Credit> credits) {
        this.credits = credits;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}