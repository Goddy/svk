package be.spring.app.dto;

import java.util.Set;

/**
 * Created by u0090265 on 10/06/16.
 */
public class MatchPollDTO extends DTOBaseClass {
    Set<RankingDTO> votes;

    public MatchPollDTO(Long id, Set<RankingDTO> votes) {
        this.votes = votes;
        this.setId(id);
    }

    public Set<RankingDTO> getVotes() {
        return votes;
    }

    public void setVotes(Set<RankingDTO> votes) {
        this.votes = votes;
    }
}
