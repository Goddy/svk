package be.svk.webapp.utils;

import be.svk.webapp.model.Account;
import be.svk.webapp.model.Role;
import be.svk.webapp.persistence.UserDetailsAdapter;
import be.svk.webapp.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * User: Tom De Dobbeleer
 * Date: 1/20/14
 * Time: 12:28 PM
 * Remarks: none
 */
@Component
public class SecurityUtils {
    @Autowired
    AccountService accountService;

    public Account getAccountFromSecurity()
    {
        UserDetailsAdapter details;
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDetailsAdapter) {
            details = (UserDetailsAdapter)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            //Always refresh the account
            return accountService.getAccount(details.getAccount().getId());
        }
        else {
            return null;
        }

    }

    public boolean isAdmin(Account account) {
        return account != null && account.getRole() == Role.ADMIN;
    }

    public boolean isloggedIn() {
        return getAccountFromSecurity() != null;
    }
}
