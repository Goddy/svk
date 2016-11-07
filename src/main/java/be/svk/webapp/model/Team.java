package be.svk.webapp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * User: Tom De Dobbeleer
 * Date: 1/17/14
 * Time: 9:43 PM
 * Remarks: none
 */


@NamedQueries({
        @NamedQuery(name = "findTeamByName", query = "select t from Team t where t.name = :name")
})

@Entity
@Table(name = "teams")
public class Team extends BaseClass {
    private String name;
    private Address address;

    public Team() {
    }

    @NotNull
    @Column(name = "description")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    @NotNull
    @OneToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinColumn(name = "adrress_id", insertable = true, updatable = true, nullable = false)
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
