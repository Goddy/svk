package be.svk.webapp.controller;

import be.svk.webapp.model.Account;
import be.svk.webapp.model.Role;
import be.svk.webapp.persistence.AccountDao;
import be.svk.webapp.persistence.MatchesDao;
import be.svk.webapp.service.MailService;
import be.svk.webapp.utils.SecurityUtils;
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

import static org.easymock.EasyMock.expect;

/**
 * Created by u0090265 on 9/25/14.
 */
public abstract class AbstractTest {
    protected MockMvc mockMvc;

    protected String userName = "test@test.com";
    protected String validUserName = "notInUse@test.com";
    protected String name = "testName";
    protected String firstName = "testsurname";
    protected String password = "P3ssword";

    protected static final String ROLE_NONE = "NONE";
    protected static final String ROLE_ADMIN = "ADMIN";
    protected static final String ROLE_USER = "USER";


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
    protected MatchesDao matchesDao;

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
        Account.Builder b = new Account.Builder()
                .username(DataFactory.getDefaultRandomString() + "@test.com")
                .lastName(DataFactory.getDefaultRandomString())
                .firstName(DataFactory.getDefaultRandomString())
                .active(true);
        return b.build();
    }

    public Account createRandomAccount(Role role) {
        Account.Builder b = new Account.Builder()
                .username(DataFactory.getDefaultRandomString() + "@test.com")
                .lastName(DataFactory.getDefaultRandomString())
                .firstName(DataFactory.getDefaultRandomString())
                .role(role)
                .active(true);
        return b.build();
    }

    public void expectSecurityLogin(Account account) {
        expect(securityUtils.getAccountFromSecurity()).andReturn(account);
    }
}
