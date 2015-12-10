package be.spring.app.form;

import org.apache.commons.lang3.text.WordUtils;

/**
 * Created by u0090265 on 12/9/15.
 */
public abstract class AccountForm {
    protected String firstName, lastName, username;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = WordUtils.capitalize(firstName);
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = WordUtils.capitalize(lastName);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
