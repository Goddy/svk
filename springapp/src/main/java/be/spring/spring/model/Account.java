package be.spring.spring.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NamedQuery(
name = "findAccountByEmail",
query = "from Account where email = :email")
@Entity
@Table(name = "account")
public class Account {
	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	
	public Account() { }
	public Account(String firstName, String lastName, String email) {
	this.firstName = firstName;
	this.lastName = lastName;
	this.email = email;
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
	@Column(name = "email")
	public String getEmail() {
		return this.email;
	}
	
	public void setEmail(String email) {
		this.email = email;
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
	private String getFullName() {
		return firstName + " " + lastName;
	}

	@Override
	@Transient
	public String toString() {
	return firstName + " " + lastName;
	}
}
