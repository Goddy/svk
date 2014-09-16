package be.spring.app.service;

import be.spring.app.form.AccountDetailsForm;
import be.spring.app.interfaces.AccountDao;
import be.spring.app.interfaces.AccountService;
import be.spring.app.interfaces.MailService;
import be.spring.app.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountDao accountDao;

    @Autowired
    private MailService mailService;

    @Transactional(readOnly = false)
    public boolean registerAccount(Account account, String password, Errors errors) {
        validateUsername(account.getUsername(), errors);
        boolean valid = !errors.hasErrors();
        if (valid) {
            accountDao.create(account, password);
        }
        //send mail
        mailService.sendPreConfiguredMail(String.format("Account id %s was created", account.getId()));
        return valid;
    }

    @Transactional(readOnly = false)
    public boolean updateAccount(Account account, Errors errors, AccountDetailsForm form) {
        //To do: why is object detached when coming from security?
        Account newAccount = getUpdatedAccount(account, form);
        validateUsernameExcludeCurrentId(newAccount.getUsername(), newAccount.getId(), errors);
        boolean valid = !errors.hasErrors();
        if (valid) {
            accountDao.update(newAccount);
        }
        return valid;
    }

    public void validateUsername(String username, Errors errors) {
        if (accountDao.findByUsername(username) != null) {
            errors.rejectValue("username", "error.duplicate.account.email",
                    new String[]{username}, null);
        }
    }

    public void validateUsernameExcludeCurrentId(String username, Long id, Errors errors) {
        if (accountDao.findByEmailExcludeCurrentId(username, id) != null) {
            errors.rejectValue("username", "error.duplicate.account.email",
                    new String[]{username}, null);
        }
    }

    @Override
    @Transactional(readOnly = false)
    public void setPasswordFor(Account account, String password) {
        accountDao.update(account, password);
    }

    @Override
    public List<Account> getAll() {
        return accountDao.getAll();
    }

    @Override
    public Account getActiveAccountByEmail(String email) {
        //Get account and check if
        return accountDao.findByUsernameAndActiveStatus(email, true);
    }

    private Account getUpdatedAccount(Account account, AccountDetailsForm form) {
        Account newAccount = accountDao.get(account.getId());
        newAccount.setFirstName(form.getFirstName());
        newAccount.setLastName(form.getLastName());
        newAccount.setUsername(form.getUsername());
        return newAccount;
    }
}