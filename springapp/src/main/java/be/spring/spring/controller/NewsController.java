package be.spring.spring.controller;

import be.spring.spring.interfaces.NewsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
        model.addAttribute(newsService.getAll());
        return VN_NEWS_PAGE;
    }
}
