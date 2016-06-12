package be.spring.app.dto;

/**
 * Created by u0090265 on 10/06/16.
 */
public class VotesDTO {
    private AccountDTO account;
    private int votes;

    public VotesDTO(AccountDTO accountDTO, int votes) {
        this.account = accountDTO;
        this.votes = votes;
    }

    public AccountDTO getAccount() {
        return account;
    }

    public void setAccount(AccountDTO account) {
        this.account = account;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }
}
