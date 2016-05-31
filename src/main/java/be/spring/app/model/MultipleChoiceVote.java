package be.spring.app.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 * Created by u0090265 on 31/05/16.
 */
@Embeddable
public class MultipleChoiceVote<T> extends Vote {
    private T answer;

    public MultipleChoiceVote() {
    }

    @NotNull
    @Column(name = "answer")
    public T getAnswer() {
        return answer;
    }

    public void setAnswer(T answer) {
        this.answer = answer;
    }
}
