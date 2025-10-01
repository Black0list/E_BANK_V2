package main.java.services;

import main.java.entities.Account;
import main.java.entities.FeeRule;
import main.java.entities.Transaction;
import main.java.entities.enums.TransactionType;
import main.java.repositories.FeeRuleRepository;
import main.java.repositories.interfaces.FeeRuleRepositoryIntf;

import java.sql.SQLException;
import java.util.Optional;

public class FeeRuleService {
    private FeeRuleRepositoryIntf feeRepo;

    public FeeRuleService(FeeRuleRepositoryIntf feeRepo){
        this.feeRepo = feeRepo;
    }

    public Optional<FeeRule> findByType(TransactionType type) throws SQLException {
        return feeRepo.findActiveRuleByTransactionType(type);
    }
}
