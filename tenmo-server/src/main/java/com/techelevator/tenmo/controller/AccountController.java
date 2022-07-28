package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.model.Account;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import java.math.BigDecimal;

@RestController
@RequestMapping("/account")
@PreAuthorize("isAuthenticated()")
public class AccountController {

    private AccountDao dao;
    private JdbcTemplate jdbcTemplate;

    public AccountController(AccountDao accountDao) {
        this.dao = accountDao;
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public BigDecimal getAccountBalance(@PathVariable("id") Long accountId) {
        return dao.getAccountBalance(accountId);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/{id}/showusers", method = RequestMethod.GET)
    public List<Account> findAllAccounts(@PathVariable("id") Long accountId) {
        return dao.findAllAccounts(accountId);
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(path = "/whatismyaccountid/{userId}", method = RequestMethod.GET)
    public Long accountId(@PathVariable("userId") Long userId) {
        return dao.findAccIdByUserId(userId);
    }

}
