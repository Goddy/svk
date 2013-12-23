package be.spring.spring.controller;

import be.spring.spring.interfaces.NewsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;

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
    @Inject NewsService newsService;

    @RequestMapping(value = "news", method = RequestMethod.GET)
    public String getNews(Model model) {
        model.addAttribute("newsList", newsService.getPagedNews(1));
        model.addAttribute("previous", String.format("%s/%s", VN_NEWS_PAGE , 1));
        model.addAttribute("next", String.format("%s/%s", VN_NEWS_PAGE ,10));
        return VN_NEWS_PAGE;
    }

    @RequestMapping(value = "/news/{page}", method = RequestMethod.GET)
    public String getPagedNews(Model model, @PathVariable int page)
    {
        model.addAttribute("newsList", newsService.getPagedNews(page));
        model.addAttribute("previous", String.format("%s/%s", VN_NEWS_PAGE , page-10));
        model.addAttribute("next", String.format("%s/%s", VN_NEWS_PAGE , page+10));
        return VN_NEWS_PAGE;
    }
}
