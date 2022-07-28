package com.techelevator.tenmo.services;


import com.techelevator.tenmo.App;
import com.techelevator.tenmo.dto.TransferDto;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.UserCredentials;

import java.math.BigDecimal;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class ConsoleService {

    private final Scanner scanner = new Scanner(System.in);

    public int promptForMenuSelection(String prompt) {
        int menuSelection;
        System.out.print(prompt);
        try {
            menuSelection = parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            menuSelection = -1;
        }
        return menuSelection;
    }

    public void printGreeting() {
        System.out.println("*********************");
        System.out.println("* Welcome to TEnmo! *");
        System.out.println("*********************");
    }

    public void printLoginMenu() {
        System.out.println();
        System.out.println("1: Register");
        System.out.println("2: Login");
        System.out.println("0: Exit");
        System.out.println();
    }

    public void printMainMenu() {
        System.out.println();
        System.out.println("1: View your current balance");
        System.out.println("2: View your past transfers");
        System.out.println("3: View your pending requests");
        System.out.println("4: Send TE bucks");
        System.out.println("5: Request TE bucks");
        System.out.println("0: Exit");
        System.out.println();
    }

    public UserCredentials promptForCredentials() {
        String username = promptForString("Username: ");
        String password = promptForString("Password: ");
        return new UserCredentials(username, password);
    }

    public Long promptForAccountToId() {
        Long userInput = promptForLong("Enter ID of user you are sending to (0 to cancel): ");
        return userInput;
    }

    public TransferDto promptForTransferAmount(Long accountFromId) {
        String transferAmountInput = promptForString("Enter amount: ");
        BigDecimal transferAmount = null;
        if (transferAmountInput != null) {
            transferAmount = new BigDecimal(transferAmountInput);
        } return new TransferDto(accountFromId, transferAmount);
    }

    public Long promptForTransferId() {
        Long userInput = promptForLong("Please enter transfer ID to view details (0 to cancel): ");
        return userInput;
    }

    public Long promptForNewTransferRequest() {
        Long userInput = promptForLong("Enter the account ID of the user you are making a request to (0 to cancel): ");
        return userInput;
    }

    public TransferDto promptForTransferRequestAmount(Long accountFromId) {
        String transferAmountInput = promptForString("Enter amount: ");
        BigDecimal transferAmount = new BigDecimal(transferAmountInput);
        return new TransferDto(accountFromId, transferAmount);
    }

    public Long promptForPendingRequestId() {
        return promptForLong("Please enter transfer ID to approve/reject (0 to cancel): ");
    }

    public Long approveOrRejectRequest() {
        System.out.println("1: Approve");
        System.out.println("2: Reject");
        System.out.println("0: Don't approve or reject");
        System.out.println("---------");
        Long userInput = promptForLong("Please choose an option: ");
        return userInput;
    }

    public String promptForString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public Long promptForLong(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                return Long.parseLong(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number.");
                scanner.nextLine();
            }
        }
    }

    public void pause() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }

    public void printErrorMessage() {
        System.out.println("An error occurred. Check the log for details.");
    }

}
