package be.spring.spring.controller;

import be.spring.spring.model.Account;
import be.spring.spring.utils.SecurityUtils;

/**
 * User: Tom De Dobbeleer
 * Date: 1/20/14
 * Time: 1:43 PM
 * Remarks: none
 */
public abstract class AbstractController {
    public Account getAccountFromSecurity() {
        return SecurityUtils.getAccountFromSecurity();
    }
}
