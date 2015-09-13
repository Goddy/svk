package be.spring.app.model;

import be.spring.app.utils.GeneralUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * User: Tom De Dobbeleer
 * Date: 1/17/14
 * Time: 9:36 PM
 * Remarks: none
 */

@NamedQueries({
        @NamedQuery(name = "findMatchById", query = "from Match where id = :id"),
        @NamedQuery(name = "getMatchesForSeason", query = "from Match where season = :season order by date desc"),
        @NamedQuery(name = "getMatchesForTeam", query = "from Match where homeTeam = :team OR awayTeam = :team order by date desc")
})

@Entity
@Table(name = "matches")
public class Match {
    private long id;
    private boolean played = false;
    private DateTime date;
    private Season season;
    private Team homeTeam;
    private Team awayTeam;
    private News news;
    private int atGoals;
    private int htGoals;
    private Set<Goal> goals;
    private Doodle matchDoodle;

    public Match() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @NotNull
    @Column(name = "date")
    @JsonIgnore
    @org.hibernate.annotations.Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    public DateTime getDate() {
        return date;
    }

    public void setDate(DateTime date) {
        this.date = date;
    }

    @Transient
    public String getStringDate() {
        return GeneralUtils.convertToStringDate(this.date);
    }

    @Transient
    public String getStringHour() {
        return GeneralUtils.convertToStringHour(this.date);
    }

    @JsonIgnore
    @NotNull
    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "season_id")
    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    @NotNull
    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "hometeam_id", insertable = true, updatable = true, nullable = false)
    public Team getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(Team homeTeam) {
        this.homeTeam = homeTeam;
    }

    @NotNull
    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "awayteam_id", insertable = true, updatable = true, nullable = false)
    public Team getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(Team awayTeam) {
        this.awayTeam = awayTeam;
    }

    @Column(name = "atGoals")
    public int getAtGoals() {
        return atGoals;
    }

    public void setAtGoals(int atGoals) {
        this.atGoals = atGoals;
    }

    @Column(name = "htGoals")
    public int getHtGoals() {
        return htGoals;
    }

    public void setHtGoals(int htGoals) {
        this.htGoals = htGoals;
    }

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "match")
    public Set<Goal> getGoals() {
        return goals;
    }

    public void setGoals(Set<Goal> goals) {
        this.goals = goals;
    }

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "news_id", insertable = true, updatable = true, nullable = true)
    public News getNews() {
        return news;
    }

    public void setNews(News news) {
        this.news = news;
    }

    @Transient
    public String getDescription() {
        return String.format("%s - %s", homeTeam.getName(), awayTeam.getName());
    }

    @NotNull
    @Column(name = "played")
    public boolean isPlayed() {
        return played;
    }

    public void setPlayed(boolean played) {
        this.played = played;
    }

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "doodle_id", insertable = true, updatable = true, nullable = true)
    public Doodle getMatchDoodle() {
        if (matchDoodle == null) matchDoodle = new Doodle();
        return matchDoodle;
    }

    public void setMatchDoodle(Doodle matchDoodle) {
        this.matchDoodle = matchDoodle;
    }
}
