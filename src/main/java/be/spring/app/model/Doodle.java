package be.spring.app.model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by u0090265 on 10/1/14.
 */
@Entity
@Table(name = "doodle")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Doodle {

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

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "doodle_id")
    public List<Presence> getPresences() {
        return presences;
    }

    public void setPresences(List<Presence> presences) {
        this.presences = presences;
    }
}
