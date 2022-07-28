package com.techelevator.tenmo.model;

import java.math.BigDecimal;

public class Account {

    private Long userId;
    private Long accountId;
    private BigDecimal accountBalance;


    public Account(){}

    public Account(Long userId, Long accountId, BigDecimal accountBalance) {
        this.userId = userId;
        this.accountId = accountId;
        this.accountBalance = accountBalance;
    }


    public Long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public BigDecimal getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(BigDecimal accountBalance) {
        this.accountBalance = accountBalance;
    }

    @Override
    public String toString() {
        return "Account{" +
                "userId=" + userId +
                ", accountId=" + accountId +
                ", accountBalance=" + accountBalance +
                '}';
    }
}

