package utils;

import be.spring.app.controller.DataFactory;
import be.spring.app.model.Account;
import be.spring.app.persistence.AccountDao;

/**
 * Created by u0090265 on 9/26/14.
 */
public class PopulateDb {
    AccountDao accountDao;

    public PopulateDb(AccountDao accountDao) {
        this.accountDao = accountDao;
        populate();
    }

    private void populate() {
        createAccount();
    }

    public Account createAccount() {
        Account account = accountDao.findByUsername("test@test.com");
        if (account == null) {
            Account.Builder b = new Account.Builder()
                    .username("test@test.com")
                    .lastName(DataFactory.getDefaultRandomString())
                    .firstName(DataFactory.getDefaultRandomString())
                    .active(true);
            account = accountDao.save(b.build());
        }
        return account;
    }
}
