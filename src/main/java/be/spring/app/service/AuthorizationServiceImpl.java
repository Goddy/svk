package be.spring.app.service;

import be.spring.app.controller.exceptions.UnauthorizedAccessException;
import be.spring.app.model.Account;
import be.spring.app.model.News;
import be.spring.app.model.Role;
import org.springframework.stereotype.Service;

/**
 * Created by u0090265 on 9/19/14.
 */
@Service
public class AuthorizationServiceImpl implements AuthorizationService{
    @Override
    public void isAuthorized(Account account, News news) {
        if (account.getRole() != Role.ADMIN ) {
            if (!account.equals(news.getAccount())) throw new UnauthorizedAccessException(String.format("Newsitem %s cannot be edited/deleted by account %s", news.getId(), account.getUsername()));
        }
    }
}
