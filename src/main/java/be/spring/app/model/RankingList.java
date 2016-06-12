package be.spring.app.model;

import java.util.Collections;
import java.util.List;

/**
 * Created by u0090265 on 11/06/16.
 */
public class RankingList {
    List<Ranking> rankings;
    int totalVotes;

    public RankingList(List<Ranking> rankings, int totalVotes) {
        this.rankings = rankings;
        this.totalVotes = totalVotes;
    }

    public List<Ranking> getRankings() {
        Collections.sort(rankings, Collections.reverseOrder());
        return rankings;
    }

    public void setRankings(List<Ranking> rankings) {
        this.rankings = rankings;
    }

    public int getTotalVotes() {
        return totalVotes;
    }

    public void setTotalVotes(int totalVotes) {
        this.totalVotes = totalVotes;
    }
}
