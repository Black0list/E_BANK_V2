package main.java.controllers;

import main.java.entities.Account;
import main.java.services.CreditService;
import main.java.utils.Validation;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;

public class CreditController {

    private CreditService creditService;

    public CreditController(CreditService creditService) {
        this.creditService = creditService;
    }

    public void requestCredit(Account account, BigDecimal salary, BigDecimal credit, float duration) throws SQLException {
        if(!Validation.isValidAmount(salary, 2) || !Validation.isValidAmount(credit, 2)){
            System.out.println("Amount is not valid");
            return;
        }

        creditService.requestCredit(account, salary, credit, duration);
    }
}
