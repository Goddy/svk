package be.spring.app.service;

import be.spring.app.data.MatchStatisticsObject;
import be.spring.app.model.Account;
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
}
