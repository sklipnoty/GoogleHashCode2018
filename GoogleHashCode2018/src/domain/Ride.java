package domain;

import java.util.Comparator;
import util.Utils;

public class Ride {

    public int id;
    public Intersection from;
    public Intersection to;
    public int earliestStart;
    public int latestFinish;
    public int score;

    public Ride() {
    }

    public void setScore() {
        this.score = Utils.getDistance(from, to);
    }

    public int getScore() {
        return score;
    }

   
}
