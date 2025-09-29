package main.java.entities;

import main.java.entities.enums.Currency;
import main.java.entities.enums.Mode;

import java.util.List;
import java.util.UUID;

public class FeeRule {
    private UUID id;
    private Mode mode;
    private Currency currency;
    private boolean is_active;
    private List<Credit> credits;
    private List<Transaction> transactions;
}