package be.spring.spring.persistence;

import be.spring.spring.interfaces.NewsDao;
import be.spring.spring.model.News;

import java.util.List;

/**
 * Created by u0090265 on 5/3/14.
 */
public class JpaNewsDao extends AbstractJpaDao<News> implements NewsDao {
    @Override
    public News findByUsername(Long id) {
        return null;
    }

    @Override
    public List<News> getPagedNews(int start) {
        return null;
    }

    @Override
    public List<News> getSearch(String term) {
        return null;
    }
}