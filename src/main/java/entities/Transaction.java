package main.java.entities;

import main.java.entities.enums.TransactionStatus;
import main.java.entities.enums.TransactionType;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

public class Transaction {
    private UUID id;
    private BigDecimal amount;
    private Account sender;
    private FeeRule feeRule;
    private TransactionStatus status;
    private TransactionType type;

    public Transaction(BigDecimal amount, Account sender, FeeRule feeRule, TransactionStatus status, TransactionType type) {
        this.id = UUID.randomUUID();
        this.amount = amount;
        this.sender = sender;
        this.feeRule = feeRule;
        this.status = status;
        this.type = type;
    }

    public Transaction(BigDecimal amount, Account sender, TransactionType type) {
        this.id = UUID.randomUUID();
        this.amount = amount;
        this.sender = sender;
        this.feeRule = new FeeRule();
        this.status = TransactionStatus.SETTLED;
        this.type = type;
    }


    public Transaction(UUID id, BigDecimal amount, Account sender, FeeRule feeRule, TransactionStatus status, TransactionType type) {
        this.id = id;
        this.amount = amount;
        this.sender = sender;
        this.feeRule = new FeeRule();
        this.status = TransactionStatus.SETTLED;
        this.type = type;
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Account getSender() {
        return sender;
    }

    public void setSender(Account sender) {
        this.sender = sender;
    }

    public FeeRule getFeeRule() {
        return feeRule;
    }

    public void setFeeRule(FeeRule feeRule) {
        this.feeRule = feeRule;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public void setStatus(TransactionStatus status) {
        this.status = status;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }
}
