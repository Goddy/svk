package be.spring.app.service;

import be.spring.app.controller.exceptions.ObjectNotFoundException;
import be.spring.app.form.NewsForm;
import be.spring.app.model.Account;
import be.spring.app.model.News;
import be.spring.app.persistence.NewsDao;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    @Autowired private AuthorizationService authorizationService;

    @Override
    @Transactional(readOnly = false)
    public News createNews(NewsForm form, Account account) {
            News n = updateNews(new News(), form, account, true);
            newsDao.save(n);
            return n;
    }

    @Override
    @Transactional(readOnly = false)
    public void updateNews(NewsForm form, Account account) {
        News n = newsDao.findOne(form.getId());
        if (n == null) throw new ObjectNotFoundException(String.format("News item with id %s not found", form.getId()));
        authorizationService.isAuthorized(account, n);
        updateNews(n, form, account, false);
        newsDao.save(n);

    }

    @Override
    public News getNewsItem(long id) {
        News news = newsDao.findOne(id);
        if (news == null) throw new ObjectNotFoundException(String.format("News item with id %s not found", id));
        return news;
    }

    @Override
    public List<News> getAll() {
         return Lists.newArrayList(newsDao.findAll());
    }

    @Override
    public Page<News> getPagedNews(int start) {
        return newsDao.findAll((new PageRequest(start, start + 10)));
    }

    @Override
    public int getNewsCount() {
        return (int)newsDao.count();
    }

    @Override
    public List<News> getSearch(String term) {
        return newsDao.getSearch("%" + term + "%");
    }

    @Override
    @Transactional(readOnly = false)
    public void deleteNews(long id, Account account) {
        News news = newsDao.findOne(id);
        authorizationService.isAuthorized(account, news);
        newsDao.delete(id);
    }

    private News updateNews(News n, NewsForm f, Account a, boolean isNew) {
        n.setHeader(f.getTitle());
        n.setContent(f.getBody());
        //n.setId(Strings.isNullOrEmpty(f.getId()) ? null : Long.parseLong(f.getId()));
        if (isNew) {
            n.setAccount(a);
        }
        return n;
    }
}
