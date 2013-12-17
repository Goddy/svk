package be.spring.spring.form;

import be.spring.spring.utils.Constants;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * User: Tom De Dobbeleer
 * Date: 12/13/13
 * Time: 2:52 PM
 * Remarks: none
 */
public class accountDetailsForm {
    protected String firstName,lastName, email;

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
                .toString();
    }
}

