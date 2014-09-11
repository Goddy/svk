package be.spring.spring.persistence;

import be.spring.spring.model.Account;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * User: Tom De Dobbeleer
 * Date: 12/13/13
 * Time: 3:13 PM
 * Remarks: none
 */
public class UserDetailsAdapter implements UserDetails {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Account account;
    private String password;

    public UserDetailsAdapter(Account account) {
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }

    public Long getId() {
        return account.getId();
    }

    public String getFirstName() {
        return account.getFirstName();
    }

    public String getLastName() {
        return account.getLastName();
    }

    public String getFullName() {
        return account.getFullName();
    }

    @Override
    public String getUsername() {
        return account.getUsername();
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(account.getRole().name()));
        return authorities;
    }
}