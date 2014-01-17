package be.spring.spring.interfaces;

import be.spring.spring.model.News;

import java.util.List;

/**
 * User: Tom De Dobbeleer
 * Date: 12/20/13
 * Time: 9:14 AM
 * Remarks: none
 */
public interface NewsDao extends Dao<News> {
    void create(News news);
    void update(News news);
    News findByUsername(Long id);
    List<News> getPagedNews(int start);
    List<News> getSearch(String term);
}
