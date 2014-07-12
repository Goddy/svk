package be.spring.spring.persistence;

import be.spring.spring.interfaces.NewsDao;
import be.spring.spring.model.News;
import be.spring.spring.utils.Constants;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * User: Tom De Dobbeleer
 * Date: 12/20/13
 * Time: 8:59 AM
 * Remarks: none
 */

public class HbnNewsDao extends AbstractHbnDao<News> implements NewsDao{

    private static final int MAX_RESULTS = 10;

    @Override
    public void create(News news) {
        create(news);
    }

    @Override
    public void update(News news) {
         update(news);
    }

    @Override
    public News findByUsername(Long id) {
        Session session = getSession();
        Query q = getSession().getNamedQuery("findNewsById");
        q.setParameter("id", id);
        return (News) q.uniqueResult();
    }

    @Override
    public List<News> getPagedNews(int start) {
        //List<News> newsList  = (List<News>) getSession().createQuery("from News order by postdate desc limit "+ start +", 10").list();
        Query q = getSession().createQuery("from News order by postdate desc");
        q.setMaxResults(MAX_RESULTS);
        q.setFirstResult(start);
        return (List<News>) q.list();
    }

    @Override
    public List<News> getSearch(String term) {
        Query q = getSession().getNamedQuery("searchNews");
        q.setParameter("term", "%" + term + "%");
        q.setMaxResults(Constants.TEN);

        return (List<News>) q.list();

    }
}
