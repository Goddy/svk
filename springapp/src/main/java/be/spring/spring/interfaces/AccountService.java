package be.spring.spring.interfaces;

import be.spring.spring.model.Account;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;

import java.util.List;

public interface AccountService {
    boolean registerAccount(Account account, String password, Errors errors);

    boolean updateAccount(Account account, Errors errors);

    void validateUsername(String email, Errors errors);

    void validateUsernameExcludeCurrentId(String email, Long id, Errors errors);

    @Transactional(readOnly = true)
    List<Account> getAll();

    Account getAccountByEmail(String email);
}

