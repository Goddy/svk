package be.spring.app.model;

import com.google.common.collect.Lists;

import javax.persistence.*;
import java.util.List;

/**
 * Created by u0090265 on 10/1/14.
 */
@Entity
@Table(name = "doodle")
public class Doodle {

    private long id;
    private List<Presence> presences;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "doodle_id")
    public List<Presence> getPresences() {
        if (presences == null) presences = Lists.newArrayList();
        return presences;
    }

    public void setPresences(List<Presence> presences) {
        this.presences = presences;
    }

    @Transient
    public boolean isPresent(final Account account) {
        for (Presence p : getPresences()) {
            if (p.getAccount().equals(account)) {
                return p.isPresent();
            }
        }
        return false;
    }
}
