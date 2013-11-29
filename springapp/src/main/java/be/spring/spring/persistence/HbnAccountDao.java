package be.spring.spring.persistence;

import javax.inject.Inject;

import org.hibernate.Query;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import be.spring.spring.model.Account;


@Repository
public abstract class HbnAccountDao extends AbstractHbnDao<Account> implements AccountDao {

	private static final String UPDATE_PASSWORD_SQL = "update account set password = ? where email = ?";
	@Inject 
	private JdbcTemplate jdbcTemplate;

	public void create(Account account, String password) {
		create(account);
		jdbcTemplate.update(UPDATE_PASSWORD_SQL, password, account.getId());
	}
	public Account findByUsername(String email) {
		Query q = getSession().getNamedQuery("findAccountByEmail");
		q.setParameter("email", email);
		return (Account) q.uniqueResult();
	}
}
