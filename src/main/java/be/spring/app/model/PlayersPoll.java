package be.spring.app.model;

import com.google.common.collect.Lists;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * Created by u0090265 on 29/05/16.
 * Class for poll which has players (id's) as options. Not using a generic String makes it a bit less generic but
 * more type safe.
 */
@Entity
@Table(name = "players_poll")
@PrimaryKeyJoinColumn(name = "poll_id", referencedColumnName = "id")
public class PlayersPoll extends Poll<Long> implements MultipleChoicePoll<Long> {
    private Set<IdentityOption> options;
    private Set<MultipleChoicePlayerVote> votes;

    public PlayersPoll() { super(); }

    @Override
    public void replaceVote(Vote vote) {
        boolean updated = false;
        for (final MultipleChoicePlayerVote element : getVotes()) {
            //If the vote already exists, replace answer
            if (element.getVoter().equals(vote.getVoter())) {
                element.setAnswer((Long) vote.getAnswer());
                updated = true;
            }
        }
        if (!updated) {
            this.getVotes().add((MultipleChoicePlayerVote) vote);
        }
    }

    @Override
    @CollectionTable(
            name = "poll_options",
            joinColumns = {@JoinColumn(
                    name = "id"
            )}
    )
    @OrderColumn
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true,
            mappedBy = "poll")
    public Set<IdentityOption> getOptions() {
        return options;
    }

    public void setOptions(Set<IdentityOption> options) {
        this.options = options;
    }

    @Override
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true,
            mappedBy = "poll", targetEntity = Vote.class)
    public Set<MultipleChoicePlayerVote> getVotes() {
        return votes;
    }

    public void setVotes(Set<MultipleChoicePlayerVote> votes) {
        this.votes = votes;
    }


    @Override
    @Transient
    public RankingList<Long> getResult() {
        List<Ranking<Long>> rankings = Lists.newArrayList();
        int totalVotes = 0;
        for (Option<Long> p : options) {
            Ranking r = new Ranking();
            r.setOption(p.getOption());
            for (Vote<Long> v : votes) {
                if (v.getAnswer().equals(p.getOption())) {
                    r.setPoints(r.getPonts() + 1);
                    totalVotes++;
                }
            }
            rankings.add(r);
        }
        return new RankingList(rankings, totalVotes);
    }
}
