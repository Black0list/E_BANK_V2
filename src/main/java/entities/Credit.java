package main.java.entities;

import main.java.entities.enums.CreditStatus;

import java.math.BigDecimal;
import java.util.UUID;

public class Credit{
    private UUID id;
    private BigDecimal amount;
    private float duration;
    private BigDecimal taux;
    private String justificatif_revenu;
    private FeeRule feeRule;
    private CreditStatus status;
}