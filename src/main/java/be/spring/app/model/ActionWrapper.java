package be.spring.app.model;

import java.util.Map;

/**
 * Created by u0090265 on 8/30/14.
 */
public class ActionWrapper<K> {
    private K object;
    private Map<String, String> additions;

    public ActionWrapper(K object) {
        this.object = object;
    }

    public K getObject() {
        return object;
    }

    public void setObject(K object) {
        this.object = object;
    }

    public Map<String, String> getAdditions() {
        return additions;
    }

    public void setAdditions(Map<String, String> additions) {
        this.additions = additions;
    }
}
