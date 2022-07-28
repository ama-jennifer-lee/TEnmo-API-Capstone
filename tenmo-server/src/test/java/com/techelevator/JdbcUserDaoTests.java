package com.techelevator;


import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.model.User;
import org.junit.Before;
import org.springframework.jdbc.core.JdbcTemplate;

public class JdbcUserDaoTests extends BaseDaoTests {

    private static final User USER_1 =
            new User(1031L, "Jethro", "1234", "user" );

    private User testUser;

    private JdbcUserDao sut;

    @Before
    public void setup() {
        sut = new JdbcUserDao((JdbcTemplate) dataSource);
        testUser = new User(1025L, "test user", "1234", "user");
    }
}
