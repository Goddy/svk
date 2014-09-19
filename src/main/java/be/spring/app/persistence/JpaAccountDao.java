package be.spring.app.persistence;

import be.spring.app.interfaces.AccountDao;
import be.spring.app.model.Account;
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
    private static final String GET_PASSWORD = "select password from account where id = ?";
    private static final String INSERT_ACCOUNT = "insert into account (email, account set password = ? where id = ?";

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
    public boolean checkPassword(Account account, String password) {
        String encodedPassword = jdbcTemplate.queryForObject(GET_PASSWORD, String.class, account.getId());
        return passwordEncoder.matches(password, encodedPassword);
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
    public Account findByUsernameAndActiveStatus(String username, boolean active) {
        Map<String, Object> parmetermap = getParameterMap("username", username);
        parmetermap.putAll(getParameterMap("active", active));
        return getSingleResultQuery("findAccountByUsernameAndActiveStatus", parmetermap );
    }

    @Override
    public Account findByEmailExcludeCurrentId(String username, Long id) {
        Map<String, Object> p = getParameterMap("username", username);
        p.putAll(getParameterMap("id", id));
        return getSingleResultQuery("findAccountByUsernameExcludeCurrentId", p);
    }
}
