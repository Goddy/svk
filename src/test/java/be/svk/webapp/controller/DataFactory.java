package be.svk.webapp.controller;

import be.svk.webapp.model.*;
import com.google.common.collect.Lists;
import org.joda.time.DateTime;

import java.util.List;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

/**
 * Created by u0090265 on 9/12/14.
 */
public class DataFactory {
    private static final int DEF_RANDOM_LENGTH = 8;
    private static final int DEF_RANDOM_MAX_LENGTH = 10;
    private static final int DEF_RANDOM_INT_LENGTH = 10;

    public static News createNews(Account account) {
        News news = new News();
        news.setContent(getDefaultRandomString());
        news.setHeader(getDefaultRandomString());
        news.setAccount(account);
        return news;
    }

    public static Match createMatch() {
        Match match = new Match();
        match.setHomeTeam(createTeam());
        match.setAwayTeam(createTeam());
        match.setDate(DateTime.now());
        match.setSeason(createSeason());
        return match;
    }

    public static Account createAccount() {
        Account account = new Account(getDefaultRandomString(), getDefaultRandomString(), getRandomUserName());
        account.setId(new java.util.Random().nextLong());
        return account;
    }

    public static Goal createGoal(Account account, Match match) {
        Goal goal = new Goal();
        goal.setScorer(account);
        goal.setAssist(account);
        goal.setMatch(match);
        goal.setOrder(1);
        return goal;
    }

    public static Season createSeason() {
        Season s = new Season();
        s.setDescription(getDefaultRandomString());
        return s;
    }

    public static Team createTeam() {
        Team team = new Team();
        team.setName(getDefaultRandomString());
        team.setAddress(createAddress());
        return team;
    }

    public static Address createAddress() {
        Address address = new Address();
        address.setAddress(getDefaultRandomString());
        address.setCity(getDefaultRandomString());
        address.setPostalCode(getDefaultRandomNumber());
        return address;
    }

    public static NewsComment createNewsComment(Account account) {
        NewsComment newsComment = new NewsComment();
        newsComment.setAccount(account);
        newsComment.setContent(getDefaultRandomString());
        return newsComment;
    }

    public static List<Presence> getPresences(int count) {
        List<Presence> r = Lists.newArrayList();
        for (int i = 0; i < count; i++) {
            Presence p = new Presence();
            p.setAccount(createAccount());
            p.setPresent(true);
            r.add(p);
        }
        return r;
    }

    public static String getDefaultRandomString() {
        return randomAlphabetic(DEF_RANDOM_LENGTH);
    }

    public static int getDefaultRandomNumber() {
        return (int) (Math.random() * DEF_RANDOM_INT_LENGTH);
    }

    public static String getRandomUserName() {
        return getDefaultRandomString() + "@test.com";
    }
}
