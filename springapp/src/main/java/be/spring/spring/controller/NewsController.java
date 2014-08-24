package be.spring.spring.controller;

import be.spring.spring.form.NewsForm;
import be.spring.spring.interfaces.NewsService;
import be.spring.spring.model.Account;
import be.spring.spring.model.News;
import be.spring.spring.utils.Constants;
import be.spring.spring.utils.PageObject;

import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    private static final String VN_NEWS_PAGE = "/news/news";
    private static final String VN_SEARCH_PAGE = "/news/search";
    private static final String VN_ADD_NEWS_PAGE = "/news/editNews";

    @Autowired
    NewsService newsService;

    @RequestMapping(value = "news", method = RequestMethod.GET)
    public String get(Model model) {
        int newsCount = newsService.getNewsCount();
        PageObject pageObject = new PageObject(model, newsCount, Constants.ZERO,VN_NEWS_PAGE);
        pageObject.addAttributes();
        List<News> newsList = newsService.getPagedNews(Constants.ZERO);
        model.addAttribute("newsList", newsList);
        return VN_NEWS_PAGE;
    }

    @RequestMapping(value = "/news/{page}", method = RequestMethod.GET)
    public String getPaged(Model model, @PathVariable int page)
    {
        int newsCount = newsService.getNewsCount();
        PageObject pageObject = new PageObject(model, newsCount,page,VN_NEWS_PAGE);
        pageObject.addAttributes();
        model.addAttribute("newsList", newsService.getPagedNews(page));

        return VN_NEWS_PAGE;
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

    @RequestMapping(value = "editNews", method = RequestMethod.GET)
    public String addGet(@ModelAttribute("form") NewsForm form, @RequestParam(required = false) String id, Model model) {
        if (!Strings.isNullOrEmpty(id)) {
            model.addAttribute("command", "updateNews.html");
            form.setId(id);
            News n = newsService.getNewsItem(Long.parseLong(id));
            form.setTitle(n.getHeader());
            form.setBody(n.getContent());
        }
        else {
            model.addAttribute("command", "createNews.html");
        }
        return VN_ADD_NEWS_PAGE;
    }

    @RequestMapping(value = "createNews", method = RequestMethod.POST)
    public String createNews(@Valid @ModelAttribute("form") NewsForm form, BindingResult result, Model model) {
        Account a = getAccountFromSecurity();
        if (result.hasErrors()) {
            return VN_ADD_NEWS_PAGE;
        }
        newsService.createNews(form, a);
        return "redirect:" + VN_NEWS_PAGE;
    }

    @RequestMapping(value = "updateNews", method = RequestMethod.POST)
    public String updateNews(@Valid @ModelAttribute("form") NewsForm form, BindingResult result, Model model) {
        Account a = getAccountFromSecurity();
        if (result.hasErrors()) {
            return VN_ADD_NEWS_PAGE;
        }
        newsService.updateNews(form, a);
        return VN_NEWS_PAGE;
    }


}
