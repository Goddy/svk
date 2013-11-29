package form;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
public class registrationForm {

	private String username, password, confirmPassword, firstName,
	lastName, email;

	public String getUsername() { return username; }
	
	public void setUsername(String username) { this.username = username; }
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String toString() {
	
	return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
		.append("username", username)
		.append("firstName", firstName)
		.append("lastName", lastName)
		.append("email", email)
		.toString();
	}
}
