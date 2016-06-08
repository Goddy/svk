package be.spring.app.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by u0090265 on 31/05/16.
 */
@MappedSuperclass
public class Option<T> extends BaseClass {
    private T option;
    private Poll poll;

    public Option() {
    }

    public Option(T option) {
        this.option = option;
    }

    public void setId(Long id) {
        this.id = id;
    }
    @Column(name = "option")
    public T getOption() {
        return option;
    }

    public void setOption(T option) {
        this.option = option;
    }

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="poll_id")
    @NotNull
    public Poll getPoll() {
        return poll;
    }

    public void setPoll(Poll poll) {
        this.poll = poll;
    }
}
