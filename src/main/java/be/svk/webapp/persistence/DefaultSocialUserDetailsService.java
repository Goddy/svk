package be.svk.webapp.persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;

/**
 * Created by u0090265 on 4/3/15.
 */
public class DefaultSocialUserDetailsService implements SocialUserDetailsService {
    @Autowired
    private UserDetailsService userDetailsService;

    private static final Logger logger = LoggerFactory.getLogger(DefaultSocialUserDetailsService.class);

    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException, DataAccessException {
        logger.info(String.format("Trying to match %s with username in Database", userId));
        return (SocialUserDetails) userDetailsService.loadUserByUsername(userId);
    }
}
