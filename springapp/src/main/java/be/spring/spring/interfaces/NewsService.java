package be.spring.spring.interfaces;

import be.spring.spring.form.NewsForm;
import be.spring.spring.model.Account;
import be.spring.spring.model.News;
import org.springframework.validation.Errors;

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

    News getNewsItem(Long id);

    List<News> getAll();

    List<News> getPagedNews(int start);

    List<News> getSearch(String term);

    public int getNewsCount();
}
