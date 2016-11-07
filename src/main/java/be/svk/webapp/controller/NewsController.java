package be.svk.webapp.controller;

import be.svk.webapp.controller.exceptions.ObjectNotFoundException;
import be.svk.webapp.form.NewsForm;
import be.svk.webapp.model.Account;
import be.svk.webapp.model.News;
import be.svk.webapp.service.NewsService;
import be.svk.webapp.utils.Constants;
import be.svk.webapp.utils.GeneralUtils;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

/**
 * User: Tom De Dobbeleer
 * Date: 12/20/13
 * Time: 10:44 AM
 * Remarks: none
 */
@Controller
@RequestMapping("/")
public class NewsController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(AccountController.class);
    private static final String VN_NEWS_ITEM_PAGE = "/news/newsItem";
    private static final String VN_NEWS_PAGE = "/news/news";
    private static final String VN_NEWS_PAGED_NEWS = "/news";
    private static final String VN_SEARCH_PAGE = "/news/search";
    private static final String VN_ADD_NEWS_PAGE = "/news/editNews";
    private static final String NEWS_ITEM = "/jspf/newsItem";

    @Autowired
    NewsService newsService;

    @RequestMapping(value = "news", method = RequestMethod.GET)
    public String getNews(Model model) {
        return VN_NEWS_PAGE;
    }

    @RequestMapping(value = "newsItem", method = RequestMethod.GET)
    public String getNewsItem(Model model, @RequestParam long newsId) {
        model.addAttribute("newsId", newsId);
        return VN_NEWS_ITEM_PAGE;
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "deleteItem", method = RequestMethod.GET)
    public String deleteNewsItem(Model model, @RequestParam long newsId) {
        newsService.deleteNews(newsId, getAccountFromSecurity());
        log.info(String.format("News item %s was deleted by user %s", newsId, getAccountFromSecurity().getUsername()));
        return Constants.REDIRECT_NEWS_PAGE;
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "editNews", method = RequestMethod.GET)
    public String addGet(@ModelAttribute("form") NewsForm form, @RequestParam(required = false) String newsId, Model model) {
        if (!Strings.isNullOrEmpty(newsId)) {
            long id = GeneralUtils.convertToLong(newsId);
            model.addAttribute("command", "updateNews.html");
            form.setId(id);
            News n = newsService.getNewsItem(id);
            if (n == null) throw new ObjectNotFoundException(String.format("News item %s not found by user %s", newsId, getAccountFromSecurity().getUsername()));
            form.setTitle(n.getHeader());
            form.setBody(n.getContent());
        }
        else {
            model.addAttribute("command", "createNews.html");
        }
        return VN_ADD_NEWS_PAGE;
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "createNews", method = RequestMethod.POST)
    public String createNews(@Valid @ModelAttribute("form") NewsForm form, BindingResult result, Model model) {
        Account a = getAccountFromSecurity();
        if (result.hasErrors()) {
            return VN_ADD_NEWS_PAGE;
        }
        News news = newsService.createNews(form, a);
        if (form.isSendEmail()) {
            newsService.sendNewsEmail(news);
            log.info(String.format("Email was sent for newsitem %s", news.getId()));

        }
        log.info(String.format("User %s created newsitem %s", a.getUsername(), news.getId()));
        return Constants.REDIRECT_NEWS_PAGE;
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "updateNews", method = RequestMethod.POST)
    public String updateNews(@Valid @ModelAttribute("form") NewsForm form, BindingResult result, Model model) {
        Account a = getAccountFromSecurity();
        if (result.hasErrors()) {
            return VN_ADD_NEWS_PAGE;
        }
        newsService.updateNews(form, a);
        log.info(String.format("User %s updated newsitem %s", a.getUsername(), form.getId()));
        return Constants.REDIRECT_NEWS_PAGE;
    }


}
