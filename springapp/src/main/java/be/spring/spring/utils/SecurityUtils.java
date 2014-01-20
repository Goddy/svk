package be.spring.spring.utils;

import be.spring.spring.model.Account;
import be.spring.spring.persistence.UserDetailsAdapter;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * User: Tom De Dobbeleer
 * Date: 1/20/14
 * Time: 12:28 PM
 * Remarks: none
 */
public class SecurityUtils {

    public static Account getAccountFromSecurity()
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

}
