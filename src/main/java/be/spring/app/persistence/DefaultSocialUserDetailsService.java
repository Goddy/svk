package be.spring.app.persistence;

import be.spring.app.model.Account;
import be.spring.app.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;

/**
 * Created by u0090265 on 4/3/15.
 */
public class DefaultSocialUserDetailsService implements SocialUserDetailsService {
    @Autowired
    AccountService accountService;

    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException, DataAccessException {
        //We want to connect accounts by the immutable userId
        Account account =
                accountService.getActiveAccountById(userId);
        if (account == null) {
            throw new UsernameNotFoundException(
                    "No user with userId "+ userId);
        }
        return new UserDetailsAdapter(account, null);
    }
}
