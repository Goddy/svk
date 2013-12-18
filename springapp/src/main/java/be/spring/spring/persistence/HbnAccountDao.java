package be.spring.spring.persistence;

import javax.inject.Inject;

import be.spring.spring.interfaces.AccountDao;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import be.spring.spring.model.Account;


@Repository
public class HbnAccountDao extends AbstractHbnDao<Account> implements AccountDao {

	private static final String UPDATE_PASSWORD_SQL = "update account set password = ? where id = ?";
	@Inject	private JdbcTemplate jdbcTemplate;
    @Inject private BCryptPasswordEncoder passwordEncoder;

	public void create(Account account, String password) {
		create(account);
        String encPassword = passwordEncoder.encode(password);
		jdbcTemplate.update(UPDATE_PASSWORD_SQL, encPassword, account.getId());
	}


    public void update(Account account) {
        update(account);
    }

    @Override
	public Account findByUsername(String username) {
        Session session = getSession();
		Query q = getSession().getNamedQuery("findAccountByUsername");
		q.setParameter("username", username);
		return (Account) q.uniqueResult();
	}

    @Override
    public Account findByEmailExcludeCurrentId(String username, Long id) {
        Session session = getSession();
        Query q = getSession().getNamedQuery("findAccountByUsernameExcludeCurrentId");
        q.setParameter("username", username);
        q.setParameter("id", id);
        return (Account) q.uniqueResult();
    }

    @Override
    public Account findById(Long id) {
        Session session = getSession();
        Query q = getSession().getNamedQuery("findAccountById");
        q.setParameter("id", id);
        return (Account) q.uniqueResult();
    }
}
