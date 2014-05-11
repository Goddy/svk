package be.spring.spring.form;

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

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getGoogleLink() {
        if (!useLink) return null;
        return googleLink;
    }

    public void setGoogleLink(String googleLink) {
        this.googleLink = googleLink;
    }

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
