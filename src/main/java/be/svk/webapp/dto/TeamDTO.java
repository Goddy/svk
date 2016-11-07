package be.svk.webapp.dto;

/**
 * Created by u0090265 on 10/2/15.
 */
public class TeamDTO {
    String name;
    String address;
    String googleMapsEmbeddedLink;

    public TeamDTO(String name, String address, String googleMapsLink) {
        this.name = name;
        this.address = address;
        this.googleMapsEmbeddedLink = googleMapsLink;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGoogleMapsEmbeddedLink() {
        return googleMapsEmbeddedLink;
    }

    public void setGoogleMapsEmbeddedLink(String googleMapsLink) {
        this.googleMapsEmbeddedLink = googleMapsLink;
    }
}
