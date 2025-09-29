package main.java.entities;

import main.java.entities.enums.Accountype;

import java.math.BigDecimal;
import java.time.Instant;

public class Account {
    private String id;
    private Accountype type;
    private BigDecimal balance;
    private Instant createdAt;
    private User owner;


}