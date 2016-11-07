package be.svk.webapp.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by u0090265 on 29/05/16.
 */
@Entity
@Table(name = "vote")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Vote<T> extends BaseClass {
    protected T answer;
    private Account voter;
    private Poll poll;

    public Vote() {}

    @Transient
    public abstract T getAnswer();

    public void setAnswer(T answer) {
        this.answer = answer;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vote<?> vote = (Vote<?>) o;

        if (voter != null ? !voter.equals(vote.voter) : vote.voter != null) return false;
        return poll != null ? poll.equals(vote.poll) : vote.poll == null;

    }

    @Override
    public int hashCode() {
        int result = voter != null ? voter.hashCode() : 0;
        result = 31 * result + (poll != null ? poll.hashCode() : 0);
        return result;
    }
}
