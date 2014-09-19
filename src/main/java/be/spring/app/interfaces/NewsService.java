package be.spring.app.interfaces;

import be.spring.app.form.NewsForm;
import be.spring.app.model.Account;
import be.spring.app.model.News;
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

    News getNewsItem(String id);

    List<News> getAll();

    List<News> getPagedNews(int start);

    List<News> getSearch(String term);

    public int getNewsCount();

    @Transactional(readOnly = false)
    void deleteNews(String id, Account account);
}
