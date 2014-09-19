package be.spring.app.service;

import be.spring.app.model.Account;
import be.spring.app.model.News;

/**
 * Created by u0090265 on 9/19/14.
 */
public interface AuthorizationService {
    public void isAuthorized(Account account, News news);
}
