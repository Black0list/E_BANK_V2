package main.java.entities;

import main.java.entities.enums.Currency;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class Bank {
    private UUID id;
    private BigDecimal total;
    private Currency currency;
    private LocalDateTime lastUpdate;

    public Bank(UUID id, BigDecimal total, Currency currency, LocalDateTime lastUpdate) {
        this.id = id;
        this.total = total;
        this.currency = currency;
        this.lastUpdate = lastUpdate;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}