package be.spring.app.controller;

import be.spring.app.interfaces.AccountDao;
import be.spring.app.interfaces.MailService;
import be.spring.app.model.Account;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

/**
 * Created by u0090265 on 9/25/14.
 */
public abstract class AbstractTest {
    protected MockMvc mockMvc;

    protected String userName = "test@test.com";
    protected String name = "testName";
    protected String firstName = "testSurName";
    protected String password = "P3ssword";

    protected Account account;

    @Autowired
    protected WebApplicationContext wac;

    @Autowired
    protected AccountDao accountDao;

    @Autowired
    protected MailService mailService;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    public void verifyValidation(MvcResult result, List<ResultMatcher> matchers) throws Exception {
        for (ResultMatcher m : matchers) {
            m.match(result);
        }
    }

    public Account createAccount() {
        Account account = new Account();
        account.setUsername(userName);
        account.setLastName(name);
        account.setFirstName(firstName);
        accountDao.create(account);
        return account;
    }
}
