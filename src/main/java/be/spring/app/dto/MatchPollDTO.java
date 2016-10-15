package be.spring.app.dto;

import java.util.Collections;
import java.util.List;

/**
 * Created by u0090265 on 10/06/16.
 */
public class MatchPollDTO extends DTOBaseClass {
    private List<VotesDTO> votes;
    private List<AccountDTO> options;
    private int totalVotes;
    private String status;
    private String matchDescription;
    private String matchDate;
    private Long matchId;

    public MatchPollDTO(Long id, Long matchId, List<VotesDTO> votes, List<AccountDTO> accounts, int totalVotes, String status, String matchDescription, String matchDate) {
        this.votes = votes;
        this.totalVotes = totalVotes;
        this.status = status;
        this.setId(id);
        this.setOptions(accounts);
        this.matchDate = matchDate;
        this.matchDescription = matchDescription;
        this.matchId = matchId;
    }

    public MatchPollDTO(Long id, List<VotesDTO> votes, List<AccountDTO> accounts, int totalVotes, String status) {
        this.votes = votes;
        this.totalVotes = totalVotes;
        this.status = status;
        this.setId(id);
        this.setOptions(accounts);
    }

    public Long getMatchId() {
        return matchId;
    }

    public void setMatchId(Long matchId) {
        this.matchId = matchId;
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

    public List<VotesDTO> getVotes() {
        return votes;
    }

    public void setVotes(List<VotesDTO> votes) {
        this.votes = votes;
    }

    public int getTotalVotes() {
        return totalVotes;
    }

    public void setTotalVotes(int totalVotes) {
        this.totalVotes = totalVotes;
    }

    public List<AccountDTO> getOptions() {
        return options;
    }

    public void setOptions(List<AccountDTO> options) {
        if (options != null) {
            Collections.sort(options);
        }
        this.options = options;
    }
}
