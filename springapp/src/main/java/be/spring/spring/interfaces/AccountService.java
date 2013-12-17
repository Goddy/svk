package be.spring.spring.interfaces;

import org.springframework.validation.Errors;

import be.spring.spring.model.Account;

public interface AccountService {
	boolean registerAccount(Account account, String password, Errors errors);
    boolean updateAccount(Account account, Errors errors);
    void validateUsername(String email, Errors errors);
    void validateUsernameExcludeCurrentId(String email, Long id, Errors errors);
    Account getAccountByEmail (String email);
}

