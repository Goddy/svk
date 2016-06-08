package be.spring.app.model;

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
        @NamedQuery(name = "getAllSeasons", query = "select s from Season s order by s.description desc")
})
@Entity
@Table(name = "season")
public class Season extends BaseClass {
    private String description;

    public Season() {
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
