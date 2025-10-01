package main.java.repositories.interfaces;

import main.java.entities.Transaction;
import main.java.entities.enums.TransactionStatus;
import main.java.entities.enums.TransactionType;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TransactionRepositoryIntf {
    void save(Transaction transaction) throws SQLException;

    Optional<Transaction> findById(UUID id) throws SQLException;

    List<Transaction> findAll() throws SQLException;

    List<Transaction> findByStatus(TransactionStatus status) throws SQLException;

    void delete(UUID id) throws SQLException;
}
