package be.spring.app.dto;

import be.spring.app.model.BaseClass;

import java.util.Collections;
import java.util.List;

/**
 * Created by u0090265 on 09/09/16.
 */
public class DoodleDTO extends BaseClass {
    private List<PresenceDTO> presences;
    private PresenceDTO currentPresence;
    private int total;

    public DoodleDTO(long id, List<PresenceDTO> presences, PresenceDTO currentPresence, int total) {
        this.id = id;
        this.total = total;
        this.currentPresence = currentPresence;
        setPresences(presences);
    }

    public List<PresenceDTO> getPresences() {
        return presences;
    }

    public void setPresences(List<PresenceDTO> presences) {
        if (presences != null) {
            Collections.sort(presences);
        }
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
