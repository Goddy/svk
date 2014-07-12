package be.spring.spring.service;

import be.spring.spring.form.NewsForm;
import be.spring.spring.interfaces.NewsDao;
import be.spring.spring.interfaces.NewsService;
import be.spring.spring.model.Account;
import be.spring.spring.model.News;
import com.google.common.base.Strings;
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
    public News createNews(NewsForm form, Account account) {
            News n = updateNews(new News(), form, account);
            newsDao.create(n);
            return n;
    }

    @Override
    @Transactional(readOnly = false)
    public void updateNews(NewsForm form, Account account) {
        News n = newsDao.get(Long.parseLong(form.getId()));
        if (n != null) {
            updateNews(n, form, account);
            newsDao.update(n);
        }
        else {
            //Todo: exception
        }
    }

    @Override
    public News getNewsItem(Long id) {
        return null;
    }

    @Override
    public List<News> getAll() {
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

    private News updateNews(News n, NewsForm f, Account a) {
        n.setHeader(f.getTitle());
        n.setContent(f.getBody());
        n.setId(Strings.isNullOrEmpty(f.getId()) ? null : Long.parseLong(f.getId()));
        n.setAccount(a);
        return n;
    }
}
