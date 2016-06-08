package be.spring.app.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Created by u0090265 on 10/10/14.
 */
@Entity
@Table(name = "comment")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Comment extends BaseClass {
    private Account account;
    private String content;
    private Date postDate = new Date();

    public Comment() {}

    public Comment(String content, Account account) {
        this.content = content;
        this.account = account;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "posted_by", nullable = false)
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @NotNull
    @Size(min = 1)
    @Column(name = "content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @NotNull
    @Column(name = "postdate")
    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }
}
