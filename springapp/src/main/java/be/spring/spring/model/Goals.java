package be.spring.spring.model;

import javax.persistence.*;

/**
 * Created by u0090265 on 5/3/14.
 */

@Embeddable
public class Goals {
    private int order;
    private Account player;


    @Column(name = "order")
    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "player", insertable = true, updatable = true, nullable = false, unique = true)
    public Account getPlayer() {
        return player;
    }

    public void setPlayer(Account player) {
        this.player = player;
    }
}
