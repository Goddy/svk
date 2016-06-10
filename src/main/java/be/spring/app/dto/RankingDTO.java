package be.spring.app.dto;

/**
 * Created by u0090265 on 10/06/16.
 */
public class RankingDTO {
    private AccountDTO accountDTO;
    private int votes;

    public RankingDTO(AccountDTO accountDTO, int votes) {
        this.accountDTO = accountDTO;
        this.votes = votes;
    }

    public AccountDTO getAccountDTO() {
        return accountDTO;
    }

    public void setAccountDTO(AccountDTO accountDTO) {
        this.accountDTO = accountDTO;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }
}
