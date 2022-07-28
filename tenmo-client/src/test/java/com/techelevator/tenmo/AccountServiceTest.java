package com.techelevator.tenmo;

import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.services.AccountService;
import org.hamcrest.core.StringStartsWith;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

import static org.junit.Assert.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

public class AccountServiceTest {

    private static final String EXPECTED_API_URL = "http://localhost:8080/account/";
    private static final Long TEST_ID = 2001L;

    private final RestTemplate restTemplate = new RestTemplate();
    private final AccountService sut = new AccountService();

    @Test
    public void getAccountBalanceTest() {
        ReflectionTestUtils.setField(sut, "restTemplate", restTemplate);
        MockRestServiceServer server = MockRestServiceServer.createServer(restTemplate);

        server.expect(requestTo(EXPECTED_API_URL + TEST_ID))
                .andExpect(method(HttpMethod.GET))
                .andExpect(header(HttpHeaders.AUTHORIZATION, new StringStartsWith("Bearer")))
                .andRespond(withSuccess("{\"account\":" + TEST_ID + "}", MediaType.APPLICATION_JSON));
        BigDecimal balance = null;
        try {
            balance = sut.getAccountBalance(TEST_ID);
        } catch (AssertionError e) {
            fail("Didn't send the expected request to retrieve all auctions. Verify that the URL, HTTP method, and headers are correct.");
        }
        assertNotNull("Call to getAccountBalance() returned null.", balance);
    }
}
