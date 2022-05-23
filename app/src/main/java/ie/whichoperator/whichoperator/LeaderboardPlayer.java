package ie.whichoperator.whichoperator;

public class LeaderboardPlayer {
    private String name;
    private int score;

    public LeaderboardPlayer(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }
    public int getScore() {
        return score;
    }

    @Override
    public String toString() {
        return "name=" + name + ", score=" + score;
    }

}
