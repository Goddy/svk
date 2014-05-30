package be.spring.spring.model;

import javax.persistence.*;

/**
 * Created by u0090265 on 5/3/14.
 */

@Embeddable
public class Goals {
    private int order;
    private Account scorer;
    private Account assist;

    public Goals() {
    }

    @Column(name = "goal_order")
    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "scorer", nullable = false)
    public Account getScorer() {
        return scorer;
    }

    public void setScorer(Account scorer) {
        this.scorer = scorer;
    }

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "assist", nullable = true)
    public Account getAssist() {
        return assist;
    }

    public void setAssist(Account assist) {
        this.assist = assist;
    }
}
