package be.spring.app.service;

import be.spring.app.model.*;
import be.spring.app.persistence.MatchesDao;
import be.spring.app.persistence.SeasonDao;
import be.spring.app.persistence.TeamDao;
import be.spring.app.utils.HtmlHelper;
import be.spring.app.utils.SecurityUtils;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * Created by u0090265 on 9/12/14.
 */
@Service
public class ConcurrentDataServiceImpl implements ConcurrentDataService {
    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MatchesDao matchesDao;
    @Autowired
    private TeamDao teamDao;
    @Autowired
    private SeasonDao seasonDao;
    @Autowired
    private HtmlHelper htmlHelper;
    @Autowired
    private SecurityUtils securityUtils;

    //Todo: duplicated behaviour, should be avoided
    @Override
    public List<ActionWrapper<Match>> getMatchForSeasonActionWrappers(long seasonId, Account account, Locale locale) {
        Season season = seasonDao.findOne(seasonId);
        List<Match> matches = Collections.synchronizedList(matchesDao.getMatchForSeason(season));

        return matches.parallelStream()
                .map(t -> setMatchHtmlActions(new ActionWrapper<>(t), locale, account))
                .collect(Collectors.toList());
    }

    @Override
    public  List<ActionWrapper<Team>> getTeamsActionWrappers(Account account, Locale locale) {
        List<Team> teams = Collections.synchronizedList(Lists.newArrayList(teamDao.findAll()));

        return teams.parallelStream()
                .map(t -> setTeamHtmlActions(new ActionWrapper<>(t), locale, account))
                .collect(Collectors.toList());
    }

    private ActionWrapper<Match> setMatchHtmlActions(final ActionWrapper<Match> matchActionWrapper, final Locale locale, final Account account) {
        try {
            HashMap<String, String> map = new HashMap<>();
            map.putAll(htmlHelper.getMatchesButtons(matchActionWrapper.getObject(), securityUtils.isAdmin(account), locale));
            //Todo: uncomment after finishing
            map.putAll(htmlHelper.getMatchesAdditions(matchActionWrapper.getObject(), account, locale));
            matchActionWrapper.setAdditions(map);
        }
        catch (Exception e) {
            log.error("Could not create Matches btns. Exception: {}", e.getMessage());
            e.printStackTrace();
        }
        return matchActionWrapper;
    }

    private ActionWrapper<Team> setTeamHtmlActions(final ActionWrapper<Team> teamActionWrapper, final Locale locale, final Account account) {
        teamActionWrapper.setAdditions(htmlHelper.getTeamButtons(teamActionWrapper.getObject(), securityUtils.isAdmin(account), locale));
        return teamActionWrapper;
    }
}
