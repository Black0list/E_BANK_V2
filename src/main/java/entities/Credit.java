package main.java.entities;

import main.java.entities.enums.CreditStatus;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

public class Credit{
    private UUID id;
    private BigDecimal amount;
    private BigDecimal total;
    private BigDecimal income;
    private BigDecimal reduce;
    private float duration;
    private FeeRule feeRule;
    private CreditStatus status;
    private Account account;

    public Credit(UUID id, BigDecimal amount, BigDecimal total, BigDecimal income, float duration, FeeRule feeRule, CreditStatus status, Account account, BigDecimal reduce) {
        this.id = id;
        this.amount = amount;
        this.total = total;
        this.income = income;
        this.duration = duration;
        this.feeRule = feeRule;
        this.status = status;
        this.account = account;
        this.reduce = reduce;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Credit(BigDecimal amount, float duration, BigDecimal income, Account account) {
        this.id = UUID.randomUUID();
        this.total = BigDecimal.ZERO;
        this.amount = amount;
        this.income = income;
        this.duration = duration;
        this.status = CreditStatus.PENDING;
        this.account = account;
        this.reduce = BigDecimal.ZERO;
    }

    public Credit(UUID id, BigDecimal amount, BigDecimal total, BigDecimal income, BigDecimal reduce, float duration, FeeRule feeRule, CreditStatus status, Account account) {
        this.id = id;
        this.amount = amount;
        this.total = total;
        this.income = income;
        this.reduce = reduce;
        this.duration = duration;
        this.feeRule = feeRule;
        this.status = status;
        this.account = account;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    public BigDecimal getIncome() {
        return income;
    }

    public void setIncome(BigDecimal income) {
        this.income = income;
    }

    public FeeRule getFeeRule() {
        return feeRule;
    }

    public void setFeeRule(FeeRule feeRule) {
        this.feeRule = feeRule;
    }

    public CreditStatus getStatus() {
        return status;
    }

    public void setStatus(CreditStatus status) {
        this.status = status;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public BigDecimal getReduce() {
        return reduce;
    }

    public void setReduce(BigDecimal reduce) {
        this.reduce = reduce;
    }

    @Override
    public String toString() {
        return "Credit{" +
                "id=" + id +
                ", amount=" + amount +
                ", total=" + total +
                ", income=" + income +
                ", reduce=" + reduce +
                ", duration=" + duration +
                ", feeRule=" + feeRule +
                ", status=" + status +
                ", account=" + account +
                '}';
    }
}