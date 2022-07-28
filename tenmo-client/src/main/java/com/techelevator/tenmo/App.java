package com.techelevator.tenmo;

import com.techelevator.tenmo.dto.TransferDto;
import com.techelevator.tenmo.model.*;
import com.techelevator.tenmo.services.AccountService;
import com.techelevator.tenmo.services.AuthenticationService;
import com.techelevator.tenmo.services.ConsoleService;
import com.techelevator.tenmo.services.TransferService;
import com.techelevator.util.StringFormatter;

import java.math.BigDecimal;

public class App {

    private static final String API_BASE_URL = "http://localhost:8080/";

    private final ConsoleService consoleService = new ConsoleService();
    private final AuthenticationService authenticationService = new AuthenticationService(API_BASE_URL);
    private final AccountService accountService = new AccountService();
    private final TransferService transferService = new TransferService();
    private final StringFormatter stringFormatter = new StringFormatter();
    private AuthenticatedUser currentUser;


    public static void main(String[] args) {
        App app = new App();
        app.run();
    }

    private void run() {
        consoleService.printGreeting();
        loginMenu();
        if (currentUser != null) {
            mainMenu();
        }
    }

    private void loginMenu() {
        int menuSelection = -1;
        while (menuSelection != 0 && currentUser == null) {
            consoleService.printLoginMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                handleRegister();
            } else if (menuSelection == 2) {
                handleLogin();
            } else if (menuSelection != 0) {
                System.out.println("Invalid Selection");
                consoleService.pause();
            }
        }
    }

    private void handleRegister() {
        System.out.println("Please register a new user account");
        UserCredentials credentials = consoleService.promptForCredentials();
        if (authenticationService.register(credentials)) {
            System.out.println("Registration successful. You can now login.");
        } else {
            consoleService.printErrorMessage();
        }
    }

    private void handleLogin() {
        UserCredentials credentials = consoleService.promptForCredentials();
        currentUser = authenticationService.login(credentials);
        if (currentUser == null) {
            consoleService.printErrorMessage();
        }
        currentUser.setToken(currentUser.getToken());
        accountService.setAuthToken(currentUser.getToken());
        transferService.setAuthToken(currentUser.getToken());
    }


    private void mainMenu() {
        int menuSelection = -1;
        while (menuSelection != 0) {
            consoleService.printMainMenu();
            menuSelection = consoleService.promptForMenuSelection("Please choose an option: ");
            if (menuSelection == 1) {
                viewCurrentBalance();
            } else if (menuSelection == 2) {
                viewTransferHistory();
            } else if (menuSelection == 3) {
                viewPendingRequests();
            } else if (menuSelection == 4) {
                sendBucks();
            } else if (menuSelection == 5) {
                requestBucks();
            }else if (menuSelection == 0) {
                continue;
            } else {
                System.out.println("Invalid Selection");
            }
            consoleService.pause();
        }
    }

    private void viewCurrentBalance() {
        BigDecimal accountBalance = accountService.getAccountBalance(currentUser.getUser().getId());
        System.out.println("Your current account balance is: $" + accountBalance);
    }

    private void viewTransferHistory() {
        stringFormatter.transferListHeader();
        Long accountFromId = accountService.getAccIdByUserId(currentUser.getUser().getId());
        stringFormatter.formatTransferList(transferService.displayTransfers(accountFromId));
        Long transferId = consoleService.promptForTransferId();
        if (transferId == 0) {
            System.out.println("Exiting...");
        } else if (transferService.getTransfer(transferId) != null) {
            stringFormatter.formatTransferDetails(transferService.getTransfer(transferId));
        } else {
            System.out.println("Invalid transfer ID. Please try again.");
        }
    }

    private void viewPendingRequests() {
        stringFormatter.pendingRequestsHeader();
        Long accountFromId = accountService.getAccIdByUserId(currentUser.getUser().getId());
        stringFormatter.formatPendingTransferList(transferService.displayPendingTransfers(accountFromId));
        Long transferId = consoleService.promptForPendingRequestId();
        if (transferId == 0) {
            System.out.println("Exiting...");
        } else {
            Long approveOrReject = consoleService.approveOrRejectRequest();
            if (approveOrReject == 0) {
                System.out.println("Exiting...");
            } else if (approveOrReject == 2) {
                transferService.reject(transferService.getTransfer(transferId));
                System.out.println("Request denied successfully!");
            } else {
                transferService.approveTransfer(transferService.getTransfer(transferId));
                System.out.println("Request approved successfully!");
            }

        }
    }

    private void sendBucks() {
        Long accountFromId = accountService.getAccIdByUserId(currentUser.getUser().getId());
        stringFormatter.formatAccountList(accountService.displayAccounts(accountFromId));
        Long accountToId = consoleService.promptForAccountToId();

        if (accountToId == 0) {
            System.out.println("Exiting...");
        } else {
            TransferDto newTransfer = consoleService.promptForTransferAmount(accountFromId);
            newTransfer.setAccountToId(accountToId);
            if (transferService.validTransfer(currentUser.getUser().getId(), newTransfer, accountService.getAccountBalance(currentUser.getUser().getId()))) {
                TransferDto createdTransfer = transferService.createTransfer(newTransfer);
                if (createdTransfer == null) {
                    consoleService.printErrorMessage();
                } else {
                    System.out.println("Transfer successful!");
                }
            }
        }
    }


    private void requestBucks() {
        Long accountFromId = accountService.getAccIdByUserId(currentUser.getUser().getId());
        stringFormatter.formatAccountList(accountService.displayAccounts(accountFromId));
        Long accountToId = consoleService.promptForNewTransferRequest();
        if (accountToId == 0) {
            System.out.println("Exiting...");
        } else {
            TransferDto newTransfer = consoleService.promptForTransferRequestAmount(accountFromId);
            newTransfer.setAccountToId(accountToId);
            if (transferService.validTransfer(currentUser.getUser().getId(), newTransfer, accountService.getAccountBalance(currentUser.getUser().getId()))) {
                TransferDto createdTransferRequest = transferService.createTransferRequest(newTransfer);
                if (createdTransferRequest == null) {
                    consoleService.printErrorMessage();
                } else {
                    System.out.println("Transfer request sent!");
                }
            } else {
                System.out.println("Invalid request. Please try again.");
            }
        }
    }


}
