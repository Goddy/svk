package be.svk.webapp.dto;

/**
 * Created by u0090265 on 08/07/16.
 */
public class SeasonDTO extends DTOBaseClass {
    public SeasonDTO(String description) {
        super();
        this.description = description;
    }

    public SeasonDTO(Long id, String description) {
        super(id);
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String description;

    @Override
    public String toString() {
        return "SeasonDTO{" +
                "description='" + description + '\'' +
                "} " + super.toString();
    }
}
