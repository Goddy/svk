package be.svk.webapp.form;

import be.svk.webapp.data.MatchStatusEnum;
import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by u0090265 on 5/30/14.
 */
public class ChangeResultForm {
    private long awayTeam;
    private long homeTeam;
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
    private DateTime date;
    private long season;
    private int htGoals;
    private int atGoals;
    private long matchId;
    private MatchStatusEnum status = MatchStatusEnum.NOT_PLAYED;
    private String statusText;
    private List<FormGoal> goals = new ArrayList<>();

    public long getMatchId() {
        return matchId;
    }

    public void setMatchId(long matchId) {
        this.matchId = matchId;
    }

    public String getStatusText() {
        return statusText;
    }

    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    public int getHtGoals() {
        return htGoals;
    }

    public void setHtGoals(int htGoals) {
        this.htGoals = htGoals;
    }

    public int getAtGoals() {
        return atGoals;
    }

    public void setAtGoals(int atGoals) {
        this.atGoals = atGoals;
    }

    public List<FormGoal> getGoals() {
        return goals;
    }

    public void setGoals(List<FormGoal> goals) {
        this.goals = goals;
    }

    public long getSeason() {
        return season;
    }

    public void setSeason(long season) {
        this.season = season;
    }

    public long getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(long homeTeam) {
        this.homeTeam = homeTeam;
    }

    public long getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(long awayTeam) {
        this.awayTeam = awayTeam;
    }

    public DateTime getDate() {
        return date;
    }

    public void setDate(DateTime date) {
        this.date = date;
    }

    public MatchStatusEnum getStatus() {
        return status;
    }

    public void setStatus(MatchStatusEnum matchStatus) {
        this.status = matchStatus;
    }

    public static class FormGoal {
        private int order;
        private String scorer, assist;

        public int getOrder() {
            return order;
        }

        public void setOrder(int order) {
            this.order = order;
        }

        public String getAssist() {
            return assist;
        }

        public void setAssist(String assist) {
            this.assist = assist;
        }

        public String getScorer() {
            return scorer;
        }

        public void setScorer(String scorer) {
            this.scorer = scorer;
        }
    }
}
