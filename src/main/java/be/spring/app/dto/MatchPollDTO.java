package be.spring.app.dto;

import java.util.Set;

/**
 * Created by u0090265 on 10/06/16.
 */
public class MatchPollDTO extends DTOBaseClass {
    private Set<VotesDTO> votes;
    private Set<AccountDTO> options;
    private int totalVotes;

    public MatchPollDTO(Long id, Set<VotesDTO> votes, Set<AccountDTO> accounts, int totalVotes) {
        this.votes = votes;
        this.totalVotes = totalVotes;
        this.setId(id);
        this.setOptions(accounts);
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

    public Set<AccountDTO> getOptions() {
        return options;
    }

    public void setOptions(Set<AccountDTO> options) {
        this.options = options;
    }
}
