package be.spring.app.form;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by u0090265 on 5/30/14.
 */
public class ChangeResultForm {
    public long getMatchId() {
        return matchId;
    }

    public void setMatchId(long matchId) {
        this.matchId = matchId;
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

        public void setScorer(String scorer) {
            this.scorer = scorer;
        }

        public String getScorer() {
            return scorer;
        }
    }

    private int htGoals;
    private int atGoals;
    private long matchId;
    private List<FormGoal> goals = new ArrayList<>();

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
}
