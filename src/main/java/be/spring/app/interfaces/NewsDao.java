package be.spring.app.interfaces;

import be.spring.app.model.News;

import java.util.List;

/**
 * User: Tom De Dobbeleer
 * Date: 12/20/13
 * Time: 9:14 AM
 * Remarks: none
 */
public interface NewsDao extends Dao<News> {
    News findByUsername(Long id);

    List<News> getPagedNews(int start);

    List<News> getSearch(String term);
}