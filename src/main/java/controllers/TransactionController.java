package main.java.controllers;

import main.java.entities.Account;
import main.java.services.TransactionService;
import main.java.utils.Validation;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;

public class TransactionController {
    private TransactionService transactService;

    public TransactionController(TransactionService transactService){
        this.transactService = transactService;
    }

    public void Transfer(BigDecimal amount, String senderId, String receiverId) throws SQLException {
        if(Objects.isNull(senderId) || Objects.isNull(receiverId)){
            System.out.println("Invalid Inputs");
            return;
        }

        if(Objects.isNull(amount) || !Validation.isValidAmount(amount, 2)){
            System.out.println("Amount is not valid");
            return;
        }

        transactService.recordTransfer(senderId, receiverId, amount);
    }
}
