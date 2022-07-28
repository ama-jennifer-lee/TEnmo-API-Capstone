package com.techelevator;

import com.techelevator.tenmo.dao.JdbcAccountDao;
import com.techelevator.tenmo.model.Account;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

public class JdbcAccountDaoTests extends BaseDaoTests{

    private static final Account ACCOUNT_1 = new Account(1001L, 2001L, BigDecimal.valueOf(1000));

    private static final Account ACCOUNT_2 = new Account(1002L, 2002L, BigDecimal.valueOf(1000));

    private static final Account ACCOUNT_3 = new Account(1003L, 2003L, BigDecimal.valueOf(1000));

    private static final Account ACCOUNT_4 = new Account(1004L, 2004L, BigDecimal.valueOf(1000));

    private Account testAccount;

    private JdbcAccountDao sut;

    @Before
    public void setup() {
        sut = new JdbcAccountDao(dataSource);
        testAccount = new Account(1031L, 2031L, BigDecimal.valueOf(2000));
    }

    @Test
    public void findByAccountId_returns_correct_account_for_userId() {
        Account account = sut.findByAccountId(1001L);
        assertAccountsMatch(ACCOUNT_1, account);

        account = sut.findByAccountId(2001L);
        assertAccountsMatch(ACCOUNT_2, account);
    }

    @Test
    public void getCity_returns_null_when_id_not_found() {
        Account account = sut.findByAccountId(9999L);
        Assert.assertNull(account);
    }

    private void assertAccountsMatch(Account expected, Account actual) {
        Assert.assertEquals(expected.getAccountId(), actual.getAccountId());
        Assert.assertEquals(expected.getUserId(), actual.getUserId());
        Assert.assertEquals(expected.getAccountBalance(), actual.getAccountBalance());
    }
}
