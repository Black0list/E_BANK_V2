package main.java.repositories.interfaces;

import main.java.entities.BankFee;

import java.sql.SQLException;

public interface BankFeeRepositoryIntf {
    void save(BankFee bankFee) throws SQLException;
}
