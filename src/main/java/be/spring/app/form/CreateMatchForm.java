package be.spring.app.form;

import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Created by u0090265 on 5/10/14.
 */
public class CreateMatchForm {
    private long awayTeam;
    private long homeTeam;
    @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
    private DateTime date;
    private long season;

    public long getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(long awayTeam) {
        this.awayTeam = awayTeam;
    }

    public long getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(long homeTeam) {
        this.homeTeam = homeTeam;
    }

    public DateTime getDate() {
        return date;
    }

    public void setDate(DateTime date) {
        this.date = date;
    }

    public long getSeason() {
        return season;
    }

    public void setSeason(long season) {
        this.season = season;
    }
}
