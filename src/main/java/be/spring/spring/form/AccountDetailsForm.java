package be.spring.spring.form;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * User: Tom De Dobbeleer
 * Date: 12/13/13
 * Time: 2:52 PM
 * Remarks: none
 */
public class AccountDetailsForm {
    protected String firstName, lastName, username;

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
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String toString() {

        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("firstName", firstName)
                .append("lastName", lastName)
                .append("username", username)
                .toString();
    }
}

