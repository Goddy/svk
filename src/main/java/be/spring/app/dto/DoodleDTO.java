package be.spring.app.dto;

import be.spring.app.model.BaseClass;

import java.util.Set;

/**
 * Created by u0090265 on 09/09/16.
 */
public class DoodleDTO extends BaseClass {
    private Set<PresenceDTO> presences;
    private PresenceDTO currentPresence;
    private int total;

    public DoodleDTO(long id, Set<PresenceDTO> presences, PresenceDTO currentPresence, int total) {
        this.id = id;
        this.presences = presences;
        this.total = total;
        this.currentPresence = currentPresence;
    }

    public Set<PresenceDTO> getPresences() {
        return presences;
    }

    public void setPresences(Set<PresenceDTO> presences) {
        this.presences = presences;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public PresenceDTO getCurrentPresence() {
        return currentPresence;
    }

    public void setCurrentPresence(PresenceDTO currentPresence) {
        this.currentPresence = currentPresence;
    }
}
