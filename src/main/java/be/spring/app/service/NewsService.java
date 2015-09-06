package be.spring.app.service;

import be.spring.app.form.NewsForm;
import be.spring.app.model.Account;
import be.spring.app.model.News;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User: Tom De Dobbeleer
 * Date: 12/20/13
 * Time: 10:11 AM
 * Remarks: none
 */
public interface NewsService {
    News createNews(NewsForm form, Account account);

    void updateNews(NewsForm form, Account account);

    News addNewsComment(long newsId, String content, Account account);

    News changeNewsComment(long commentId, long newsId, String content, Account account);

    @Transactional(readOnly = false)
    News deleteNewsComment(long commentId, long newsId, Account account);

    News getNewsItem(long id);

    List<News> getAll();

    Page<News> getPagedNews(int start);

    List<News> getSearch(String term);

    public int getNewsCount();

    @Transactional(readOnly = false)
    void deleteNews(long id, Account account);

    boolean sendNewsEmail(News news);
}
