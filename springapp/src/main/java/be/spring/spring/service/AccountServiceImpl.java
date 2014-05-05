package be.spring.spring.service;

import be.spring.spring.interfaces.AccountDao;
import be.spring.spring.interfaces.AccountService;
import be.spring.spring.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;

@Service
@Transactional(readOnly = true)
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountDao accountDao;

    @Transactional(readOnly = false)
    public boolean registerAccount(Account account, String password, Errors errors) {
        validateUsername(account.getUsername(), errors);
        boolean valid = !errors.hasErrors();
        if (valid) {
            accountDao.create(account, password);
        }
        return valid;
    }

    @Transactional(readOnly = false)
    public boolean updateAccount(Account account, Errors errors) {
        validateUsernameExcludeCurrentId(account.getUsername(), account.getId(), errors);
        boolean valid = !errors.hasErrors();
        if (valid) {
            accountDao.update(account);
        }
        return valid;
    }

    @Transactional(readOnly = true)
    public void validateUsername(String username, Errors errors) {
        if (accountDao.findByUsername(username) != null) {
            errors.rejectValue("username", "error.duplicate.account.email",
                    new String[]{username}, null);
        }
    }

    @Transactional(readOnly = true)
    public void validateUsernameExcludeCurrentId(String username, Long id, Errors errors) {
        if (accountDao.findByEmailExcludeCurrentId(username, id) != null) {
            errors.rejectValue("username", "error.duplicate.account.email",
                    new String[]{username}, null);
        }
    }

    @Override
    public Account getAccountByEmail(String email) {
        return accountDao.findByUsername(email);
    }
}
