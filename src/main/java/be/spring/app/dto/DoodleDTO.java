package be.spring.app.dto;

import be.spring.app.model.BaseClass;

import java.util.Set;

/**
 * Created by u0090265 on 09/09/16.
 */
public class DoodleDTO extends BaseClass {
    private Set<PresenceDTO> presences;
    private int total;

    public DoodleDTO(long id, Set<PresenceDTO> presences, int total) {
        this.id = id;
        this.presences = presences;
        this.total = total;
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
}
