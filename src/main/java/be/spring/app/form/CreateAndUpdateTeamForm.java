package be.spring.app.form;

/**
 * Created by u0090265 on 5/11/14.
 */
public class CreateAndUpdateTeamForm {
    private long id;
    private String teamName;
    private String postalCode;
    private String address;
    private String city;
    private String googleLink;
    private boolean useLink = false;
    private long addressId;
    private boolean useExistingAddress = true;

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAddressId() {
        return addressId;
    }

    public void setAddressId(long addressId) {
        this.addressId = addressId;
    }

    public boolean isUseExistingAddress() {
        return useExistingAddress;
    }

    public void setUseExistingAddress(boolean useExistingAddress) {
        this.useExistingAddress = useExistingAddress;
    }
}
