package be.spring.app.model;

import javax.persistence.*;

/**
 * Created by u0090265 on 31/05/16.
 */
@Embeddable
public class Option<T> {

    private T option;

    public Option() {
    }

    @Column(name = "option")
    public T getOption() {
        return option;
    }

    public void setOption(T option) {
        this.option = option;
    }
}
