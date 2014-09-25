package controllers;

import be.spring.app.interfaces.AccountDao;
import be.spring.app.model.Account;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * Created by u0090265 on 9/25/14.
 */
public abstract class AbstractTest {
    protected MockMvc mockMvc;

    private String userName = "test@test.com";
    private String name = "testName";
    private String surName = "testSurName";

    @Autowired
    protected WebApplicationContext wac;

    @Autowired
    protected AccountDao accountDao;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    public Account createAccount() {
        Account account = new Account();
        account.setUsername(userName);
        account.setLastName(name);
        account.setFirstName(surName);
        accountDao.create(account);
        return account;
    }
}
