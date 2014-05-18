package be.spring.spring.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * User: Tom De Dobbeleer
 * Date: 1/17/14
 * Time: 9:37 PM
 * Remarks: none
 */

@NamedQueries({
        @NamedQuery(name = "findSeasonById", query = "from Season where id = :id"),
        @NamedQuery(name = "getLastSeasons", query = "from Season order by description desc")
})
@Entity
@Table(name = "season")
public class Season {
    private long id;
    private String description;

    public Season() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @NotNull
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
