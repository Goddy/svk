package be.spring.app.model;

import javax.persistence.Embeddable;

/**
 * Created by u0090265 on 8/22/15.
 */
@Embeddable
public class AccountSettings {
    //Set default value to true
    private boolean sendDoodleNotifications = true;

    public boolean isSendDoodleNotifications() {
        return sendDoodleNotifications;
    }

    public void setSendDoodleNotifications(boolean sendDoodleNotifications) {
        this.sendDoodleNotifications = sendDoodleNotifications;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AccountSettings that = (AccountSettings) o;

        if (sendDoodleNotifications != that.sendDoodleNotifications) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (sendDoodleNotifications ? 1 : 0);
    }

    @Override
    public String toString() {
        return "AccountSettings{" +
                "sendDoodleNotifications=" + sendDoodleNotifications +
                '}';
    }
}
