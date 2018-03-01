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
public class ScoreCost
{
    public int score;
    public int timeCost;

    public ScoreCost(int score, int timeCost)
    {
        this.score = score;
        this.timeCost = timeCost;
    }
    
    public int getRandomHeuristicValue() {
        return (int) Math.floor(score/timeCost);
    }
    
}
