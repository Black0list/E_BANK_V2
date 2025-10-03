package main.java.entities;

import main.java.entities.enums.Currency;
import main.java.entities.enums.OperationType;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public class BankFee {
    private UUID id;
    private String executor;
    private OperationType sourceType;
    private UUID sourceId;
    private BigDecimal amount;
    private String feeType;
    private Currency currency;
    private Instant createdAt;

    public BankFee(String executor, OperationType sourceType, UUID sourceId, BigDecimal amount, Currency currency) {
        this.id = UUID.randomUUID();
        this.executor = executor;
        this.sourceType = sourceType;
        this.sourceId = sourceId;
        this.amount = amount;
        this.currency = currency;
        this.createdAt = Instant.now();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getExecutor() {
        return executor;
    }

    public void setExecutor(String executor) {
        this.executor = executor;
    }

    public OperationType getSourceType() {
        return sourceType;
    }

    public void setSourceType(OperationType sourceType) {
        this.sourceType = sourceType;
    }

    public UUID getSourceId() {
        return sourceId;
    }

    public void setSourceId(UUID sourceId) {
        this.sourceId = sourceId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
