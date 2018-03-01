package util;

import domain.Intersection;
import domain.Ride;
import domain.ScoreCost;
import domain.SelfDrivingRides;
import domain.Vehicle;
import java.util.List;
import java.util.Map;

public class Utils
{
    public static int getDistance(Intersection toIntersection, Intersection fromIntersection)
    {
        return Math.abs(fromIntersection.row - toIntersection.row) + Math.abs(fromIntersection.col - toIntersection.col);
    }
    
    public static int calculateScoreForEntireSolution (Map<Vehicle, List<Ride>> carRides, SelfDrivingRides problem) {
        int score = 0;
        for(List<Ride> rides:carRides.values()) {
            int step = 0;
            Intersection currentIntersection = new Intersection(0,0);
            for(Ride ride: rides) {
                boolean bonus = false;
                step += Utils.getDistance(ride.from, currentIntersection);
                if(step <= ride.earliestStart) {
                    step = ride.earliestStart;
                    bonus = true;
                }
                
                int possibleScore = Utils.getDistance(ride.to, ride.from);
                step += possibleScore;
                
                if(step < ride.latestFinish) {
                    score += possibleScore;
                    if (bonus) {
                        score += problem.bonus;
                    }
                }
                currentIntersection = ride.to;
            } 
        }
        
        return score;
    }
    
    public static ScoreCost calculateScoreCostForOneRideWithCar(
            Vehicle car, 
            Ride ride, 
            SelfDrivingRides problem, 
            int currentStep
    ) {
        int step = currentStep;
        int score = 0;
        boolean bonus = false;
        step += Utils.getDistance(ride.from, car.it);
        
        if(step <= ride.earliestStart) {
            step = ride.earliestStart;
            bonus = true;
        }

        int possibleScore = Utils.getDistance(ride.to, ride.from);
        step += possibleScore;

        if(step < ride.latestFinish) {
            score += possibleScore;
            if (bonus) {
                score += problem.bonus;
            }
        }
        
        return new ScoreCost(score, (step - currentStep));
    } 
}
