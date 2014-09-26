package be.spring.app.controller;

import be.spring.app.controller.exceptions.ObjectNotFoundException;
import be.spring.app.form.NewsForm;
import be.spring.app.model.Account;
import be.spring.app.model.News;
import be.spring.app.service.NewsService;
import be.spring.app.utils.Constants;
import be.spring.app.utils.GeneralUtils;
import be.spring.app.utils.PageObject;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * User: Tom De Dobbeleer
 * Date: 12/20/13
 * Time: 10:44 AM
 * Remarks: none
 */
@Controller
@RequestMapping("/news")
public class NewsController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(AccountController.class);
    private static final String VN_NEWS_ITEM_PAGE = "/news/newsItem";
    private static final String VN_NEWS_PAGE = "/news/news";
    private static final String VN_SEARCH_PAGE = "/news/search";
    private static final String VN_ADD_NEWS_PAGE = "/news/editNews";

    @Autowired
    NewsService newsService;

    @RequestMapping(value = "news", method = RequestMethod.GET)
    public String getNews(Model model) {
        setPagedNews(model, Constants.ZERO);
        return VN_NEWS_PAGE;
    }

    @RequestMapping(value = "newsItem", method = RequestMethod.GET)
    public String getNewsItem(Model model, @RequestParam long newsId) {
        News news = newsService.getNewsItem(newsId);
        model.addAttribute("newsItem", news );
        return VN_NEWS_ITEM_PAGE;
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "deleteItem", method = RequestMethod.GET)
    public String deleteNewsItem(Model model, @RequestParam long newsId) {
        newsService.deleteNews(newsId, getAccountFromSecurity());
        log.info(String.format("News item %s was deleted by user %s", newsId, getAccountFromSecurity().getUsername()));
        return getRedirect(VN_NEWS_PAGE);
    }

    @RequestMapping(value = "/news/{page}", method = RequestMethod.GET)
    public String getPaged(Model model, @PathVariable int page)
    {
        setPagedNews(model, page);
        return VN_NEWS_PAGE;
    }

    private void setPagedNews(Model model, int page) {
        Page<News> pages = newsService.getPagedNews(page);
        PageObject pageObject = new PageObject(model, pages.getTotalPages(), page, VN_NEWS_PAGE);
        pageObject.addAttributes();
        model.addAttribute("newsList", Lists.newArrayList(pages.iterator()));
    }

    @RequestMapping(value = "search", method = RequestMethod.GET)
    public String search(Model model) {
        return VN_SEARCH_PAGE;
    }

    @RequestMapping(value = "search", method = RequestMethod.POST)
    public String searchTerm(Model model, @RequestParam(value="search", required=false) String searchTerm) {
        model.addAttribute("searchValue", searchTerm);
        model.addAttribute("newsList", newsService.getSearch(searchTerm));
        return VN_SEARCH_PAGE;
    }

    @RequestMapping(value = "getNewsSearch.json", method = RequestMethod.GET)
    public @ResponseBody List<News> getNews(@RequestParam(value="search", required=false) String searchTerm) {
        return newsService.getSearch(searchTerm);
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
        newsService.createNews(form, a);
        return "redirect:" + VN_NEWS_PAGE;
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value = "updateNews", method = RequestMethod.POST)
    public String updateNews(@Valid @ModelAttribute("form") NewsForm form, BindingResult result, Model model) {
        Account a = getAccountFromSecurity();
        if (result.hasErrors()) {
            return VN_ADD_NEWS_PAGE;
        }
        newsService.updateNews(form, a);
        return "redirect:" + VN_NEWS_PAGE;
    }


}
