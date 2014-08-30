package be.spring.spring.persistence;

import be.spring.spring.interfaces.AccountDao;
import be.spring.spring.model.Account;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * Created by u0090265 on 7/12/14.
 */

@Repository
public class JpaAccountDao extends AbstractJpaDao<Account> implements AccountDao {
    private static final String UPDATE_PASSWORD_SQL = "update account set password = ? where id = ?";
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void create(Account account, String password) {
        create(account);
        String encPassword = passwordEncoder.encode(password);
        jdbcTemplate.update(UPDATE_PASSWORD_SQL, encPassword, account.getId());
    }

    @Override
    public void update(Account account, String password) {
        String encPassword = passwordEncoder.encode(password);
        jdbcTemplate.update(UPDATE_PASSWORD_SQL, encPassword, account.getId());
    }

    @Override
    public Account findByUsername(String username) {
        return getSingleResultQuery("findAccountByUsername", getParameterMap("username", username));
    }

    @Override
    public Account findByEmailExcludeCurrentId(String username, Long id) {
        Map<String, Object> p = getParameterMap("username", username);
        p.putAll(getParameterMap("id", id));
        return getSingleResultQuery("findAccountByUsernameExcludeCurrentId", p);
    }
}
