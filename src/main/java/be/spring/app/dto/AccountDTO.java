package be.spring.app.dto;

/**
 * Created by u0090265 on 10/06/16.
 */
public class AccountDTO extends DTOBaseClass implements Comparable<AccountDTO>{
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(AccountDTO o) {
        return o.name.compareTo(this.name);
    }
}
