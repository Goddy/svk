package be.spring.app.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by u0090265 on 29/05/16.
 */
@Entity
@Table(name = "vote")
@Inheritance(strategy = InheritanceType.JOINED)
public class Vote {
    private Long id;
    private Account voter;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
