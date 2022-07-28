package com.techelevator.tenmo.controller.dto;

import java.math.BigDecimal;

public class TransferDto {

    private Long accountFromId;
    private Long accountToId;
    private BigDecimal transferAmount;

    public TransferDto() {
    }

    public TransferDto(Long accountFrom, Long accountTo, BigDecimal transferAmount) {
        this.accountFromId = accountFrom;
        this.accountToId = accountTo;
        this.transferAmount = transferAmount;
    }

    public Long getAccountFromId() {
        return accountFromId;
    }

    public void setAccountFromId(Long accountFromId) {
        this.accountFromId = accountFromId;
    }

    public Long getAccountToId() {
        return accountToId;
    }

    public void setAccountToId(Long accountToId) {
        this.accountToId = accountToId;
    }

    public BigDecimal getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(BigDecimal transferAmount) {
        this.transferAmount = transferAmount;
    }
}
