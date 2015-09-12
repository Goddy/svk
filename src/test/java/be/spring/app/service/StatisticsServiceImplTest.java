package be.spring.app.service;

import be.spring.app.controller.AbstractTest;
import be.spring.app.controller.DataFactory;
import be.spring.app.data.MatchStatisticsObject;
import be.spring.app.model.Account;
import be.spring.app.model.Match;
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
        m.setGoals(Sets.newHashSet(DataFactory.createGoal(account, m)));

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
        m.setGoals(Sets.newHashSet(DataFactory.createGoal(account, m)));

        matchesDao.save(m);

        List<MatchStatisticsObject> r = statisticsService.getAssistsPerPlayerForSeason(m.getSeason().getId());
        MatchStatisticsObject mso = r.get(0);

        assertEquals(ONE.intValue(), r.size());
        assertEquals(account.getId(), mso.getId());
        assertEquals((Long) ONE.longValue(), mso.getNumber());
        assertEquals(account.getFirstName() + " " + account.getLastName(), mso.getName());
    }
}