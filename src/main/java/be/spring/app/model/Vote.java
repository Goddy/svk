package be.spring.app.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by u0090265 on 29/05/16.
 */
@Entity
@Table(name = "vote")
@Inheritance(strategy = InheritanceType.JOINED)
public class Vote extends BaseClass {
    private Account voter;
    private Poll poll;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "voter", nullable = false)
    public Account getVoter() {
        return voter;
    }

    public void setVoter(Account voter) {
        this.voter = voter;
    }

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="poll_id")
    @NotNull
    public Poll getPoll() {
        return poll;
    }

    public void setPoll(Poll poll) {
        this.poll = poll;
    }
}
