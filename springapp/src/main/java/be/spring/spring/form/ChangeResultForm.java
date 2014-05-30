package be.spring.spring.form;

import be.spring.spring.model.Goals;

import java.util.Set;

/**
 * Created by u0090265 on 5/30/14.
 */
public class ChangeResultForm {
    private String awayTeam;
    private String homeTeam;
    private String htGoals;
    private String atGoals;
    private Set<Goals> goals;

    public String getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public String getHtGoals() {
        return htGoals;
    }

    public void setHtGoals(String htGoals) {
        this.htGoals = htGoals;
    }

    public String getAtGoals() {
        return atGoals;
    }

    public void setAtGoals(String atGoals) {
        this.atGoals = atGoals;
    }

    public Set<Goals> getGoals() {
        return goals;
    }

    public void setGoals(Set<Goals> goals) {
        this.goals = goals;
    }
}
