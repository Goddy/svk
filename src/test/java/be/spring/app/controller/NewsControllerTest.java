package be.spring.app.controller;

import be.spring.app.model.*;
import be.spring.app.persistence.NewsDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithSecurityContextTestExecutionListener;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.ServletTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.transaction.annotation.Transactional;

import static junit.framework.Assert.assertEquals;
import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * Created by u0090265 on 9/12/14.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"/jUnit.xml"})
@Transactional
@TestExecutionListeners(listeners = {ServletTestExecutionListener.class,
        DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionalTestExecutionListener.class,
        WithSecurityContextTestExecutionListener.class})
public class NewsControllerTest extends AbstractTest {
    Account userAccount1;
    Account userAccount2;
    Account adminAccount;

    private String comment;

    @Autowired
    private NewsDao newsDao;

    @Before
    public void setUpTests() {
        userAccount1 = createRandomAccount();
        userAccount2 = createRandomAccount();
        adminAccount = createRandomAccount(Role.ADMIN);
        comment = DataFactory.getDefaultRandomString();
    }

    @Test
    public void testCreateCommentWithAccount() throws Exception {
        reset(securityUtils);
        News n = createAndSaveNews(userAccount1);
        expectSecurityLogin(userAccount1);
        replay(securityUtils);

        postCreateNews(n.getId(), comment, status().isOk(), ROLE_USER);

        News resultNews = newsDao.findOne(n.getId());

        NewsComment resultComment = resultNews.getComments().get(0);
        assertEquals(comment, resultComment.getContent());
        assertEquals(userAccount1, resultComment.getAccount());

        verify(securityUtils);
    }

    @Test
    public void testCreateCommentWithOutAccount() throws Exception {
        News n = createAndSaveNews(userAccount1);

        mockMvc.perform(post("/news/addComment.html")
                .param("comment", comment)
                .param("newsId", Long.toString(n.getId()))
                .accept(MediaType.TEXT_PLAIN))
                .andDo(print())
                .andExpect(status().isFound())
                .andReturn();

        News resultNews = newsDao.findOne(n.getId());

        assertTrue(resultNews.getComments().isEmpty());
    }

    @Test
    public void testUpdateCommentWithAccount() throws Exception {
        reset(securityUtils);
        News n = createNewsWithComment(userAccount1);
        NewsComment c = n.getComments().get(0);
        expectSecurityLogin(userAccount1);
        replay(securityUtils);

        postUpdateNews(n.getId(), c.getId(), comment, status().isOk(), ROLE_USER);

        News resultNews = newsDao.findOne(n.getId());

        NewsComment resultComment = resultNews.getComments().get(0);
        assertEquals(comment, resultComment.getContent());
        assertEquals(userAccount1, resultComment.getAccount());

        verify(securityUtils);
    }

    @Test
    public void testUpdateCommentWrongAccount() throws Exception {
        reset(securityUtils);
        News n = createNewsWithComment(userAccount1);
        NewsComment c = n.getComments().get(0);
        expectSecurityLogin(userAccount2);
        replay(securityUtils);

        postUpdateNews(n.getId(), c.getId(), comment, view().name("error-403"), ROLE_USER);

        News resultNews = newsDao.findOne(n.getId());

        NewsComment resultComment = resultNews.getComments().get(0);
        assertEquals(c, resultComment);
        assertEquals(userAccount1, resultComment.getAccount());

        verify(securityUtils);
    }

    @Test
    public void testUpdateCommentWrongAccountButAdmin() throws Exception {
        reset(securityUtils);
        News n = createNewsWithComment(userAccount1);
        NewsComment c = n.getComments().get(0);
        expectSecurityLogin(adminAccount);
        replay(securityUtils);

        postUpdateNews(n.getId(), c.getId(), comment, status().isOk(), ROLE_USER);

        News resultNews = newsDao.findOne(n.getId());

        NewsComment resultComment = resultNews.getComments().get(0);
        assertEquals(comment, resultComment.getContent());
        assertEquals(userAccount1, resultComment.getAccount());

        verify(securityUtils);
    }

    @Test
    public void testUpdateCommentWithOutAccount() throws Exception {
        News n = createNewsWithComment(userAccount1);
        Comment c = n.getComments().get(0);

        mockMvc.perform(post("/news/editComment.html")
                .param("comment", comment)
                .param("newsId", Long.toString(n.getId()))
                .param("commentId", Long.toString(c.getId()))
                .accept(MediaType.TEXT_PLAIN))
                .andDo(print())
                .andExpect(status().isFound())
                .andReturn();

        News resultNews = newsDao.findOne(n.getId());

        assertEquals(c, resultNews.getComments().get(0));
    }

    @Test
    public void testDeleteCommentWithUser() throws Exception {
        reset(securityUtils);
        News n = createNewsWithComment(userAccount1);
        NewsComment c = n.getComments().get(0);
        expectSecurityLogin(userAccount1);
        replay(securityUtils);

        postDeleteNews(n.getId(), c.getId(), status().isOk(), ROLE_USER);

        News resultNews = newsDao.findOne(n.getId());

        assertTrue(resultNews.getComments().isEmpty());

        verify(securityUtils);
    }

    @Test
    public void testDeleteCommentWithOtherUser() throws Exception {
        reset(securityUtils);
        News n = createNewsWithComment(userAccount1);
        NewsComment c = n.getComments().get(0);
        expectSecurityLogin(userAccount2);
        replay(securityUtils);

        postDeleteNews(n.getId(), c.getId(), status().isOk(), ROLE_USER);

        News resultNews = newsDao.findOne(n.getId());

        assertFalse(resultNews.getComments().isEmpty());

        verify(securityUtils);
    }


    @Test
    public void testDeleteCommentWithOtherUserButAdmin() throws Exception {
        reset(securityUtils);
        News n = createNewsWithComment(userAccount1);
        NewsComment c = n.getComments().get(0);
        expectSecurityLogin(adminAccount);
        replay(securityUtils);

        postDeleteNews(n.getId(), c.getId(), status().isOk(), ROLE_USER);

        News resultNews = newsDao.findOne(n.getId());

        assertTrue(resultNews.getComments().isEmpty());

        verify(securityUtils);
    }


    private News createAndSaveNews(Account account) {
        News news = DataFactory.createNews(account);
        return newsDao.save(news);
    }

    private News createNewsWithComment(Account account) {
        News news = DataFactory.createNews(account);
        NewsComment comment = DataFactory.createNewsComment(account);
        comment.setNews(news);
        news.getComments().add(comment);

        return newsDao.save(news);

    }

    private void postCreateNews(long newsId, String comment, ResultMatcher resultMatcher, String role) throws Exception {
        mockMvc.perform(post("/news/addComment.html")
                .param("comment", comment)
                .param("newsId", Long.toString(newsId))
                .with(user("user").roles(role))
                .accept(MediaType.TEXT_PLAIN))
                .andDo(print())
                .andExpect(resultMatcher)
                .andExpect(view().name("/jspf/newsItem"))
                .andReturn();
    }

    private void postUpdateNews(long newsId, long commentId, String comment, ResultMatcher resultMatcher, String role) throws Exception {
        mockMvc.perform(post("/news/editComment.html")
                .param("comment", comment)
                .param("newsId", Long.toString(newsId))
                .param("commentId", Long.toString(commentId))
                .with(user("user").roles(role))
                .accept(MediaType.TEXT_PLAIN))
                .andDo(print())
                .andExpect(resultMatcher)
                .andReturn();
    }

    private void postDeleteNews(long newsId, long commentId, ResultMatcher resultMatcher, String role) throws Exception {
        mockMvc.perform(post("/news/deleteComment.html")
                .param("newsId", Long.toString(newsId))
                .param("commentId", Long.toString(commentId))
                .with(user("user").roles(role))
                .accept(MediaType.TEXT_PLAIN))
                .andDo(print())
                .andExpect(resultMatcher)
                .andReturn();
    }
}
