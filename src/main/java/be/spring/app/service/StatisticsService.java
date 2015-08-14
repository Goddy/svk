package be.spring.app.service;

import be.spring.app.data.MatchStatisticsObject;
import be.spring.app.model.Account;

import java.util.List;

/**
 * Created by u0090265 on 6/27/15.
 */
public interface StatisticsService {

    List<MatchStatisticsObject> getGoalsPerPlayerForSeason(long seasonId);

    List<MatchStatisticsObject> getAssistsPerPlayerForSeason(long seasonId);

    List<MatchStatisticsObject> getAssistsFor(Account account, long seasonId);

    List<MatchStatisticsObject> getGoalsFor(Account account, long seasonId);
}
