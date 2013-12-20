package be.spring.spring.persistence;

import be.spring.spring.interfaces.NewsDao;
import be.spring.spring.model.News;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

/**
 * User: Tom De Dobbeleer
 * Date: 12/20/13
 * Time: 8:59 AM
 * Remarks: none
 */
@Repository
public class HbnNewsDao extends AbstractHbnDao<News> implements NewsDao{

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
}
