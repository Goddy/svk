package be.spring.app.dto;

import java.util.Set;

/**
 * Created by u0090265 on 10/06/16.
 */
public class MatchPollDTO extends DTOBaseClass {
    private Set<VotesDTO> votes;
    private int totalVotes;

    public MatchPollDTO(Long id, Set<VotesDTO> votes, int totalVotes) {
        this.votes = votes;
        this.setId(id);
    }

    public Set<VotesDTO> getVotes() {
        return votes;
    }

    public void setVotes(Set<VotesDTO> votes) {
        this.votes = votes;
    }

    public int getTotalVotes() {
        return totalVotes;
    }

    public void setTotalVotes(int totalVotes) {
        this.totalVotes = totalVotes;
    }
}
