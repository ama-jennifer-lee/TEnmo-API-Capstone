package com.techelevator.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.Transfer;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

public class StringFormatter {

    public String string;
    ObjectMapper mapper = new ObjectMapper();
    DecimalFormat df = new DecimalFormat("#.00");


    public void formatAccountList(List<Account> listOfAccounts) {
        List<Account> listOfUsers =
                mapper.convertValue(listOfAccounts, new TypeReference<List<Account>>() {});
        for (Account account : listOfUsers) {
            String formattedListOfUsers = "Account ID: " + account.getAccountIdId();
            System.out.println(formattedListOfUsers);
        }
    }

    public void formatTransferDetails(Transfer transfer) {
        System.out.println("--------------------------------------------");
        System.out.println("Transfer Details");
        System.out.println("--------------------------------------------");
        System.out.println("Transaction ID: " + transfer.getTransferId());
        System.out.println("From Account ID: " + transfer.getAccountFrom());
        System.out.println("To Account ID: " + transfer.getAccountTo());
        System.out.println("Type: " + transfer.getTransferTypeDesc());
        System.out.println("Status: " + transfer.getTransferStatusDesc());
        System.out.println("Amount: $" + transfer.getTransferAmount() + "\n");
    }

    public void transferListHeader() {
        String columnNameFormat = String.format("%-10.20s %-10.20s %-10.20s %s", "ID", "FROM", "TO", "AMOUNT");
        System.out.println("--------------------------------------------");
        System.out.println("--------------Transfer History--------------");
        System.out.println("--------------------------------------------");
        System.out.println(columnNameFormat);
        System.out.println("--------------------------------------------");

    }

    public void formatTransferList(List<Transfer> listOfTransfers) {
        List<Transfer> formattedListOfTransfers =
                mapper.convertValue(listOfTransfers, new TypeReference<List<Transfer>>() {
                });

        for (Transfer transfer : formattedListOfTransfers) {
            String transferId = String.valueOf(transfer.getTransferId());
            String accountFrom = String.valueOf(transfer.getAccountFrom());
            String accountTo = String.valueOf(transfer.getAccountTo());
            String transferAmount = df.format(transfer.getTransferAmount());
            String entriesFormat = String.format("%-10.20s %-10.20s %-10.20s $%s", transferId, accountFrom, accountTo, transferAmount);
            System.out.println(entriesFormat);
        }

    }

    public void formatPendingTransferList(List<Transfer> listOfTransfers) {
        List<Transfer> formattedListOfTransfers =
                mapper.convertValue(listOfTransfers, new TypeReference<List<Transfer>>() {
                });

        for (Transfer transfer : formattedListOfTransfers) {
            String transferId = String.valueOf(transfer.getTransferId());
            String accountFrom = String.valueOf(transfer.getAccountFrom());
            String transferAmount = df.format(transfer.getTransferAmount());
            String entriesFormat = String.format("%-16.20s %-16.20s $%s", transferId, accountFrom, transferAmount);
            System.out.println(entriesFormat);
        }

    }

    public void pendingRequestsHeader() {
        String columnNameFormat = String.format("%-16.20s %-16.20s %s", "ID", "FROM", "AMOUNT");
        System.out.println("--------------------------------------------");
        System.out.println("--------------Pending Requests--------------");
        System.out.println("--------------------------------------------");
        System.out.println(columnNameFormat);
        System.out.println("--------------------------------------------");
    }


}

