package com.techelevator.tenmo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Transaction not found.")
public class TransferNotFoundException extends Exception {

    public TransferNotFoundException() {
        super("Transaction not found.");
    }
}
