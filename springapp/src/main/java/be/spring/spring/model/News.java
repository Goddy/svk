package be.spring.spring.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Calendar;

/**
 * User: Tom De Dobbeleer
 * Date: 12/18/13
 * Time: 7:42 PM
 * Remarks: none
 */

@NamedQueries({
        @NamedQuery(name = "findAccountByUsername", query = "from Account where username = :username"),
        @NamedQuery(name = "findAccountByUsernameExcludeCurrentId", query = "from Account where username = :username AND id != :id")
})
@Entity
@Table(name = "account")
public abstract class News {
        private Long id;
        private Calendar postDate;
        private String header;
        private String content;
        private Account account;

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
    @DateTimeFormat(pattern="yyyy-mm-dd hh:mm:ss")
    public Calendar getPostDate() {
        return postDate;
    }

    public void setPostDate(Calendar postDate) {
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

    public Account getAccount() {
        return account;
    }

    @OneToOne
    @MapsId
    public void setAccount(Account account) {
        this.account = account;
    }
}
