package be.spring.spring.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

/**
 * User: Tom De Dobbeleer
 * Date: 1/17/14
 * Time: 9:36 PM
 * Remarks: none
 */

@NamedQueries({
        @NamedQuery(name = "findMatchById", query = "from Match where id = :id"),
        @NamedQuery(name = "getMatchesForSeason", query = "from Match where season = :season order by date desc")
})

@Entity
@Table(name = "matches")
public class Match {
    private long id;
    private Date date;
    private Season season;
    private Team homeTeam;
    private Team awayTeam;
    private int atGoals;
    private int htGoals;
    private Set<Goals> goals;

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
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @NotNull
    @OneToOne(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    @JoinColumn(name="season_id",insertable=true, updatable=true, nullable=false,unique=true)
    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    @NotNull
    @OneToOne(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    @JoinColumn(name="hometeam_id",insertable=true, updatable=true, nullable=false,unique=true)
    public Team getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(Team homeTeam) {
        this.homeTeam = homeTeam;
    }

    @NotNull
    @OneToOne(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    @JoinColumn(name="awayteam_id",insertable=true, updatable=true, nullable=false,unique=true)
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

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "goals", joinColumns = @JoinColumn(name = "match_id"))
    @OrderColumn
    public Set<Goals> getGoals() {
        return goals;
    }

    public void setGoals(Set<Goals> goals) {
        this.goals = goals;
    }
}
