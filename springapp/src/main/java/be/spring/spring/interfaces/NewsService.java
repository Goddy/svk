package be.spring.spring.interfaces;

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
    boolean createNews(News news, Errors errors);
    boolean updateNews(News news, Errors errors);
    News getNewsItem(Long id);
    List getAll();
    List<News> getPagedNews(int start);
}
