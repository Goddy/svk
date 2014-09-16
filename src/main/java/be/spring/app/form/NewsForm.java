package be.spring.app.form;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * Created by u0090265 on 5/30/14.
 */
public class NewsForm {
    @NotNull
    @NotEmpty
    private String title;

    @NotNull
    @NotEmpty
    private String body;

    private String id;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
