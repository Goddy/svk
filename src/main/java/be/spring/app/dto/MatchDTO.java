package be.spring.app.dto;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by u0090265 on 10/2/15.
 */
public class MatchDTO extends DTOBaseClass {
    String date;
    String hour;
    String homeTeam;
    String awayTeam;
    String atGoals;
    String htGoals;
    String status;
    String locationUrl;
    boolean hasDoodle;
    MatchPollDTO poll;
    List<GoalDTO> goals;

    public MatchDTO(Long id, String date, String hour, String homeTeam, String awayTeam, String atGoals, String htGoals, String status, MatchPollDTO matchPollDTO, List<GoalDTO> goals, String locationUrl, boolean hasDoodle) {
        this.date = date;
        this.hour = hour;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.atGoals = atGoals;
        this.htGoals = htGoals;
        this.poll = matchPollDTO;
        this.status = status;
        this.setId(id);
        this.goals = goals;
        this.hasDoodle = hasDoodle;
        this.locationUrl = locationUrl;
    }

    @ApiModelProperty(value = "Goals of this match, orederd", name = "goals")
    public List<GoalDTO> getGoals() {
        return goals;
    }

    public void setGoals(List<GoalDTO> goals) {
        this.goals = goals;
    }


    @ApiModelProperty(value = "Date when match will be played, formatted dd/mm/YYYY", name = "date")
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @ApiModelProperty(value = "Hour when match will be played, formatted HH:mm", name = "hour")
    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    @ApiModelProperty(value = "Name of the hometeam", name = "homeTeam")
    public String getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    @ApiModelProperty(value = "Name of the awayteam", name = "awayTeam")
    public String getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
    }

    @ApiModelProperty(value = "Goals scored by awayteam", name = "atGoals")
    public String getAtGoals() {
        return atGoals;
    }

    public void setAtGoals(String atGoals) {
        this.atGoals = atGoals;
    }

    @ApiModelProperty(value = "Goals scored by hometeam", name = "htGoals")
    public String getHtGoals() {
        return htGoals;
    }

    public void setHtGoals(String htGoals) {
        this.htGoals = htGoals;
    }

    @ApiModelProperty(value = "Motm poll", name = "poll")
    public MatchPollDTO getPoll() {
        return poll;
    }

    public void setPoll(MatchPollDTO poll) {
        this.poll = poll;
    }

    @ApiModelProperty(value = "Match status", name = "status")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @ApiModelProperty(value = "Match location", name = "location")
    public String getLocationUrl() {
        return locationUrl;
    }

    public void setLocationUrl(String locationUrl) {
        this.locationUrl = locationUrl;
    }

    @ApiModelProperty(value = "Match doodle", name = "doodle")
    public boolean isHasDoodle() {
        return hasDoodle;
    }

    public void setHasDoodle(boolean hasDoodle) {
        this.hasDoodle = hasDoodle;
    }
}
