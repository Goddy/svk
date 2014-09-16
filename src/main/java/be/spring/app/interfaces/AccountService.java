package be.spring.app.interfaces;

import be.spring.app.form.AccountDetailsForm;
import be.spring.app.model.Account;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;

import java.util.List;

public interface AccountService {
    boolean registerAccount(Account account, String password, Errors errors);

    boolean updateAccount(Account account, Errors errors, AccountDetailsForm form);

    void validateUsername(String email, Errors errors);

    void validateUsernameExcludeCurrentId(String email, Long id, Errors errors);

    @Transactional(readOnly = false)
    void setPasswordFor(Account account, String password);

    @Transactional(readOnly = true)
    List<Account> getAll();

    Account getActiveAccountByEmail(String email);
}

