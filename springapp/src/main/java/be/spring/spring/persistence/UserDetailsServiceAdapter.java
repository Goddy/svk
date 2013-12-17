package be.spring.spring.persistence;

import javax.inject.Inject;

import be.spring.spring.interfaces.AccountService;
import be.spring.spring.interfaces.UserDetailsDao;
import be.spring.spring.model.Account;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



/**
 * User: Tom De Dobbeleer
 * Date: 12/13/13
 * Time: 3:35 PM
 * Remarks: none
 */

@Service("userDetailsService")
@Transactional(readOnly = true)
public class UserDetailsServiceAdapter implements UserDetailsService {
    @Inject AccountService accountService;
    @Inject UserDetailsDao userDetailsDao;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
        Account account =
                accountService.getAccountByEmail(username);
        if (account == null) {
            throw new UsernameNotFoundException(
                    "No user with email "+ username);
        } else if (account.getRole().isEmpty()) {
            throw new UsernameNotFoundException(
                    "User " + account.getUsername() + " has no authorities");
        }
        UserDetailsAdapter user = new UserDetailsAdapter(account);
        user.setPassword(
                userDetailsDao.findPasswordByUsername(username));
        return user;
    }
}

