package com.techelevator.tenmo.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Transfer {

    private Long transferId;
    private Long transferTypeId;
    private String transferTypeDesc;
    private Long transferStatusId;
    private String transferStatusDesc;
    private Long accountFrom;
    private Long accountTo;
    private BigDecimal transferAmount;

    public Transfer() {}

    public Transfer(Long accountFrom, Long accountTo, BigDecimal transferAmount) {
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
        this.transferAmount = transferAmount;
    }

    public Long getTransferId() {
        return transferId;
    }

    public void setTransferId(Long transferId) {
        this.transferId = transferId;
    }

    public Long getTransferTypeId() {
        return transferTypeId;
    }

    public void setTransferTypeId(Long transferTypeId) {
        this.transferTypeId = transferTypeId;
    }

    public String getTransferTypeDesc() {
        return transferTypeDesc;
    }

    public void setTransferTypeDesc(String transferTypeDesc) {
        this.transferTypeDesc = transferTypeDesc;
    }

    public Long getTransferStatusId() {
        return transferStatusId;
    }

    public void setTransferStatusId(Long transferStatusId) {
        this.transferStatusId = transferStatusId;
    }

    public String getTransferStatusDesc() {
        return transferStatusDesc;
    }

    public void setTransferStatusDesc(String transferStatusDesc) {
        this.transferStatusDesc = transferStatusDesc;
    }

    public Long getAccountFrom() {
        return accountFrom;
    }

    public void setAccountFrom(Long accountFrom) {
        this.accountFrom = accountFrom;
    }

    public Long getAccountTo() {
        return accountTo;
    }

    public void setAccountTo(Long accountTo) {
        this.accountTo = accountTo;
    }

    public BigDecimal getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(BigDecimal transferAmount) {
        this.transferAmount = transferAmount;
    }

    @Override
    public String toString() {
        return "Transfer{" +
                "transferTypeId=" + transferTypeId +
                ", transferTypeDesc='" + transferTypeDesc + '\'' +
                ", transferStatusId=" + transferStatusId +
                ", transferStatusDesc='" + transferStatusDesc + '\'' +
                ", accountFrom=" + accountFrom +
                ", accountTo=" + accountTo +
                ", transferAmount=" + transferAmount +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transfer transfer = (Transfer) o;
        return Objects.equals(transferId, transfer.transferId) && Objects.equals(transferTypeId, transfer.transferTypeId) && Objects.equals(transferTypeDesc, transfer.transferTypeDesc) && Objects.equals(transferStatusId, transfer.transferStatusId) && Objects.equals(transferStatusDesc, transfer.transferStatusDesc) && Objects.equals(accountFrom, transfer.accountFrom) && Objects.equals(accountTo, transfer.accountTo) && Objects.equals(transferAmount, transfer.transferAmount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transferId, transferTypeId, transferTypeDesc, transferStatusId, transferStatusDesc, accountFrom, accountTo, transferAmount);
    }
}
