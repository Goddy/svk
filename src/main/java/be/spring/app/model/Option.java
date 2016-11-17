package be.spring.app.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * Created by u0090265 on 31/05/16.
 */
@MappedSuperclass
public abstract class Option<T> extends BaseClass {
    private T option;
    private Poll poll;

    public Option() {
    }

    public Option(T option) {
        this.option = option;
    }

    @Column(name = "opt")
    public T getOption() {
        return option;
    }

    public void setOption(T option) {
        this.option = option;
    }
}
