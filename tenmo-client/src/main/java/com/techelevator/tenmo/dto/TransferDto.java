package com.techelevator.tenmo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.util.Objects;

public class TransferDto {

    @JsonProperty("accountFromId")
    private Long accountFromId;
    @JsonProperty("accountToId")
    private Long accountToId;
    @JsonProperty("transferAmount")
    private BigDecimal transferAmount;

    public TransferDto() {
    }

    public TransferDto(Long accountFrom, BigDecimal transferAmount){
        this.accountFromId = accountFrom;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransferDto that = (TransferDto) o;
        return Objects.equals(accountFromId, that.accountFromId) && Objects.equals(accountToId, that.accountToId) && Objects.equals(transferAmount, that.transferAmount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountFromId, accountToId, transferAmount);
    }
}
