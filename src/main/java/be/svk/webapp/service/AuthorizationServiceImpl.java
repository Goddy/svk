package be.svk.webapp.service;

import be.svk.webapp.controller.exceptions.UnauthorizedAccessException;
import be.svk.webapp.model.Account;
import be.svk.webapp.model.Comment;
import be.svk.webapp.model.News;
import be.svk.webapp.model.Role;
import org.springframework.stereotype.Service;

/**
 * Created by u0090265 on 9/19/14.
 */
@Service
public class AuthorizationServiceImpl implements AuthorizationService{
    @Override
    public void isAuthorized(Account account, News news) {
        authorize(account, news.getAccount());
    }

    @Override
    public void isAuthorized(Account account, Comment comment) {
        authorize(account, comment.getAccount());
    }

    private void authorize(Account requestingAccount, Account account) {
        if (requestingAccount.getRole() != Role.ADMIN ) {
            if (!account.equals(requestingAccount)) throw new UnauthorizedAccessException(String.format("Object cannot be edited/deleted by account %s", requestingAccount.getUsername()));
        }
    }
}
