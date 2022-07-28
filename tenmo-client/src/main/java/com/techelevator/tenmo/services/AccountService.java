package com.techelevator.tenmo.services;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.AuthenticatedUser;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;

public class AccountService {

    public static final String API_BASE_URL = "http://localhost:8080/";
    private RestTemplate restTemplate = new RestTemplate();
    private String authToken = null;

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public BigDecimal getAccountBalance(Long id) {
        ResponseEntity<BigDecimal> response =
                restTemplate.exchange(API_BASE_URL + "account/" + id,
                        HttpMethod.GET, makeAuthEntity(), BigDecimal.class);
        BigDecimal accountBalance = response.getBody();
        return accountBalance;
    }

    public List<Account> displayAccounts(Long id) {
        ResponseEntity<List> response = restTemplate.exchange(API_BASE_URL +"account/" + id + "/showusers",
                HttpMethod.GET, makeAuthEntity(), List.class);
        List<Account> listOfUsers = response.getBody();
        return listOfUsers;
    }

    public Long getAccIdByUserId(Long userId) {
        ResponseEntity<Long> response =
                restTemplate.exchange(API_BASE_URL + "account/whatismyaccountid/" + userId, HttpMethod.GET, makeAuthEntity(), Long.class);
        Long accountId = response.getBody();
        return accountId;
    }

    private HttpEntity<Void> makeAuthEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(authToken);
        return new HttpEntity<>(headers);
    }

}
