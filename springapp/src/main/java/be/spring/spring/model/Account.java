package be.spring.spring.model;

import be.spring.spring.utils.Constants;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NamedQueries({
        @NamedQuery(name = "findAccountByUsername", query = "from Account where username = :username"),
        @NamedQuery(name = "findAccountByUsernameExcludeCurrentId", query = "from Account where username = :username AND id != :id")
})
@Entity
@Table(name = "account")
public class Account {
	private Long id;
	private String firstName;
	private String lastName;
	private String username;
    private String role;
	
	public Account() {}
	public Account(String firstName, String lastName, String username) {
	this.firstName = firstName;
	this.lastName = lastName;
	this.username = username;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	public Long getId() {
		return id;
	}
	@SuppressWarnings("unused")
	private void setId(Long id) {
		this.id = id;
	}
	
	@NotNull
	@Size(min = 1, max = 50)
	@Column(name = "username")
	public String getUsername() {
		return this.username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	@NotNull
	@Size(min = 1, max = 50)
	@Column(name = "firstname")
	public String getFirstName() { return firstName; }
	public void setFirstName(String firstName) {
	this.firstName = firstName;
	}
	public String getLastName() { return lastName; }
	public void setLastName(String lastName) {
	this.lastName = lastName;
	}
	
	@Transient
	public String getFullName() {
		return firstName + " " + lastName;
	}

	@Override
	@Transient
	public String toString() {
	return firstName + " " + lastName;
	}

    @Column(name = "role")
    public String getRole() {
        if(role == null)
            return Constants.DEFAULT_ROLE;
        else
            return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
