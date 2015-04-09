package be.spring.app.interceptors;

import be.spring.app.model.Account;
import be.spring.app.persistence.AccountDao;
import be.spring.app.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.web.DisconnectInterceptor;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.web.context.request.WebRequest;

/**
 * Created by u0090265 on 4/8/15.
 */
public class RemoveAccountSocialProviderAfterDisconnectInterceptor implements DisconnectInterceptor<Facebook> {
    @Autowired
    AccountDao accountDao;

    @Autowired
    private SecurityUtils securityUtils;

    @Override
    public void preDisconnect(ConnectionFactory<Facebook> connectionFactory, WebRequest request) {

    }

    @Override
    public void postDisconnect(ConnectionFactory<Facebook> connectionFactory, WebRequest request) {
        Account account = securityUtils.getAccountFromSecurity();
        if (account != null ) {
            account.setSignInProvider(null);
            accountDao.save(account);
        }
    }
}
