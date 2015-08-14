package be.spring.app.service;

import be.spring.app.common.JUnitTest;
import be.spring.app.controller.DataFactory;
import be.spring.app.model.*;
import be.spring.app.persistence.AccountDao;
import be.spring.app.persistence.DoodleDao;
import be.spring.app.persistence.MatchesDao;
import be.spring.app.utils.HtmlHelper;
import com.google.common.collect.Lists;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.*;

public class DoodleServiceImplTest extends JUnitTest {
    DoodleDao doodleDao = EasyMock.createStrictMock(DoodleDao.class);
    AccountDao accountDao = EasyMock.createStrictMock(AccountDao.class);
    HtmlHelper htmlHelper = EasyMock.createStrictMock(HtmlHelper.class);
    MatchesDao matchesDao = EasyMock.createStrictMock(MatchesDao.class);

    DoodleServiceImpl doodleService;

    @Before
    public void setUp() throws Exception {
        doodleService = new DoodleServiceImpl(doodleDao, accountDao, htmlHelper, matchesDao);
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
        m.getMatchDoodle().setPresences(Lists.newArrayList(p));

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
        m.getMatchDoodle().setPresences(Lists.newArrayList(p));

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

    @Override
    protected Object[] getMocks() {
        return new Object[]{doodleDao, accountDao, htmlHelper, matchesDao};
    }
}