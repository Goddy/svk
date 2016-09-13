package be.spring.app.controller;

import be.spring.app.model.Account;
import be.spring.app.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by u0090265 on 14/06/16.
 */
public abstract class AbstractSecurityController {
    @Autowired
    private SecurityUtils securityUtils;

    public Account getAccountFromSecurity() {
        return securityUtils.getAccountFromSecurity();
    }

    public boolean isLoggedIn() {
        return securityUtils.isloggedIn();
    }

    public boolean isAdmin() {
        return securityUtils.isAdmin(getAccountFromSecurity());
    }
}
