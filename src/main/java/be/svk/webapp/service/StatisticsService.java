package be.svk.webapp.service;

import be.svk.webapp.data.AccountStatistic;
import be.svk.webapp.data.MatchStatisticsObject;
import be.svk.webapp.model.Account;
import be.svk.webapp.model.Match;

import java.util.List;

/**
 * Created by u0090265 on 6/27/15.
 */
public interface StatisticsService {

    List<MatchStatisticsObject> getGoalsPerPlayerForSeason(long seasonId);

    List<MatchStatisticsObject> getAssistsPerPlayerForSeason(long seasonId);

    List<MatchStatisticsObject> getAssistsFor(Account account, long seasonId);

    List<MatchStatisticsObject> getGoalsFor(Account account, long seasonId);

    AccountStatistic getAccountStatistic(List<Match> matches, Account account);
}
