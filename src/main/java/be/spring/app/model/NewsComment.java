package be.spring.app.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by u0090265 on 10/24/14.
 */
@Entity
@Table(name = "comment_news")
public class NewsComment extends Comment {
    private News news;

    public NewsComment() {
        super();
    }
    public NewsComment(String content, News news, Account account) {
        super(content, account);
        this.news = news;
    }

    @ManyToOne
    @JoinColumn(name = "NEWS_ID", referencedColumnName = "ID")
    public News getNews() {
        return news;
    }

    public void setNews(News news) {
        this.news = news;
    }
}
