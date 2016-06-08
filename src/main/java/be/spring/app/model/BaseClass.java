package be.spring.app.model;

import javax.persistence.*;

/**
 * Created by u0090265 on 08/06/16.
 */
@MappedSuperclass
public class BaseClass {
    protected Long id;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
