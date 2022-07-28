package com.techelevator.tenmo.services;

import com.techelevator.tenmo.dto.TransferDto;
import com.techelevator.tenmo.model.AuthenticatedUser;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.util.BasicLogger;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;

public class TransferService {

    public static final String API_BASE_URL = "http://localhost:8080/";
    private RestTemplate restTemplate = new RestTemplate();
    private final AccountService accountService = new AccountService();
    private String authToken = null;

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public Transfer getTransfer(Long id) {
        Transfer transfer = null;
        if (id != null) {
            ResponseEntity<Transfer> response =
                    restTemplate.exchange(API_BASE_URL + "transfer/" + id,
                            HttpMethod.GET, makeAuthEntity(), Transfer.class);
            transfer = response.getBody(); }
        return transfer;
    }

    public boolean validTransfer(Long id, TransferDto createdTransfer, BigDecimal accountBalance) {
        boolean validTransfer = false;
        if ((accountBalance.compareTo(BigDecimal.ZERO) > 0)
                &&
                (!(createdTransfer.getAccountFromId().equals(createdTransfer.getAccountToId())))
                && (accountBalance.compareTo(createdTransfer.getTransferAmount()) >= 0)) {
            validTransfer = true;
        }
        return validTransfer;
    }

    public TransferDto createTransfer(TransferDto createdTransfer) {
        HttpEntity<TransferDto> entity = makeTransferDtoEntity(createdTransfer);
        TransferDto returnedTransfer = null;
        try {
            returnedTransfer = restTemplate.postForObject(API_BASE_URL + "transfer", entity, TransferDto.class);
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return returnedTransfer;
    }

    public TransferDto createTransferRequest(TransferDto createdTransferRequest) {
        HttpEntity<TransferDto> entity = makeTransferDtoEntity(createdTransferRequest);
        TransferDto returnedTransfer = null;
        try {
            returnedTransfer = restTemplate.postForObject(API_BASE_URL + "transfer/request", entity, TransferDto.class);
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return returnedTransfer;
    }

    public List<Transfer> displayTransfers(Long id) {
        ResponseEntity<List> response = restTemplate.exchange(API_BASE_URL + "transfer/" + id + "/showtransfers",
                HttpMethod.GET, makeAuthEntity(), List.class);
        List<Transfer> listOfTransfers = response.getBody();
        return listOfTransfers;
    }

    public List<Transfer> displayPendingTransfers(Long id) {
        ResponseEntity<List> response = restTemplate.exchange(API_BASE_URL + "transfer/" + id + "/showpendingtransfers",
                HttpMethod.GET, makeAuthEntity(), List.class);
        return (List<Transfer>) response.getBody();
    }

    public void approveTransfer(Transfer transfer) {
        boolean success = false;
        try {
            restTemplate.put(API_BASE_URL + "transfer/approved", makeTransferEntity(transfer));
            success = true;
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
    }

    public void reject(Transfer transfer) {
        boolean success = false;
        try {
            restTemplate.put(API_BASE_URL + "transfer/rejected", makeTransferEntity(transfer));
            success = true;
        } catch (RestClientResponseException | ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
    }

    private HttpEntity<Transfer> makeTransferEntity(Transfer transfer) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(transfer, headers);
    }

    private HttpEntity<TransferDto> makeTransferDtoEntity(TransferDto transferDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(transferDto, headers);
    }

    private HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(headers);
    }
}
