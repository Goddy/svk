package be.spring.app.controller;

import be.spring.app.model.Account;
import be.spring.app.model.Role;
import be.spring.app.persistence.NewsDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.easymock.EasyMock.reset;

/**
 * Created by u0090265 on 9/12/14.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations={"/test-applicationContext.xml"})
@Transactional
public class NewsControllerTest extends AbstractTest {
    Account userAccount;
    Account adminAccount;

    @Autowired
    private NewsDao newsDao;

    @Before
    public void setUp() {
        userAccount = createRandomAccount();
        adminAccount = createRandomAccount(Role.ADMIN);
    }

    @org.junit.Test
    public void testGetNewsPage() throws Exception {

    }

    @org.junit.Test
    public void testGetNewsItem() throws Exception {

    }

    @org.junit.Test
    public void testDeleteNewsItem() throws Exception {

    }

    @org.junit.Test
    public void testGetPaged() throws Exception {

    }

    @org.junit.Test
    public void testSearch() throws Exception {

    }

    @org.junit.Test
    public void testSearchTerm() throws Exception {

    }

    @org.junit.Test
    public void testGetNews() throws Exception {

    }

    @org.junit.Test
    public void testAddGet() throws Exception {

    }

    @org.junit.Test
    public void testCreateNews() throws Exception {

    }

    @org.junit.Test
    public void testUpdateNews() throws Exception {

    }

    @Test
    public void testCreateComment() throws Exception {
        reset(securityUtils);
        expectSecurityLogin(userAccount);
    }
}
