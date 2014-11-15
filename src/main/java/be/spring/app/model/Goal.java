package be.spring.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * Created by u0090265 on 5/3/14.
 */

@Entity
@Table(name = "goals")
public class Goal {
    private Long id;
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

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "MATCH_ID", referencedColumnName = "ID")
    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }
}
