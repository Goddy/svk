package be.spring.app.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by u0090265 on 10/2/15.
 */
public class MatchDTO {
    String date;
    String hour;
    String homeTeam;
    String awayTeam;
    String atGoals;
    String htGoals;

    public MatchDTO(String date, String hour, String homeTeam, String awayTeam, String atGoals, String htGoals) {
        this.date = date;
        this.hour = hour;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.atGoals = atGoals;
        this.htGoals = htGoals;
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
}
