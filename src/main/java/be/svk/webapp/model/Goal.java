package be.svk.webapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * Created by u0090265 on 5/3/14.
 */

@Entity
@Table(name = "goals")
public class Goal extends BaseClass implements Comparable<Goal> {
    private int order;
    private Account scorer;
    private Account assist;
    private Match match;

    public Goal() {
    }

    @Column(name = "goal_order")
    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "scorer", nullable = true)
    public Account getScorer() {
        return scorer;
    }

    public void setScorer(Account scorer) {
        this.scorer = scorer;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "assist", nullable = true)
    public Account getAssist() {
        return assist;
    }

    public void setAssist(Account assist) {
        this.assist = assist;
    }

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "match_id", referencedColumnName = "id")
    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    @Override
    public int compareTo(Goal o) {
        return Integer.compare(order, o.order);
    }
}
