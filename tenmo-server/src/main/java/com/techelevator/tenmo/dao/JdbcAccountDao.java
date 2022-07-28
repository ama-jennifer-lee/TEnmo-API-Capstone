package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcAccountDao implements AccountDao {

    private JdbcTemplate jdbcTemplate;
    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public JdbcAccountDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public BigDecimal getAccountBalance(Long accountId) {
        String sql = "SELECT balance FROM account WHERE user_id = ?;";
        return jdbcTemplate.queryForObject(sql, BigDecimal.class, accountId);
    }

    @Override
    public Long findAccIdByUserId(Long userId) {
        String sql = "SELECT account_id FROM account WHERE user_id = ? ";
        return jdbcTemplate.queryForObject(sql, Long.class, userId);
    }

    @Override
    public List<Account> findAllAccounts(Long accountId) {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT account_id, user_id FROM account WHERE account_id != ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, accountId);
        while (results.next()) {
            Account listedAccount = mapRowToAccountIds(results);
            accounts.add(listedAccount);
        }
        return accounts;
    }

    @Override
    public Account findByAccountId(Long accountId) {
        Account account = null;
        String sql = "SELECT account_id, user_id, balance FROM account WHERE account_id = ? ";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, accountId);
        if (results.next()) {
            account = mapRowToAccount(results);
        }
        return account;
    }


    @Override
    public Account findByUserId(Long userId) {
        Account account = null;
        String sql = "SELECT account_id, user_id, balance FROM account WHERE user_id = ? ";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
        if (results.next()) {
            account = mapRowToAccount(results);
        }
        return account;
    }

    @Override
    public boolean create(Account account) {
        String sql = "INSERT INTO account (user_id, balance) VALUES (?, ?)";
        return jdbcTemplate.update(sql, account.getUserId(), account.getAccountBalance()) == 1;
    }

    private Account mapRowToAccountIds(SqlRowSet rowSet) {
        Account account = new Account();
        account.setAccountId(rowSet.getLong("account_id"));
        account.setUserId(rowSet.getLong("user_id"));
        return account;
    }

    private Account mapRowToAccount(SqlRowSet rowSet) {
        Account account = new Account();
        account.setAccountId(rowSet.getLong("account_id"));
        account.setUserId(rowSet.getLong("user_id"));
        account.setAccountBalance(rowSet.getBigDecimal("balance"));
        return account;
    }
}
