package be.spring.app.model;

import com.google.common.collect.Sets;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by u0090265 on 10/1/14.
 */
@Entity
@Table(name = "doodle")
public class Doodle {

    private long id;
    private Set<Presence> presences;

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
    public Set<Presence> getPresences() {
        if (presences == null) presences = Sets.newHashSet();
        return presences;
    }

    public void setPresences(Set<Presence> presences) {
        this.presences = presences;
    }

    @Transient
    public Presence.PresenceType isPresent(final Account account) {
        if (account == null) return Presence.PresenceType.ANONYMOUS;
        for (Presence p : getPresences()) {
            if (p.getAccount().equals(account)) {
                return p.isPresent() ? Presence.PresenceType.PRESENT : Presence.PresenceType.NOT_PRESENT;
            }
        }
        return Presence.PresenceType.NOT_FILLED_IN;
    }

    @Transient
    public int countPresences() {
        int i = 0;
        for (Presence p : getPresences()) {
            if (p.isPresent()) {
                i++;
            }
        }
        return i;
    }

    public Presence getPresenceFor(Account account) {
        if (presences == null) return null;
        for (Presence p : getPresences()) {
            if (p.getAccount().equals(account)) {
                return p;
            }
        }
        return null;
    }
}
