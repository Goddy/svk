package be.spring.app.service;

import be.spring.app.controller.exceptions.ObjectNotFoundException;
import be.spring.app.form.NewsForm;
import be.spring.app.interfaces.NewsDao;
import be.spring.app.interfaces.NewsService;
import be.spring.app.model.Account;
import be.spring.app.model.News;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        News n = newsDao.get(form.getId());
        if (n == null) throw new ObjectNotFoundException(String.format("News item with id %s not found", form.getId()));
        updateNews(n, form, account);
        newsDao.update(n);

    }

    @Override
    public News getNewsItem(String id) {
        News news = newsDao.get(id);
        if (news == null) throw new ObjectNotFoundException(String.format("News item with id %s not found", id));
        return newsDao.get(id);
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

    @Override
    @Transactional(readOnly = false)
    public void deleteNews(String id) {
        newsDao.delete(id);
    }

    private News updateNews(News n, NewsForm f, Account a) {
        n.setHeader(f.getTitle());
        n.setContent(f.getBody());
        n.setId(Strings.isNullOrEmpty(f.getId()) ? null : Long.parseLong(f.getId()));
        n.setAccount(a);
        return n;
    }
}
