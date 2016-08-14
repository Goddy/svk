package be.spring.app.model;

import javax.persistence.*;

/**
 * Created by u0090265 on 11/8/15.
 */
@Entity
@Table(name = "image")
public class Image extends BaseClass {
    private String imageId;
    private String imageUrl;

    @Column(name = "image_id")
    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    @Column(name = "image_url")
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
