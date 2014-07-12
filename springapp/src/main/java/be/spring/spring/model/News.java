package be.spring.spring.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;


/**
 * User: Tom De Dobbeleer
 * Date: 12/18/13
 * Time: 7:42 PM
 * Remarks: Base class for news items.
 */
@NamedQueries({
        @NamedQuery(name = "findNewsById", query = "select n from News n where n.id = :id"),
        @NamedQuery(name = "searchNews", query = "select n from News n where n.header like :term")
})
@Entity
@Table(name = "news")
public class News {
        private Long id;
        private Date postDate;
        private String header;
        private String content;
        private Account account;


    public News (String header, String content, Account account) {
        this.header = header;
        this.content = content;
        this.account = account;
    }
    public News () {
        postDate = new Date();
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NotNull
    @Column(name = "postdate")
    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    @NotNull
    @Size(min = 1)
    @Column(name = "header")
    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
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

    @OneToOne(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
    @JoinColumn(name="posted_by")
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
