package be.spring.spring.form;

import be.spring.spring.model.Goal;

import java.util.List;

/**
 * Created by u0090265 on 5/30/14.
 */
public class ChangeResultForm {
    public static class FormGoal {
        private int scorer, assist, order;

        public int getOrder() {
            return order;
        }

        public void setOrder(int order) {
            this.order = order;
        }

        public int getAssist() {
            return assist;
        }

        public void setAssist(int assist) {
            this.assist = assist;
        }

        public void setScorer(int scorer) {
            this.scorer = scorer;
        }

        public int getScorer() {
            return scorer;
        }
    }

    private int htGoals;
    private int atGoals;
    private List<FormGoal> goals;

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
