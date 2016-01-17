package be.spring.app.service;

import be.spring.app.common.JUnitTest;
import be.spring.app.controller.DataFactory;
import be.spring.app.data.MatchStatusEnum;
import be.spring.app.model.*;
import be.spring.app.persistence.AccountDao;
import be.spring.app.persistence.DoodleDao;
import be.spring.app.persistence.MatchesDao;
import be.spring.app.utils.HtmlHelper;
import com.google.common.collect.Sets;
import org.easymock.EasyMock;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.MessageSource;

import java.util.Locale;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

public class DoodleServiceImplTest extends JUnitTest {
    DoodleDao doodleDao = EasyMock.createStrictMock(DoodleDao.class);
    AccountDao accountDao = EasyMock.createStrictMock(AccountDao.class);
    HtmlHelper htmlHelper = EasyMock.createStrictMock(HtmlHelper.class);
    MatchesDao matchesDao = EasyMock.createStrictMock(MatchesDao.class);
    MailService mailService = EasyMock.createStrictMock(MailService.class);
    MessageSource messageSource = EasyMock.createStrictMock(MessageSource.class);

    DoodleServiceImpl doodleService;

    @Before
    public void setUp() throws Exception {
        doodleService = new DoodleServiceImpl(doodleDao, accountDao, htmlHelper, matchesDao, messageSource, mailService);
        resetAll();
    }

    @Test
    public void testChangePresenceNormalUser() throws Exception {
        Match m = DataFactory.createMatch();
        Account a = DataFactory.createAccount();
        expect(matchesDao.findOne(m.getId())).andReturn(m);
        expect(doodleDao.save(anyObject(Doodle.class))).andReturn(new Doodle());

        replayAll();

        Presence presence = doodleService.changePresence(a, a.getId(), m.getId());

        assertEquals(a, presence.getAccount());
        assertTrue(presence.isPresent());

        verifyAll();
    }

    @Test
    public void testChangePresenceAdminUserMatchPlayed() throws Exception {
        Match m = DataFactory.createMatch();
        m.setStatus(MatchStatusEnum.PLAYED);
        Account a = DataFactory.createAccount();
        a.setRole(Role.ADMIN);
        expect(matchesDao.findOne(m.getId())).andReturn(m);
        expect(doodleDao.save(anyObject(Doodle.class))).andReturn(new Doodle());

        replayAll();

        Presence presence = doodleService.changePresence(a, a.getId(), m.getId());

        assertEquals(a, presence.getAccount());
        assertTrue(presence.isPresent());

        verifyAll();
    }

    @Test(expected = RuntimeException.class)
    public void testChangePresenceNormalUserMatchPassed() throws Exception {
        Match m = DataFactory.createMatch();
        m.setStatus(MatchStatusEnum.PLAYED);
        Account a = DataFactory.createAccount();
        expect(matchesDao.findOne(m.getId())).andReturn(m);

        replayAll();

        doodleService.changePresence(a, a.getId(), m.getId());
    }

    @Test
    public void testChangePresenceAdminUser() throws Exception {
        Match m = DataFactory.createMatch();
        Account a = DataFactory.createAccount();
        Account b = DataFactory.createAccount();
        a.setRole(Role.ADMIN);
        expect(matchesDao.findOne(m.getId())).andReturn(m);
        expect(accountDao.findOne(b.getId())).andReturn(b);
        expect(doodleDao.save(anyObject(Doodle.class))).andReturn(new Doodle());

        replayAll();

        Presence presence = doodleService.changePresence(a, b.getId(), m.getId());

        assertNotEquals(a, presence.getAccount());
        assertEquals(b, presence.getAccount());
        assertTrue(presence.isPresent());

        verifyAll();
    }

    @Test
    public void testChangePresenceNormalUserAlreadyPresent() throws Exception {
        Match m = DataFactory.createMatch();
        Account a = DataFactory.createAccount();
        Presence p = new Presence();
        p.setPresent(true);
        p.setAccount(a);
        m.getMatchDoodle().setPresences(Sets.newHashSet(p));

        expect(matchesDao.findOne(m.getId())).andReturn(m);
        expect(doodleDao.save(anyObject(Doodle.class))).andReturn(new Doodle());

        replayAll();

        Presence presence = doodleService.changePresence(a, a.getId(), m.getId());

        assertEquals(a, presence.getAccount());
        assertFalse(presence.isPresent());

        verifyAll();
    }

    @Test
    public void testChangePresenceAdminUserAlreadyPresent() throws Exception {
        Match m = DataFactory.createMatch();
        Account a = DataFactory.createAccount();
        Account b = DataFactory.createAccount();
        a.setRole(Role.ADMIN);
        Presence p = new Presence();
        p.setPresent(true);
        p.setAccount(b);
        m.getMatchDoodle().setPresences(Sets.newHashSet(p));

        expect(matchesDao.findOne(m.getId())).andReturn(m);
        expect(accountDao.findOne(b.getId())).andReturn(b);
        expect(doodleDao.save(anyObject(Doodle.class))).andReturn(new Doodle());

        replayAll();

        Presence presence = doodleService.changePresence(a, b.getId(), m.getId());

        assertNotEquals(a, presence.getAccount());
        assertEquals(b, presence.getAccount());
        assertFalse(presence.isPresent());

        verifyAll();
    }

    @Test(expected = RuntimeException.class)
    public void testChangePresenceNoAccess() throws Exception {
        Match m = DataFactory.createMatch();
        Account a = DataFactory.createAccount();
        Account b = DataFactory.createAccount();

        replayAll();

        Presence presence = doodleService.changePresence(a, b.getId(), m.getId());

        verifyAll();
    }

    @Test
    public void testSendDoodleNotificationsForSuccess() throws Exception {
        Match m = DataFactory.createMatch();
        Account a = DataFactory.createAccount();
        Account b = DataFactory.createAccount();
        b.getAccountSettings().setSendDoodleNotifications(false);

        //Expect the messageSource to be called
        expect(messageSource.getMessage(eq("email.doodle.subject"), anyObject(String[].class), eq(Locale.ENGLISH))).andReturn("Test");
        expect(messageSource.getMessage(eq("email.doodle.body"), anyObject(String[].class), eq(Locale.ENGLISH))).andReturn("Test");
        expect(mailService.sendMail(eq(a.getUsername()), eq(a.toString()), anyString(), anyString())).andReturn(true);

        replayAll();

        doodleService.sendDoodleNotificationsFor(m, Sets.newHashSet(a, b));

        verifyAll();
    }

    @Test
    public void testSendDoodleNotificationsForMatchTooFarAway() throws Exception {
        Match m = DataFactory.createMatch();
        m.setDate(DateTime.now().plusDays(14));
        Account a = DataFactory.createAccount();
        a.getAccountSettings().setSendDoodleNotifications(true);
        Account b = DataFactory.createAccount();

        replayAll();

        doodleService.sendDoodleNotificationsFor(m, Sets.newHashSet(a, b));

        verifyAll();
    }


    @Test
    public void testSendDoodleNotificationsEnoughPresences() throws Exception {
        Match m = DataFactory.createMatch();
        m.getMatchDoodle().setPresences(Sets.newHashSet(DataFactory.getPresences(13)));
        Account a = DataFactory.createAccount();
        a.getAccountSettings().setSendDoodleNotifications(true);
        Account b = DataFactory.createAccount();

        replayAll();

        doodleService.sendDoodleNotificationsFor(m, Sets.newHashSet(a, b));

        verifyAll();
    }



    @Override
    protected Object[] getMocks() {
        return new Object[]{doodleDao, accountDao, htmlHelper, matchesDao, messageSource, mailService};
    }
}