package be.spring.app.model;

/**
 * Created by u0090265 on 18/06/16.
 */
public interface MultipleChoicePoll<T> {
    RankingList<T> getResult();
}
