package be.spring.app.model;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 * Created by u0090265 on 08/06/16.
 */
@Entity
@Table(name = "multiple_choice_player_vote")
@PrimaryKeyJoinColumn(name = "vote_id", referencedColumnName = "id")
public class MultipleChoicePlayerVote extends MultipleChoiceVote<Long>{
    public MultipleChoicePlayerVote(){super();}
}
