package be.spring.app.data;

/**
 * Created by u0090265 on 6/27/15.
 */
public class MatchStatisticsObject {
    private Long id;
    private Long number;
    private String name;

    public MatchStatisticsObject(long id, long number, String name) {
        this.id = id;
        this.number = number;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MatchStatisticsObject that = (MatchStatisticsObject) o;

        if (id != that.id) return false;
        if (number != that.number) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (number ^ (number >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "StatisticsGoalsObject{" +
                "id=" + id +
                ", number=" + number +
                ", name='" + name + '\'' +
                '}';
    }
}
