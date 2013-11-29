package model;

public class Account {
	private String firstName;
	private String lastName;
	private String email;
	
	public Account() { }
	public Account(String firstName, String lastName, String email) {
	this.firstName = firstName;
	this.lastName = lastName;
	this.email = email;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getFirstName() { return firstName; }
	public void setFirstName(String firstName) {
	this.firstName = firstName;
	}
	public String getLastName() { return lastName; }
	public void setLastName(String lastName) {
	this.lastName = lastName;
	}
	public String toString() {
	return firstName + " " + lastName;
	}
}
