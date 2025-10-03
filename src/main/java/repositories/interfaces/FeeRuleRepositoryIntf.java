package main.java.repositories.interfaces;

import main.java.entities.FeeRule;
import main.java.entities.enums.OperationType;
import main.java.entities.enums.TransactionType;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FeeRuleRepositoryIntf {
    void save(FeeRule feeRule) throws SQLException;

    Optional<FeeRule> findById(UUID id) throws SQLException;

    void delete(UUID id) throws SQLException;

    Optional<FeeRule> findActiveRuleByOperationType(OperationType type) throws SQLException;
}
