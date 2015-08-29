package be.spring.app.controller;

import be.spring.app.model.Account;
import be.spring.app.persistence.AccountDao;
import net.tanesha.recaptcha.ReCaptcha;
import net.tanesha.recaptcha.ReCaptchaResponse;
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

    @Autowired
    private ReCaptcha reCaptcha;

    ReCaptchaResponse reCaptchaResponse = createNiceMock(ReCaptchaResponse.class);
    String newName = DataFactory.getDefaultRandomString();
    String newFirstName = DataFactory.getDefaultRandomString();
    String newUsername = DataFactory.getRandomUserName();

    @Test
    public void testPostRegistrationFormSucceeds() throws Exception {
        reset(jdbcTemplate, mailService, reCaptcha, reCaptchaResponse);
        executeJdbcStatement();
        expect(mailService.sendPreConfiguredMail(anyString())).andReturn(true);
        expect(reCaptcha.checkAnswer(anyString(), anyString(), anyString())).andReturn(reCaptchaResponse);
        expect(reCaptchaResponse.isValid()).andReturn(true);

        replay(jdbcTemplate, mailService, reCaptcha, reCaptchaResponse);

        MvcResult r = performRegistration(firstName, "'- äïéèçtest ", newUsername, password, password, status().isFound());

        verifyValidation(r, Arrays.asList(model().hasNoErrors()));

        Account account = accountDao.findByUsername(newUsername);
        assertNotNull(account);

        verify(jdbcTemplate, mailService, reCaptcha, reCaptchaResponse);

    }

    @Test
    public void testPostRegistrationFormAccountExists() throws Exception {
        Account account = accountDao.findByUsername(userName);
        reset(reCaptcha, reCaptchaResponse);

        expect(reCaptcha.checkAnswer(anyString(), anyString(), anyString())).andReturn(reCaptchaResponse);
        expect(reCaptchaResponse.isValid()).andReturn(true);
        replay(reCaptcha, reCaptchaResponse);

        MvcResult r = performRegistration(firstName, name, userName, password, password, status().isOk());

        verifyValidation(r, Arrays.asList(model().attributeHasFieldErrors("form", "username")));
        verify(reCaptcha, reCaptchaResponse);
    }

    @Test
    public void testValidation() throws Exception {
        reset(reCaptcha, reCaptchaResponse);
        expect(reCaptcha.checkAnswer(anyString(), anyString(), anyString())).andReturn(reCaptchaResponse);
        expectLastCall().anyTimes();
        expect(reCaptchaResponse.isValid()).andReturn(true);
        expectLastCall().anyTimes();
        replay(reCaptcha, reCaptchaResponse);

        //Password regex errors
        //Length
        MvcResult r = performRegistration(firstName, name, userName, "P3ss", "P3ss", status().isOk());
        verifyValidation(r, Arrays.asList(model().attributeHasFieldErrors("form", "password")));

        //Length too long
        r = performRegistration(firstName, name, userName, "P1ssIamTooLongForAPassword", "P1ssIamTooLongForAPassword", status().isOk());
        verifyValidation(r, Arrays.asList(model().attributeHasFieldErrors("form", "password")));

        //No Numbers
        r = performRegistration(firstName, name, userName, "IdontContainNrs", "IdontContainNrs", status().isOk());
        verifyValidation(r, Arrays.asList(model().attributeHasFieldErrors("form", "password")));

        //No capitals
        r = performRegistration(firstName, name, userName, "idontc3ntaincap", "idontc3ntaincap", status().isOk());
        verifyValidation(r, Arrays.asList(model().attributeHasFieldErrors("form", "password")));

        //passwords do not match
        r = performRegistration(firstName, name, userName, password, "dummy", status().isOk());
        verifyValidation(r, Arrays.asList(model().attributeHasFieldErrors("form", "confirmPassword")));

        //Invalid name and lastName
        r = performRegistration("$test", "$test", userName, password, password, status().isOk());
        verifyValidation(r, Arrays.asList(model().attributeHasFieldErrors("form", "firstName"), model().attributeHasFieldErrors("form", "lastName")));

        //Invalid name and lastName
        r = performRegistration("a.", "a.", userName, password, password, status().isOk());
        verifyValidation(r, Arrays.asList(model().attributeHasFieldErrors("form", "firstName"), model().attributeHasFieldErrors("form", "lastName")));
    }

    @Test
    public void testEmptyValidation() throws Exception {
        /**
         * Something's wrong with this test.
         reset(reCaptcha, reCaptchaResponse);
         expect(reCaptcha.checkAnswer(anyString(), anyString(), anyString())).andReturn(reCaptchaResponse);
         expect(reCaptchaResponse.isValid()).andReturn(true);
         replay(reCaptcha, reCaptchaResponse);
         MvcResult r = performRegistration("", "", "", "", "", status().isOk());

        verifyValidation(r, Arrays.asList(model().attributeHasFieldErrors("Account", "firstName"),
                model().attributeHasFieldErrors("Account", "lastName"),
                model().attributeHasFieldErrors("Account", "confirmPassword"),
                model().attributeHasFieldErrors("Account", "password"),
                model().attributeHasFieldErrors("Account", "userName")
        ));
         verify(reCaptcha, reCaptchaResponse);
         **/
    }

    @Test
    public void testUpdateAccountDetails() throws Exception {
        Account existingAccount = createRandomAccount();
        //Save the account!
        accountDao.save(existingAccount);
        reset(securityUtils, jdbcTemplate);
        expect(securityUtils.getAccountFromSecurity()).andReturn(existingAccount);
        expectQueryForPassword("notEmpty");
        replay(securityUtils, jdbcTemplate);

        performUpdate(newFirstName, newName, newUsername, status().isOk());

        Account newAccount = accountDao.findOne(existingAccount.getId());
        assertEquals(WordUtils.capitalize(newFirstName), newAccount.getFirstName());
        assertEquals(WordUtils.capitalize(newName), newAccount.getLastName());
        assertEquals(newUsername, newAccount.getUsername());
        verify(securityUtils, jdbcTemplate);
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
                .param("recaptcha_challenge_field", "test")
                .param("recaptcha_response_field", "test")
                .accept(MediaType.TEXT_HTML))
                .andDo(print())
                .andExpect(status)
                .andReturn();
    }

    private void executeJdbcStatement() {
        expect(jdbcTemplate.update(anyString(), anyString(), anyString())).andReturn(1);
    }
    private void expectQueryForPassword(String expectedPassword) {
        expect(jdbcTemplate.queryForObject(anyString(), eq(String.class), anyObject(long.class))).andReturn(expectedPassword);
    }

}
