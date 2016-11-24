package be.spring.app.model;

import be.spring.app.data.SocialMediaEnum;
import be.spring.app.utils.GeneralUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NamedQueries({
        @NamedQuery(name = "findAccountByUsername", query = "select a from Account a where a.username = :username"),
        @NamedQuery(name = "findAccountByUsernameAndActiveStatus", query = "select a from Account a where a.username = :username AND a.active = :active"),
        @NamedQuery(name = "findAccountById", query = "select a from Account a where a.id = :id"),
        @NamedQuery(name = "findAccountByUsernameExcludeCurrentId", query = "select a from Account a where a.username = :username AND a.id != :id"),
        @NamedQuery(name = "findAccountsByStatus", query = "select a from Account a where a.active = :status"),
        @NamedQuery(name = "findNotNullActivationCodes", query = "select a from Account a where a.pwdRecovery IS NOT NULL")

})
@Entity
@Table(name = "account")
public class Account extends BaseClass implements Comparable<Account> {
    private String firstName;
    private String lastName;
    private String username;
    private Role role;
    private SocialMediaEnum signInProvider;
    private String pwdRecovery;
    private boolean active = false;
    private AccountSettings accountSettings;

    private AccountProfile accountProfile;

    public Account(String firstName, String lastName, String username) {
        //Default values: null as password
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
    }

    public Account() {
    }

    @Enumerated(EnumType.STRING)
    @JsonIgnore
    @Column(name = "sign_in_provider", length = 20)
    public SocialMediaEnum getSignInProvider() {
        return signInProvider;
    }

    public void setSignInProvider(SocialMediaEnum signInProvider) {
        this.signInProvider = signInProvider;
    }

    @NotNull
    @JsonIgnore
    @Size(min = 1, max = 50)
    @Column(name = "username")
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @NotNull
    @JsonIgnore
    @Size(min = 1, max = 50)
    @Column(name = "firstname")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @JsonIgnore
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Transient
    public String getFullName() {
        StringBuilder b = new StringBuilder(firstName)
                .append(" ")
                .append(GeneralUtils.abbreviateName(lastName))
                .append(".");
        return b.toString();
    }

    @Override
    @Transient
    public String toString() {
        return firstName + " " + lastName;
    }

    @Column(name = "role")
    @JsonIgnore
    @Enumerated(EnumType.STRING)
    public Role getRole() {
        if (role == null)
            return Role.USER;
        else
            return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @NotNull
    @JsonIgnore
    @Column(name = "activated")
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Column(name = "password_recovery")
    @JsonIgnore
    public String getPwdRecovery() {
        return pwdRecovery;
    }

    public void setPwdRecovery(String pwdRecovery) {
        this.pwdRecovery = pwdRecovery;
    }

    @JsonIgnore
    @Embedded
    public AccountSettings getAccountSettings() {
        if (accountSettings == null) accountSettings = new AccountSettings();
        return accountSettings;
    }

    public void setAccountSettings(AccountSettings accountSettings) {
        this.accountSettings = accountSettings;
    }

    @OneToOne(cascade = CascadeType.ALL, optional = true, fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonIgnore
    @JoinColumn(name = "profile_id", insertable = true, updatable = true, nullable = true)
    public AccountProfile getAccountProfile() {
        if (accountProfile == null) accountProfile = new AccountProfile();
        return accountProfile;
    }

    public void setAccountProfile(AccountProfile accountProfile) {
        this.accountProfile = accountProfile;
    }

    @Override
    public boolean equals(Object obj) {
        if (! (obj instanceof Account) ) return false;
        final Account other = (Account) obj;
        return id != null && other.id != null && id.equals(other.id);
    }

    @Override
    public int compareTo(Account o) {
        return this.getFullName().compareTo(o.getFullName());
    }

    public static class Builder {

        private Long id;

        private String username;

        private String firstName;

        private String lastName;

        private String password;

        private Role role = Role.USER;

        private boolean active = false;

        private SocialMediaEnum socialSignInProvider;

        public Builder() {
        }

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder active(boolean active) {
            this.active = active;
            return this;
        }

        public Builder role(Role role) {
            this.role = role;
            return this;
        }

        public Builder signInProvider(SocialMediaEnum socialSignInProvider) {
            this.socialSignInProvider = socialSignInProvider;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Account build() {
            Account account = new Account(firstName, lastName, username);
            account.setSignInProvider(socialSignInProvider);
            account.setRole(role);
            account.setActive(active);
            return account;
        }
    }
}
