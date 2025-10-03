package main.java.repositories.interfaces;

import main.java.entities.Credit;

import java.sql.SQLException;
import java.util.Optional;

public interface CreditRepositoryIntf {
    void save(Credit credit) throws SQLException;
    Optional<Credit> findActiveCreditByAccountId(String accountId) throws SQLException;
}
