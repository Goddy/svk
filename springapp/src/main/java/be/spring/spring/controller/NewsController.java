package be.spring.spring.controller;

import be.spring.spring.interfaces.NewsService;
import be.spring.spring.model.News;
import be.spring.spring.utils.Constants;
import be.spring.spring.utils.PageObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

/**
 * User: Tom De Dobbeleer
 * Date: 12/20/13
 * Time: 10:44 AM
 * Remarks: none
 */
@Controller
@RequestMapping("/news")
public class NewsController {
    private static final Logger log = LoggerFactory.getLogger(AccountController.class);
    private static final String VN_NEWS_PAGE = "/news/news";
    private static final String VN_SEARCH_PAGE = "/news/search";


    @Inject NewsService newsService;

    @RequestMapping(value = "news", method = RequestMethod.GET)
    public String get(Model model) {
        int newsCount = newsService.getNewsCount();
        PageObject pageObject = new PageObject(model, newsCount, Constants.ONE,VN_NEWS_PAGE);
        pageObject.addAttributes();
        model.addAttribute("newsList", newsService.getPagedNews(Constants.ONE));
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
}
