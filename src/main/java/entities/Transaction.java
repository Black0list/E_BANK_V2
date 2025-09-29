package main.java.entities;

import main.java.entities.enums.TransactionStatus;
import main.java.entities.enums.TransactionType;

import java.math.BigDecimal;
import java.util.UUID;

public class Transaction {
    private UUID id;
    private BigDecimal amount;
    private Account accountFrom;
    private Account accountTo;
    private FeeRule feeRule;
    private TransactionStatus status;
    private TransactionType type;
}
