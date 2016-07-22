package be.spring.app.dto;

import java.util.Set;

/**
 * Created by u0090265 on 10/06/16.
 */
public class MatchPollDTO extends DTOBaseClass {
    private Set<VotesDTO> votes;
    private Set<AccountDTO> options;
    private int totalVotes;
    private String status;
    private String matchDescription;
    private String matchDate;

    public MatchPollDTO(Long id, Set<VotesDTO> votes, Set<AccountDTO> accounts, int totalVotes, String status, String matchDescription, String matchDate) {
        this.votes = votes;
        this.totalVotes = totalVotes;
        this.status = status;
        this.setId(id);
        this.setOptions(accounts);
        this.matchDate = matchDate;
        this.matchDescription = matchDescription;
    }

    public MatchPollDTO(Long id, Set<VotesDTO> votes, Set<AccountDTO> accounts, int totalVotes, String status) {
        this.votes = votes;
        this.totalVotes = totalVotes;
        this.status = status;
        this.setId(id);
        this.setOptions(accounts);
    }

    public String getMatchDescription() {
        return matchDescription;
    }

    public void setMatchDescription(String matchDescription) {
        this.matchDescription = matchDescription;
    }

    public String getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(String matchDate) {
        this.matchDate = matchDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String open) {
        status = open;
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
