package be.spring.app.dto;

import be.spring.app.model.BaseClass;

/**
 * Created by u0090265 on 09/09/16.
 */
public class MatchDoodleDTO extends BaseClass {
    private DoodleDTO doodle;
    private String date;
    private String description;

    public MatchDoodleDTO(DoodleDTO doodle, String date, String description) {
        this.doodle = doodle;
        this.date = date;
        this.description = description;
    }

    public DoodleDTO getDoodle() {
        return doodle;
    }

    public void setDoodle(DoodleDTO doodle) {
        this.doodle = doodle;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
