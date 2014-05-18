package be.spring.spring.persistence;

/**
 * User: Tom De Dobbeleer
 * Date: 2/3/14
 * Time: 7:49 PM
 * Remarks: none
 */

import be.spring.spring.interfaces.Dao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public abstract class AbstractJpaDao<T>
        implements Dao<T> {
    @PersistenceContext
    private EntityManager entityManager;
    private Class<T> domainClass;


    @SuppressWarnings("unchecked")
    public List<T> getMultipleResultQuery(String query, Map<String, ? extends Object> parameterMap) {
        Query q = getEntityManager().createNamedQuery(query, getDomainClass());
        setParameters(q, parameterMap);
        return (List<T>) q.getResultList();
    }


    @SuppressWarnings("unchecked")
    public T getSingleResultQuery(String query, Map<String, ? extends Object> parameterMap) {
        try {
            Query q = getEntityManager().createNamedQuery(query, getDomainClass());
            setParameters(q, parameterMap);
            return (T) q.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }

    }

    private void setParameters(Query q, Map<String, ? extends Object> parameterMap) {
        if (parameterMap != null) {
            for (Map.Entry<String, ? extends Object> entry : parameterMap.entrySet()) {
                q.setParameter(entry.getKey(), entry.getValue());
            }
        }
    }


    public EntityManager getEntityManager() {
        return entityManager;
    }

    @SuppressWarnings("unchecked")
    private Class<T> getDomainClass() {
        if (domainClass == null) {
            ParameterizedType thisType =
                    (ParameterizedType) getClass().getGenericSuperclass();
            this.domainClass =
                    (Class<T>) thisType.getActualTypeArguments()[0];
        }
        return domainClass;
    }

    private String getDomainClassName() {
        return getDomainClass().getName();
    }

    public void createAll(List<T> tList) {
        for (T t : tList) {
            create(t);
        }
    }

    public void create(T t) {
        getEntityManager().persist(t);
    }

    @SuppressWarnings("unchecked")
    public T get(Serializable id) {
        return getEntityManager().find(getDomainClass(), id);
    }

    @SuppressWarnings("unchecked")
    public List<T> getAll() {
        List<T> list = getEntityManager()
                .createQuery("select x from " + getDomainClassName() + " x")
                .getResultList();
        return list;
    }

    public void delete(T t) {
        getEntityManager().remove(t);
    }

    public void deleteById(Serializable id) {
        delete(get(id));
    }

    public void deleteAll() {
        getEntityManager()
                .createQuery("delete from " + getDomainClassName() + " x")
                .executeUpdate();
    }

    public long count() {
        return (Long) getEntityManager()
                .createQuery("select count(*) from " + getDomainClassName())
                .getSingleResult();
    }

    @SuppressWarnings("unchecked")
    public T load(Serializable id) {
        return null;
    }

    public void update(T t) {
        getEntityManager().persist(t);
    }

    public boolean exists(Serializable id) {
        return (get(id) != null);
    }

    public Map<String, Object> getParameterMap(String key, Object value) {
        Map<String, Object> result = new HashMap<>();
        result.put(key, value);
        return result;
    }
}
