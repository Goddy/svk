package be.spring.spring.model;

import java.util.List;

/**
 * Created by u0090265 on 8/30/14.
 */
public class ActionWrapper<T> {
    private T object;
    private String htmlActions;

    public ActionWrapper(T object) {
        this.object = object;
    }

    public String getHtmlActions() {
        return htmlActions;
    }

    public void setHtmlActions(String htmlActions) {
        this.htmlActions = htmlActions;
    }

    public T getObject() {
        return object;
    }

    public void setObject(T object) {
        this.object = object;
    }
}
