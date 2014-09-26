package be.spring.app.controller;

import be.spring.app.model.Account;
import be.spring.app.persistence.AccountDao;
import be.spring.app.service.MailService;
import be.spring.app.utils.SecurityUtils;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.Filter;
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
    protected SecurityUtils securityUtils;

    @Autowired
    private Filter springSecurityFilterChain;

    @Autowired
    protected UserDetailsService userDetailsService;

    @Autowired
    protected WebApplicationContext wac;

    @Autowired
    protected AccountDao accountDao;

    @Autowired
    protected MailService mailService;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).addFilter(springSecurityFilterChain).build();
    }

    public void verifyValidation(MvcResult result, List<ResultMatcher> matchers) throws Exception {
        for (ResultMatcher m : matchers) {
            m.match(result);
        }
    }

    public Account getTestAccount() {
        return accountDao.findByUsername(userName);
    }

    public Account createRandomAccount() {
        Account account = new Account();
        account.setUsername(DataFactory.getDefaultRandomString() + "@test.com");
        account.setLastName(DataFactory.getDefaultRandomString());
        account.setFirstName(DataFactory.getDefaultRandomString());
        account.setActive(true);
        accountDao.save(account);
        return account;
    }
}
