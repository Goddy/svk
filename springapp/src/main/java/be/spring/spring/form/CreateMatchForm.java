package be.spring.spring.form;

import be.spring.spring.model.Season;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by u0090265 on 5/10/14.
 */
public class CreateMatchForm {
    private int awayTeam;
    private int homeTeam;
    private Date date;
    private Season season;

    @NotNull
    @NotEmpty(message = "{validation.notempty.message}")
    public int getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(int awayTeam) {
        this.awayTeam = awayTeam;
    }

    @NotNull
    @NotEmpty(message = "{validation.notempty.message}")
    public int getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(int homeTeam) {
        this.homeTeam = homeTeam;
    }

    @NotNull
    @NotEmpty(message = "{validation.notempty.message}")
    @DateTimeFormat
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @NotNull
    @NotEmpty(message = "{validation.notempty.message}")
    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }
}
