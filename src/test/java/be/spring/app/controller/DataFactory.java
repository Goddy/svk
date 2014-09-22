package be.spring.app.controller;

import be.spring.app.model.News;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

/**
 * Created by u0090265 on 9/12/14.
 */
public class DataFactory {
    private static final int DEF_RANDOM_LENGTH = 8;
    private static final int DEF_RANDOM_MAX_LENGTH = 10;

    public static News createNews() {
        News news = new News();
        news.setContent(getDefaultRandomString());
        news.setHeader(getDefaultRandomString());
        return news;
    }

    public static String getDefaultRandomString() {
        return randomAlphabetic(DEF_RANDOM_LENGTH);
    }
}
