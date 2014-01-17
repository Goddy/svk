package be.spring.spring.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * User: Tom De Dobbeleer
 * Date: 1/17/14
 * Time: 9:43 PM
 * Remarks: none
 */
/**
@NamedQueries({
        @NamedQuery(name = "findTeamById", query = "from teams where id = :id"),
})
**/
@Entity
@Table(name = "teams")
public class Team {
    private long id;
    private String name;
    private Address address;

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
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull
    @OneToOne(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    @JoinColumn(name="adrress_id",insertable=true, updatable=true, nullable=false,unique=true)
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
