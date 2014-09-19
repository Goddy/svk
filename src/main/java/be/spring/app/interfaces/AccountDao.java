package be.spring.app.interfaces;

import be.spring.app.model.Account;

public interface AccountDao extends Dao<Account> {
	void create(Account account, String password);

    boolean checkPassword(Account account, String password);

    void update(Account account, String password);

	Account findByUsername(String email);

    Account findByUsernameAndActiveStatus(String username, boolean active);

    Account findByEmailExcludeCurrentId(String email, Long id);
}
