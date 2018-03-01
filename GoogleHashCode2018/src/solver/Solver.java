package solver;

import domain.Intersection;
import domain.Ride;
import domain.SelfDrivingRides;
import domain.Vehicle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import util.Utils;

/**
 * Random. Pure randomness.
 * @author Sklipnoty
 */
public class Solver implements ISolver{

    public Map<Vehicle, List<Ride>> rides = new HashMap<>();
    public Map<Vehicle, List<Ride>> solution = new HashMap<>();
    public SelfDrivingRides sdr;
    public List<Vehicle> vehicles = new ArrayList<>();
    public Random random;
    public boolean[] removedRides;
    public int MAX_IT = 500000;

    public Solver(SelfDrivingRides sdr, String name) {
        this.sdr = sdr;
        System.out.println("Solving " + name);
        this.random = new Random();
        superRandomRiding();
    }
    
    public void superRandomRiding() {
        
        int bestScore = 0;
        
        for(int j = 0; j < 20; j++ ) {
            //setup
            this.removedRides = new boolean[sdr.numRides];
            this.vehicles = new ArrayList<>();
            this.rides = new HashMap<>();
            this.random = new Random();

            for (int i = 0; i < sdr.vehicles; i++) {
                vehicles.add(new Vehicle(i, new Intersection(0, 0)));
            }

            for (Vehicle vehicle : vehicles) {
                List<Ride> ridesA = new ArrayList<>();
                rides.putIfAbsent(vehicle, ridesA);
            }
            
            randomRiding();
                
            int currentScore = Utils.calculateScoreForEntireSolution(rides, sdr);
        
            if(currentScore > bestScore) {
                System.out.println(currentScore);
                solution = rides;
                bestScore = currentScore;
            }
        }
    }

    public void randomRiding() {

        int currentIterations = 0;

        while (currentIterations < MAX_IT) {

            currentIterations++;

            // Voor elk vehicle nemen we een random ride;
            for (Vehicle vehicle : vehicles) {

                Ride ride = pickRandomValidRide(vehicle);

                if (ride == null) {
                    continue;
                }

                rides.get(vehicle).add(ride);
            }
        }

    }

    public Ride pickRandomValidRide(Vehicle vehicle) {
        int randomRide = random.nextInt(sdr.numRides);
        Ride r = sdr.rides.get(randomRide);
        int numberOfIterations = 0;

        while (!isValidRide(randomRide, vehicle, r)) {
            r = sdr.rides.get(randomRide);
            numberOfIterations++;

            if (numberOfIterations >= 10000) {
                return null;
            }
        }

        removedRides[randomRide] = true;
        return r;
    }

    public boolean isValidRide(int rideID, Vehicle vehicle, Ride ride) {
        int totalDistance = Utils.getDistance(vehicle.it, ride.from) + Utils.getDistance(ride.from, ride.to);
        return ((totalDistance < ride.latestFinish) && !removedRides[rideID]);
    }

}
