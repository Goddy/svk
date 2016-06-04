package be.spring.app.model;

import com.google.common.collect.Lists;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Created by u0090265 on 29/05/16.
 * Class for poll which has players (id's) as options. Not using a generic String makes it a bit less generic but
 * more type safe.
 */
public class PlayersPoll extends Poll<List<Ranking>> {
    private Set<Option<Long>> players;
    private Set<MultipleChoiceVote<Long>> votes;

    public PlayersPoll() {
    }

    @CollectionTable(
            name = "poll_options",
            joinColumns = {@JoinColumn(
                    name = "id"
            )}
    )
    @OrderColumn
    public Set<Option<Long>> getPlayers() {
        return players;
    }

    public void setPlayers(Set<Option<Long>> players) {
        this.players = players;
    }

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "poll")
    public Set<MultipleChoiceVote<Long>> getVotes() {
        return votes;
    }

    public void setVotes(Set<MultipleChoiceVote<Long>> votes) {
        this.votes = votes;
    }

    @Override
    @Transient
    public List<Ranking> getResult() {
        List<Ranking> rankings = Lists.newArrayList();
        for (Option<Long> p : players) {
            Ranking r = new Ranking();
            for (MultipleChoiceVote<Long> v : votes) {
                if (v.getAnswer().equals(p.getOption())) {
                    r.setPoints(r.getPonts() + 1);
                }
            }
            rankings.add(r);
        }
        Collections.sort(rankings, Collections.reverseOrder());
        return rankings;
    }
}
