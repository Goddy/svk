package be.spring.app.utils;

import be.spring.app.model.Account;
import be.spring.app.model.Role;
import be.spring.app.persistence.UserDetailsAdapter;
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
    public Account getAccountFromSecurity()
    {
        UserDetailsAdapter details;
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof UserDetailsAdapter) {
            details = (UserDetailsAdapter)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return details.getAccount();
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
