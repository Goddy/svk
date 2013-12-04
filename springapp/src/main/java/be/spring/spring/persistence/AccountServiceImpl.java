package be.spring.spring.persistence;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;

import be.spring.spring.model.Account;

@Service
@Transactional(readOnly = true)
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountDao accountDao;

    @Transactional(readOnly = false)
    public boolean registerAccount(Account account, String password, Errors errors) {
        validateEmail(account.getEmail(), errors);
        boolean valid = !errors.hasErrors();
        if (valid) { accountDao.create(account, password); }
        return valid;
    }
    private void validateEmail(String email, Errors errors) {
        if (accountDao.findByEmail(email) != null) {
            errors.rejectValue("email", "error.duplicate",
                    new String[] { email }, null);
        }
    }
}
