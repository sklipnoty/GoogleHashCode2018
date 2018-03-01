package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SelfDrivingRides
{
    public List<Ride> rides = new ArrayList<>();
    public int rows;
    public int cols;
    public int vehicles;
    public int numRides;
    public int bonus;
    public int steps;
    
    public SelfDrivingRides(Scanner in)
    {
        rows = in.nextInt();
        cols = in.nextInt();
        vehicles = in.nextInt();
        numRides = in.nextInt();
        bonus = in.nextInt();
        steps = in.nextInt();
        
        for (int i = 0; i < numRides; i++)
        {
            Intersection startInt = new Intersection();
            startInt.row = in.nextInt();
            startInt.col = in.nextInt();
            
            Intersection endInt = new Intersection();
            endInt.row = in.nextInt();
            endInt.col = in.nextInt();
            
            Ride ride = new Ride();
            ride.id = i;
            ride.earliestStart = in.nextInt();
            ride.latestFinish = in.nextInt();
            ride.from = startInt;
            ride.to = endInt;
            ride.setScore();
            
            rides.add(ride);
        }
    }
}
