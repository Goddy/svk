package be.svk.webapp.service;

import be.svk.webapp.data.AccountStatistic;
import be.svk.webapp.data.MatchStatisticsObject;
import be.svk.webapp.data.MatchStatusEnum;
import be.svk.webapp.model.Account;
import be.svk.webapp.model.Goal;
import be.svk.webapp.model.Match;
import be.svk.webapp.model.Presence;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * Created by u0090265 on 6/27/15.
 */
@Service
public class StatisticsServiceImpl implements StatisticsService {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<MatchStatisticsObject> getGoalsPerPlayerForSeason(long seasonId) {
        Query query = em.createNativeQuery("SELECT  C.id as ID, COUNT(B.scorer) as COUNT, " +
                "CONCAT(C.firstName, ' ', C.lastName) as NAME " +
                "FROM matches A LEFT JOIN goals B ON (A.id = B.match_id) " +
                "left join account C ON (B.scorer = C.id) " +
                "where B.scorer IS NOT NULL AND A.season_id = " + seasonId + " group by B.scorer");
        return map(query.getResultList());
    }

    @Override
    public List<MatchStatisticsObject> getAssistsPerPlayerForSeason(long seasonId) {
        Query query = em.createNativeQuery("SELECT C.id as ID, COUNT(B.assist) as COUNT, " +
                "CONCAT(C.firstName, ' ', C.lastName) as NAME " +
                "FROM matches A LEFT JOIN goals B ON (A.id = B.match_id) " +
                "left join account C ON (B.assist = C.id) " +
                "where B.assist IS NOT NULL AND A.season_id = " + seasonId + " group by B.assist");
        return map(query.getResultList());
    }

    @Override
    public List<MatchStatisticsObject> getAssistsFor(Account account, long seasonId) {
        return filterFor(getAssistsPerPlayerForSeason(seasonId), account);
    }

    @Override
    public List<MatchStatisticsObject> getGoalsFor(Account account, long seasonId) {
        return filterFor(getGoalsPerPlayerForSeason(seasonId), account);
    }

    private List<MatchStatisticsObject> filterFor(List<MatchStatisticsObject> oldList, Account account) {
        List<MatchStatisticsObject> newList = Lists.newArrayList();
        for (MatchStatisticsObject o : oldList) {
            if (o.getId().equals(account.getId())) {
                newList.add(o);
            }
        }
        return newList;
    }

    private List<MatchStatisticsObject> map(List<Object[]> result) {
        List<MatchStatisticsObject> l = Lists.newArrayList();
        for (Object[] o : result) {
            MatchStatisticsObject m = new MatchStatisticsObject(Long.parseLong(o[0].toString()), Long.parseLong(o[1].toString()), (String) o[2]);
            l.add(m);
        }
        return l;
    }

    @Override
    public AccountStatistic getAccountStatistic(List<Match> matches, Account account) {
        AccountStatistic accountStatistic = new AccountStatistic(account);
        setMatchData(matches, account, accountStatistic);
        return accountStatistic;
    }

    private void setMatchData(List<Match> matches, Account account, AccountStatistic accountStatistic) {
        int goals = 0;
        int assists = 0;
        int presences = 0;
        for (Match match : matches) {
            //Only gather info on played match
            if (match.getStatus().equals(MatchStatusEnum.PLAYED)) {
                if (match.getMatchDoodle().isPresent(account).equals(Presence.PresenceType.PRESENT)) presences++;
                for (Goal goal : match.getGoals()) {
                    if (goal.getScorer() != null && goal.getScorer().equals(account)) {
                        goals++;
                    }
                    if (goal.getAssist() != null && goal.getAssist().equals(account)) {
                        assists++;
                    }
                }
            }
        }
        accountStatistic.setGoals(goals);
        accountStatistic.setAssists(assists);
        accountStatistic.setPlayed(presences);
    }
}
