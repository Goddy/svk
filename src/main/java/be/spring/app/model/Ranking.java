package be.spring.app.model;

/**
 * Created by u0090265 on 04/06/16.
 */
public class Ranking implements Comparable<Ranking> {
    private int points;
    private Long player;

    public Long getPlayer() {
        return player;
    }

    public void setPlayer(Long player) {
        this.player = player;
    }

    public int getPonts() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    @Override
    public int compareTo(Ranking ranking) {
        return points > ranking.points ? 1 : points < ranking.points ? -1 : 0;
    }
}
