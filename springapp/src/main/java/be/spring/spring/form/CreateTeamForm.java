package be.spring.spring.form;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.NotNull;

/**
 * Created by u0090265 on 5/11/14.
 */
public class CreateTeamForm {
    private String teamName;
    private int postalCode;
    private String address;
    private String city;
    private String googleLink;
    private boolean useLink = false;

    @NotNull
    @NumberFormat
    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    @NotNull
    @NotEmpty
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @NotNull
    @NotEmpty
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @NotNull
    @NotEmpty
    public String getGoogleLink() {
        if (!useLink) return null;
        return googleLink;
    }

    public void setGoogleLink(String googleLink) {
        this.googleLink = googleLink;
    }

    @NotNull
    @NotEmpty
    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public boolean isUseLink() {
        return useLink;
    }

    public void setUseLink(boolean useLink) {
        this.useLink = useLink;
    }
}
