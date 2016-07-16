package be.spring.app.dto;

/**
 * Created by u0090265 on 15/07/16.
 */
public class GoalDTO {
    AccountDTO scorer;
    AccountDTO assist;

    public GoalDTO(AccountDTO scorer, AccountDTO assist) {
        this.scorer = scorer;
        this.assist = assist;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GoalDTO goalDTO = (GoalDTO) o;

        if (scorer != null ? !scorer.equals(goalDTO.scorer) : goalDTO.scorer != null) return false;
        return !(assist != null ? !assist.equals(goalDTO.assist) : goalDTO.assist != null);

    }

    @Override
    public int hashCode() {
        int result = scorer != null ? scorer.hashCode() : 0;
        result = 31 * result + (assist != null ? assist.hashCode() : 0);
        return result;
    }

    public AccountDTO getScorer() {
        return scorer;
    }

    public void setScorer(AccountDTO scorer) {
        this.scorer = scorer;
    }

    public AccountDTO getAssist() {
        return assist;
    }

    public void setAssist(AccountDTO assist) {
        this.assist = assist;
    }
}
