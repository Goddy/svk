package be.svk.webapp.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Created by u0090265 on 8/22/15.
 */
@Embeddable
public class AccountSettings {
    //Set default value to true
    private boolean sendDoodleNotifications = true;
    private boolean sendNewsNotifications = true;

    @Column(name = "send_doodle_notifications")
    public boolean isSendDoodleNotifications() {
        return sendDoodleNotifications;
    }

    public void setSendDoodleNotifications(boolean sendDoodleNotifications) {
        this.sendDoodleNotifications = sendDoodleNotifications;
    }

    @Column(name = "send_news_notifications")
    public boolean isSendNewsNotifications() {
        return sendNewsNotifications;
    }

    public void setSendNewsNotifications(boolean sendNewsNotifications) {
        this.sendNewsNotifications = sendNewsNotifications;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AccountSettings that = (AccountSettings) o;

        if (sendDoodleNotifications != that.sendDoodleNotifications) return false;
        return sendNewsNotifications == that.sendNewsNotifications;

    }

    @Override
    public int hashCode() {
        int result = (sendDoodleNotifications ? 1 : 0);
        result = 31 * result + (sendNewsNotifications ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AccountSettings{" +
                "sendDoodleNotifications=" + sendDoodleNotifications +
                ", sendNewsNotifications=" + sendNewsNotifications +
                '}';
    }
}
