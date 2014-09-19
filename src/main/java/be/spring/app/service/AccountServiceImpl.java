package be.spring.app.service;

import be.spring.app.controller.exceptions.ObjectNotFoundException;
import be.spring.app.form.AccountDetailsForm;
import be.spring.app.form.ActivateAccountForm;
import be.spring.app.interfaces.AccountDao;
import be.spring.app.interfaces.AccountService;
import be.spring.app.interfaces.MailService;
import be.spring.app.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;

import java.util.List;
import java.util.Locale;

@Service
@Transactional(readOnly = true)
public class AccountServiceImpl implements AccountService {

    @Value("${base.url}")
    private String baseUrl;

    @Autowired
    private AccountDao accountDao;

    @Autowired
    MessageSource messageSource;

    @Autowired
    private MailService mailService;

    @Transactional(readOnly = false)
    public boolean registerAccount(Account account, String password, Errors errors) {
        validateUsername(account.getUsername(), errors);
        boolean valid = !errors.hasErrors();
        if (valid) {
            accountDao.create(account, password);
            mailService.sendPreConfiguredMail(messageSource.getMessage("mail.user.registered", new Object[] {baseUrl, account.getId(), account.getFullName()},Locale.ENGLISH));
        }
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

    @Override
    @Transactional
    public Account activateAccount(ActivateAccountForm form, Locale locale, Errors errors) {
        Account account = accountDao.get(form.getAccountId());
        if (account == null) throw new ObjectNotFoundException(String.format("Object with id %s not found", form.getAccountId()));
        account.setActive(true);
        if (form.isSendEmail()) {
            if (!mailService.sendMail(account.getUsername(),
                    messageSource.getMessage("email.activation.subject", null, locale),
                    messageSource.getMessage("email.activation.body", new String[]{account.getFirstName()}, locale))) {
                errors.reject("sendEmail");
            }
        }
        return account;
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
    @Transactional(readOnly = false)
    public boolean checkOldPassword(Account account, String password) {
        return accountDao.checkPassword(account, password);
    }

    @Override
    public List<Account> getAll() {
        return accountDao.getAll();
    }

    @Override
    public Account getAccount(String id) {
        return accountDao.get(id);
    }

    @Override
    public Account getActiveAccountByEmail(String email) {
        //Get account and check if
        return accountDao.findByUsernameAndActiveStatus(email, true);
    }

    @Override
    public List<Account> getAccountsByActivationStatus(boolean status) {
        return accountDao.findByActivationStatus(status);
    }

    @Override
    public List<Account> getAccountsWithActivationCode() {
        return accountDao.findByActivationCodeNotNull();
    }

    private Account getUpdatedAccount(Account account, AccountDetailsForm form) {
        Account newAccount = accountDao.get(account.getId());
        newAccount.setFirstName(form.getFirstName());
        newAccount.setLastName(form.getLastName());
        newAccount.setUsername(form.getUsername());
        return newAccount;
    }
}
