package be.svk.webapp.interceptors;

import be.svk.webapp.data.SocialMediaEnum;
import be.svk.webapp.model.Account;
import be.svk.webapp.persistence.AccountDao;
import be.svk.webapp.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.*;
import org.springframework.social.connect.web.ConnectInterceptor;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.request.WebRequest;

/**
 * Created by u0090265 on 4/8/15.
 */
public class SetAccountSocialProviderAfterConnectInterceptor implements ConnectInterceptor<Facebook> {
    @Autowired
    AccountDao accountDao;

    @Autowired
    private UsersConnectionRepository usersConnectionRepository;

    @Autowired
    private ConnectionRepository connectionRepository;

    @Autowired
    private SecurityUtils securityUtils;

    @Override
    public void preConnect(ConnectionFactory<Facebook> connectionFactory, MultiValueMap<String, String> parameters, WebRequest request) {

    }

    @Override
    public void postConnect(Connection<Facebook> connection, WebRequest request) {
        //Check if connection is not duplicate
        boolean connectionAlreadyAssociatedWithAnotherUser = usersConnectionRepository
                .findUserIdsWithConnection(connection).size() > 1;
        if (connectionAlreadyAssociatedWithAnotherUser) {
            connectionRepository.removeConnection(connection.getKey());
            DuplicateConnectionException duplicateException = new DuplicateConnectionException(
                    connection.getKey());
            request.setAttribute("lastSessionException",
                    duplicateException, WebRequest.SCOPE_SESSION);
            throw duplicateException;
        }
        //Connection successfull, set ENUM value on account
        else {
            Account account = securityUtils.getAccountFromSecurity();
            if (account != null) {
                account.setSignInProvider(SocialMediaEnum.valueOf(connection.getKey().getProviderId().toUpperCase()));
                accountDao.save(account);
            }
        }
    }
}
