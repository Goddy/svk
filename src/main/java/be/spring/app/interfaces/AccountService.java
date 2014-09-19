package be.spring.app.interfaces;

import be.spring.app.form.AccountDetailsForm;
import be.spring.app.form.ActivateAccountForm;
import be.spring.app.model.Account;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;

import java.util.List;
import java.util.Locale;

public interface AccountService {
    boolean registerAccount(Account account, String password, Errors errors);

    boolean updateAccount(Account account, Errors errors, AccountDetailsForm form);

    @Transactional
    Account activateAccount(ActivateAccountForm form, Locale locale, Errors errors);

    void validateUsername(String email, Errors errors);

    void validateUsernameExcludeCurrentId(String email, Long id, Errors errors);

    @Transactional(readOnly = false)
    void setPasswordFor(Account account, String password);

    @Transactional(readOnly = false)
    boolean checkOldPassword(Account account, String password);

    @Transactional(readOnly = true)
    List<Account> getAll();

    @Transactional(readOnly = true)
    Account getAccount(String id);

    Account getActiveAccountByEmail(String email);

    List<Account> getAccountsByActivationStatus(boolean status);

    List<Account> getAccountsWithActivationCode();
}

