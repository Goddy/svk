package form;

import java.util.Locale;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.ScriptAssert;


@ScriptAssert(
lang = "javascript",
script = "_this.confirmPassword.equals(_this.password)",
message = "account.password.mismatch.message")
public class registrationForm{

	private String password, confirmPassword, firstName,
	lastName, email;
	
	@NotNull
	@Pattern(regexp = "(?=.*?[a-z])(?=.*?[A-Z])(?=.*?[^a-zA-Z]).{8,}", message = "{validation.complexity.password.message}")
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
	
	@NotNull
	@NotEmpty(message = "{validation.notempty.message}")
	@Size(max = 50)
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	@NotNull
	@NotEmpty(message = "{validation.notempty.message}")
	@Size(max = 50)
	public String getLastName() {
		return lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Email
	@NotEmpty(message = "{validation.notempty.message}")
	@NotNull
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

	public String toString() {
	
	return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
		.append("firstName", firstName)
		.append("lastName", lastName)
		.append("email", email)
		.append("password", password)
		.append("confirmPassword", confirmPassword)
		.toString();
	}

}
