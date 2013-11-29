package be.spring.spring.persistence;

import org.springframework.validation.Errors;

import be.spring.spring.model.Account;

public interface AccountService {
	boolean registerAccount(Account account, String password, Errors errors);
}

