package be.spring.app.persistence;

import be.spring.app.model.Account;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface AccountDao extends PagingAndSortingRepository<Account, Long>, JpaSpecificationExecutor<Account> {
    @Query("select a from Account a where a.username = ?1")
	Account findByUsername(String email);

    @Query("select a from Account a where a.username = ?1 AND a.active = ?2")
    Account findByUsernameAndActiveStatus(String username, boolean active);

    @Query("select a from Account a where a.id = ?1 AND a.active = ?2")
    Account findByIdAndActiveStatus(Long id, boolean active);

    @Query("select a from Account a where a.username = ?1 AND a.id <> ?2")
    Account findByEmailExcludeCurrentId(String email, Long id);

    @Query("select a from Account a where a.active = ?1")
    List<Account> findByActivationStatus(boolean status);

    @Query("select a from Account a where a.pwdRecovery IS NOT NULL")
    List<Account> findByActivationCodeNotNull();
}
