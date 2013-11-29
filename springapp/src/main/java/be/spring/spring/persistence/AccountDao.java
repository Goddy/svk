package be.spring.spring.persistence;

import be.spring.spring.model.Account;


public interface AccountDao extends Dao<Account> {
	void create(Account account, String password);
	Account findByEmail(String email);
}
