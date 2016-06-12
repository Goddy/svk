package be.spring.app.dto;

/**
 * Created by u0090265 on 11/06/16.
 */
public class MultipleChoiceVoteDTO<T> {
    private T answer;

    public T getAnswer() {
        return answer;
    }

    public void setAnswer(T answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "MultipleChoiceVoteDTO{" +
                "answer=" + answer +
                '}';
    }
}
