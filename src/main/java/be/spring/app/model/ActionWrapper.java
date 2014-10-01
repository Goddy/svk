package be.spring.app.model;

import java.util.Map;

/**
 * Created by u0090265 on 8/30/14.
 */
public class ActionWrapper<T> {
    private T object;
    private Map additions;

    public ActionWrapper(T object) {
        this.object = object;
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }

    public Map getAdditions() {
        return additions;
    }

    public void setAdditions(Map additions) {
        this.additions = additions;
    }
}
