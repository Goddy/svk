package be.svk.webapp.dto;

/**
 * Created by u0090265 on 10/06/16.
 */
public class VotesDTO implements Comparable<VotesDTO>{
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

    @Override
    public int compareTo(VotesDTO o) {
        if (this.votes < o.votes) return 1;
        else if (this.votes == o.votes) return this.account.compareTo(o.account);
        else return -1;
    }
}
