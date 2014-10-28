package be.spring.app.controller;

import be.spring.app.model.Account;
import be.spring.app.persistence.AccountDao;
import org.apache.commons.lang3.text.WordUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.test.context.support.WithSecurityContextTestExecutionListener;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.ServletTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by u0090265 on 9/25/14.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations={"/jUnit.xml"})
@Transactional
@TestExecutionListeners(listeners={ServletTestExecutionListener.class,
        DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        WithSecurityContextTestExecutionListener.class})
public class AccountControllerTest extends AbstractTest {
    @Autowired
    AccountDao accountDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    String newName = DataFactory.getDefaultRandomString();
    String newFirstName = DataFactory.getDefaultRandomString();
    String newUsername = DataFactory.getRandomUserName();

    @Test
    public void testPostRegistrationFormSucceeds() throws Exception {
        reset(jdbcTemplate, mailService);
        executeJdbcStatement();
        mailService.sendPreConfiguredMail(anyString());

        replay(jdbcTemplate, mailService);

        MvcResult r = performRegistration(firstName, name, newUsername, password, password, status().isFound());

        verifyValidation(r, Arrays.asList(model().hasNoErrors()));

        Account account = accountDao.findByUsername(newUsername);
        assertNotNull(account);

        verify(jdbcTemplate, mailService);

    }

    @Test
    public void testPostRegistrationFormAccountExists() throws Exception {
        //Try to register with account that already exists (DB populator)
        MvcResult r = performRegistration(firstName, name, userName, password, password, status().isOk());

        verifyValidation(r, Arrays.asList(model().attributeHasFieldErrors("Account", "username")));
    }

    @Test
    public void testPasswordValidation() throws Exception {
        //Password regex errors
        //Length
        MvcResult r = performRegistration(firstName, name, userName, "P3ss", "P3ss", status().isOk());
        verifyValidation(r, Arrays.asList(model().attributeHasFieldErrors("Account", "password")));

        //Length too long
        r = performRegistration(firstName, name, userName, "P1ssIamTooLongForAPassword", "P1ssIamTooLongForAPassword", status().isOk());
        verifyValidation(r, Arrays.asList(model().attributeHasFieldErrors("Account", "password")));

        //No Numbers
        r = performRegistration(firstName, name, userName, "IdontContainNrs", "IdontContainNrs", status().isOk());
        verifyValidation(r, Arrays.asList(model().attributeHasFieldErrors("Account", "password")));

        //No capitals
        r = performRegistration(firstName, name, userName, "idontc3ntaincap", "idontc3ntaincap", status().isOk());
        verifyValidation(r, Arrays.asList(model().attributeHasFieldErrors("Account", "password")));

        //passwords do not match
        r = performRegistration(firstName, name, userName, password, "dummy", status().isOk());
        verifyValidation(r, Arrays.asList(model().attributeHasFieldErrors("Account", "password")));

    }

    @Test(expected = org.springframework.web.util.NestedServletException.class)
    public void testNullAndEmptyValidation() throws Exception {
        MvcResult r = performRegistration(null, null, null, null, null, status().isOk());
        verifyValidation(r, Arrays.asList(model().attributeHasFieldErrors("Account", "firstName"),
                model().attributeHasFieldErrors("Account", "lastName"),
                model().attributeHasFieldErrors("Account", "confirmPassword"),
                model().attributeHasFieldErrors("Account", "password"),
                model().attributeHasFieldErrors("Account", "userName")
        ));

        r = performRegistration("", "", "", "", "", status().isOk());
        verifyValidation(r, Arrays.asList(model().attributeHasFieldErrors("Account", "firstName"),
                model().attributeHasFieldErrors("Account", "lastName"),
                model().attributeHasFieldErrors("Account", "confirmPassword"),
                model().attributeHasFieldErrors("Account", "password"),
                model().attributeHasFieldErrors("Account", "userName")
        ));
    }

    @Test
    public void testUpdateAccountDetails() throws Exception {
        Account existingAccount = createRandomAccount();
        reset(securityUtils);
        expect(securityUtils.getAccountFromSecurity()).andReturn(existingAccount);
        replay(securityUtils);

        performUpdate(newFirstName, newName, newUsername, status().isOk());

        Account newAccount = accountDao.findOne(existingAccount.getId());
        assertEquals(WordUtils.capitalize(newFirstName), newAccount.getFirstName());
        assertEquals(WordUtils.capitalize(newName), newAccount.getLastName());
        assertEquals(newUsername, newAccount.getUsername());
        verify(securityUtils);
    }

    @Test
    public void testGetAccountDetails() throws Exception {

    }

    @Test
    public void testUpdatePassword() throws Exception {

    }

    private MvcResult performUpdate(String firstName, String lastName, String username, ResultMatcher status) throws Exception {
        return mockMvc.perform(post("/account/update_details.html")
                .param("firstName", firstName)
                .param("lastName", lastName)
                .param("username", username)
                .with(user("user").roles("USER"))
                .accept(MediaType.TEXT_PLAIN))
                .andDo(print())
                .andExpect(status)
                .andReturn();
    }


    private MvcResult performRegistration(String firstName, String lastName, String username, String password, String repeatPassword, ResultMatcher status) throws Exception {
        return mockMvc.perform(post("/account/register")
                .param("firstName", firstName)
                .param("lastName", lastName)
                .param("password", password)
                .param("confirmPassword", repeatPassword)
                .param("username", username)
                .accept(MediaType.TEXT_HTML))
                .andDo(print())
                .andExpect(status)
                .andReturn();
    }

    private void executeJdbcStatement() {
        expect(jdbcTemplate.update(anyString(), anyString(), anyString())).andReturn(1);
    }

}