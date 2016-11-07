package be.svk.webapp.service;

import be.svk.webapp.model.Account;
import be.svk.webapp.model.Comment;
import be.svk.webapp.model.News;

/**
 * Created by u0090265 on 9/19/14.
 */
public interface AuthorizationService {
    void isAuthorized(Account account, News news);

    void isAuthorized(Account account, Comment comment);
}
