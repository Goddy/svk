package be.svk.webapp.service;

import be.svk.webapp.controller.AbstractTest;
import be.svk.webapp.controller.DataFactory;
import be.svk.webapp.data.MatchStatisticsObject;
import be.svk.webapp.model.Account;
import be.svk.webapp.model.Match;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"/jUnit.xml"})
@Transactional
public class StatisticsServiceImplTest extends AbstractTest {

    @Autowired
    StatisticsService statisticsService;

    private final static Integer ONE = 1;

    @Test
    public void testGetGoalsPerPlayerForSeason() throws Exception {
        Account account = accountDao.findByUsername(userName);
        Match m = DataFactory.createMatch();
        m.setGoals(Sets.newTreeSet(Lists.newArrayList(DataFactory.createGoal(account, m))));

        matchesDao.save(m);

        List<MatchStatisticsObject> r = statisticsService.getGoalsPerPlayerForSeason(m.getSeason().getId());
        MatchStatisticsObject mso = r.get(0);

        assertEquals(ONE.intValue(), r.size());
        assertEquals(account.getId(), mso.getId());
        assertEquals((Long) ONE.longValue(), mso.getNumber());
        assertEquals(account.getFirstName() + " " + account.getLastName(), mso.getName());
    }

    @Test
    public void testGetAssistsPerPlayerForSeason() throws Exception {
        Account account = accountDao.findByUsername(userName);
        Match m = DataFactory.createMatch();
        m.setGoals(Sets.newTreeSet(Lists.newArrayList(DataFactory.createGoal(account, m))));

        matchesDao.save(m);

        List<MatchStatisticsObject> r = statisticsService.getAssistsPerPlayerForSeason(m.getSeason().getId());
        MatchStatisticsObject mso = r.get(0);

        assertEquals(ONE.intValue(), r.size());
        assertEquals(account.getId(), mso.getId());
        assertEquals((Long) ONE.longValue(), mso.getNumber());
        assertEquals(account.getFirstName() + " " + account.getLastName(), mso.getName());
    }
}