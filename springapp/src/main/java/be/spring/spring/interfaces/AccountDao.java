package be.spring.spring.interfaces;

import be.spring.spring.model.Account;
import be.spring.spring.interfaces.Dao;

public interface AccountDao extends Dao<Account> {
	void create(Account account, String password);

    void update(Account account, String password);

	Account findByUsername(String email);
    Account findByEmailExcludeCurrentId(String email, Long id);
    Account findById(Long id);
}
