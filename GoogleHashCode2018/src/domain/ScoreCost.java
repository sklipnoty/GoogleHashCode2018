/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

/**
 *
 * @author Bart
 */
public class ScoreCost {

    public int score;
    public int timeCost;

    public ScoreCost(int score, int timeCost) {
        this.score = score;
        this.timeCost = timeCost;
    }

    public int getRandomHeuristicValue() {
        // System.out.println("[S] " + score + " [T] " +timeCost);
        if (score > 0 && timeCost > 0) {
            double value = (((double) timeCost / (double) score));
        //    System.out.println("[T] " + timeCost + " [S] " + score + " [V] " + (int) value);
            return (int) value;
        } else {
            return 500000;
        }

    }
}
