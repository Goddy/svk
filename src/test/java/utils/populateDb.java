package utils;

import be.spring.app.model.Account;
import be.spring.app.persistence.AccountDao;

/**
 * Created by u0090265 on 9/26/14.
 */
public class populateDb {
    AccountDao accountDao;

    public populateDb(AccountDao accountDao) {
        this.accountDao = accountDao;
        populate();
    }

    private void populate() {
        createAccount();
    }

    public Account createAccount() {
        Account account = new Account();
        account.setUsername("test@test.com");
        account.setLastName("testLastName");
        account.setFirstName("testFirstName");
        account.setActive(true);
        accountDao.save(account);
        return account;
    }
}
