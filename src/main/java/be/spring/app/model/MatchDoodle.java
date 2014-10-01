package be.spring.app.model;

import javax.persistence.*;

/**
 * Created by u0090265 on 10/1/14.
 */
@Entity
@Table(name = "match_doodle")
public class MatchDoodle extends Doodle {
    private Match match;

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="match_id")
    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }
}
