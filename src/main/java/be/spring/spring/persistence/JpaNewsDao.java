package be.spring.spring.persistence;

import be.spring.spring.interfaces.NewsDao;
import be.spring.spring.model.News;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;

/**
 * Created by u0090265 on 5/3/14.
 */

@Repository
public class JpaNewsDao extends AbstractJpaDao<News> implements NewsDao {
    @Override
    public News findByUsername(Long id) {
        return getSingleResultQuery("findNewsById", getParameterMap("id", id) );
    }

    @Override
    public List<News> getPagedNews(int start) {
        Query q = getEntityManager().createQuery("select n from News n order by n.postDate desc");
        q.setMaxResults(10);
        q.setFirstResult(start);
        return (List<News>) q.getResultList();
    }

    @Override
    public List<News> getSearch(String term) {
        return getMultipleResultQuery("searchNews", getParameterMap("term", "%" + term + "%"));
    }
}
