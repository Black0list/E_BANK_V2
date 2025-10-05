package main.java.repositories.interfaces;

import main.java.entities.Credit;
import main.java.entities.enums.CreditStatus;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CreditRepositoryIntf {
    void save(Credit credit) throws SQLException;
    Optional<Credit> findActiveCreditByAccountId(String accountId) throws SQLException;
    List<Credit> findAllByStatus(String status) throws SQLException;
    void validateCredit(UUID creditId) throws SQLException;
    void denyCredit(UUID creditId) throws SQLException;
    void updateCreditBalanceAndStatus(UUID id, BigDecimal total, float duration, CreditStatus status) throws SQLException ;
}
