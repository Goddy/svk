package be.spring.app.form;

import be.spring.app.validators.SanitizeUtils;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

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

    private MultipartFile image;

    private long id;

    private boolean sendEmail = false;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = SanitizeUtils.SanitizeHtml(title);
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = SanitizeUtils.SanitizeHtml(body);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isSendEmail() {
        return sendEmail;
    }

    public void setSendEmail(boolean sendEmail) {
        this.sendEmail = sendEmail;
    }

    @Override
    public String toString() {
        return "NewsForm{" +
                "title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", id=" + id +
                ", sendEmail=" + sendEmail +
                '}';
    }
}
