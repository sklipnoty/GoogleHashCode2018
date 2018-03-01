package domain;

import util.Utils;

public class Ride implements Comparable<Ride> {

    public int id;
    public Intersection from;
    public Intersection to;
    public int earliestStart;
    public int latestFinish;

    private int score;

    public Ride() {
    }

    public void setScore() {
        this.score = Utils.getDistance(from, to);
    }

    @Override
    public int compareTo(Ride ride) {
        return Integer.compare(this.score, ride.score);
    }
}
