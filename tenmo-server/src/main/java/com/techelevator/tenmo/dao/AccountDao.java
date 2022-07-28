package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

import java.math.BigDecimal;
import java.util.List;

public interface AccountDao {

    BigDecimal getAccountBalance(Long accountId);

    Long findAccIdByUserId(Long userId);

    List<Account> findAllAccounts(Long accountId);

    Account findByAccountId(Long accountId);

    Account findByUserId(Long userId);

    boolean create(Account account);

}
