package be.spring.app.service;

import be.spring.app.form.AccountDetailsForm;
import be.spring.app.form.ActivateAccountForm;
import be.spring.app.model.Account;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;

import java.util.List;
import java.util.Locale;

public interface AccountService {
    Account registerAccount(Account account, String password);

    Account updateAccount(Account account, AccountDetailsForm form);

    @Transactional
    Account saveAccount(Account account);

    @Transactional
    Account activateAccount(ActivateAccountForm form, Locale locale, Errors errors);

    void validateUsername(String email, Errors errors);

    void validateUsernameExcludeCurrentId(String email, Long id, Errors errors);

    @Transactional(readOnly = false)
    void setPasswordFor(Account account, String password);

    @Transactional(readOnly = false)
    boolean checkOldPassword(Account account, String password);

    @Transactional(readOnly = true)
    boolean passwordIsNullOrEmpty(Account account);

    @Transactional(readOnly = true)
    List<Account> getAll();

    @Transactional(readOnly = true)
    List<Account> getAllActivateAccounts();

    @Transactional(readOnly = true)
    Account getAccount(String id);

    Account getAccount(Long id);

    Account getActiveAccountByEmail(String email);

    Account getActiveAccountById(String id);

    List<Account> getAccountsByActivationStatus(boolean status);

    List<Account> getAccountsWithActivationCode();
}

