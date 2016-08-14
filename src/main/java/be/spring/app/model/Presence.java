package be.spring.app.model;

import javax.persistence.*;

/**
 * Created by u0090265 on 10/1/14.
 */
@Entity
@Table(name = "presences", uniqueConstraints = @UniqueConstraint(columnNames = {"doodle_id", "account_id"}))
public class Presence extends BaseClass {
    private Account account;
    private boolean present;

    public enum PresenceType {
        NOT_PRESENT, PRESENT, NOT_FILLED_IN, ANONYMOUS
    }

    @OneToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="account_id")
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Column(name = "present")
    public boolean isPresent() {
        return present;
    }

    public void setPresent(boolean present) {
        this.present = present;
    }
}
