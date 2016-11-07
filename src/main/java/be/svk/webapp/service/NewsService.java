package be.svk.webapp.service;

import be.svk.webapp.form.NewsForm;
import be.svk.webapp.model.Account;
import be.svk.webapp.model.News;
import com.google.common.base.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
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

    Page<News> getPagedNews(int start, int pageSize, Optional<Sort> sort);

    Page<News> getSearch(String term, int start, int pageSize, Optional<Sort> sort);

    int getNewsCount();

    @Transactional(readOnly = false)
    void deleteNews(long id, Account account);

    boolean sendNewsEmail(News news);
}
