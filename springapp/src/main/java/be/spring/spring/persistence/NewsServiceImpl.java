package be.spring.spring.persistence;

import be.spring.spring.interfaces.NewsDao;
import be.spring.spring.interfaces.NewsService;
import be.spring.spring.model.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;

import java.util.List;

/**
 * User: Tom De Dobbeleer
 * Date: 12/20/13
 * Time: 10:10 AM
 * Remarks: none
 */
@Service
@Transactional(readOnly = true)
public class NewsServiceImpl implements NewsService {
    @Autowired private NewsDao newsDao;

    @Override
    @Transactional(readOnly = false)
    public boolean createNews(News news, Errors errors) {
        boolean valid = !errors.hasErrors();
        if (valid )
            newsDao.create(news);
        return valid;
    }

    @Override
    @Transactional(readOnly = false)
    public boolean updateNews(News news, Errors errors) {
        boolean valid = !errors.hasErrors();
        if (valid )
            newsDao.update(news);
        return valid;
    }

    @Override
    public News getNewsItem(Long id) {
        return null;
    }

    @Override
    public List getAll() {
         return newsDao.getAll();
    }

    @Override
    public List<News> getPagedNews(int start) {
        return newsDao.getPagedNews(start);
    }

    @Override
    public int getNewsCount() {
        return (int)newsDao.count();
    }

    @Override
    public List<News> getSearch(String term) {
        return newsDao.getSearch(term);
    }
}
