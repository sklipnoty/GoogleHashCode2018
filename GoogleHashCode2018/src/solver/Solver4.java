/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package solver;

import domain.Intersection;
import domain.Ride;
import domain.SelfDrivingRides;
import domain.Vehicle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;
import util.Utils;

/**
 * Greedy in terms of Time
 *
 * @author Sklipnoty
 */
public class Solver4 implements ISolver {

    private SelfDrivingRides sdr;
    private final Random random;
    public Map<Vehicle, List<Ride>> rides = new HashMap<>();
    public List<Vehicle> vehicles = new ArrayList<>();
    public PriorityQueue<Ride> pq = new PriorityQueue<>(new Utils.RideTimeComparator());
    public boolean[] removedRides;
    private static final Integer MAX_IT = 10000;
    public Map<Vehicle, List<Ride>> solution = new HashMap<>();
    private String name;

    public Solver4(SelfDrivingRides sdr, String name) {
        this.sdr = sdr;
        this.name = name;
        System.out.println("Solving " + name);
        this.random = new Random();
        init();
        greedy();
    }

    public void init() {
        this.removedRides = new boolean[sdr.numRides];

        for (Ride r : sdr.rides) {
            pq.add(r);
        }

        this.vehicles = new ArrayList<>();

        for (int i = 0; i < sdr.vehicles; i++) {
            vehicles.add(new Vehicle(i, new Intersection(0, 0)));
        }

        for (Vehicle vehicle : vehicles) {
            List<Ride> ridesA = new ArrayList<>();
            rides.putIfAbsent(vehicle, ridesA);
        }
    }

    private void greedy() {

        int numberOfIterations = 0;

        while (pq.size() > 0 && numberOfIterations < MAX_IT) {
            //     System.out.println(name +" "  +pq.size());

            numberOfIterations++;
            Ride r = pq.peek();

            //      System.out.println(pq.size() + " " + numberOfIterations);
            for (Vehicle vehicle : vehicles) {

                if (isValidRide(vehicle.id, vehicle, r)) {

                    if (random.nextInt(50) < 25) {
                        continue;
                    }

                    // check if vehicle is "close"          
                    int minDistance = Integer.MAX_VALUE;

                    for (Vehicle v : vehicles) {
                        int distance = Utils.getDistance(v.it, r.from);

                        if (distance < minDistance) {
                            minDistance = distance;
                        }
                    }

                    double cd = ((double) Utils.getDistance(vehicle.it, r.from));

                    double distance = minDistance / cd;
                    
                    if (distance > 0.08) {
                        continue;
                    }

                    r = pq.poll();

                    if (r != null) {
                        vehicle.rideVehicle(r);
                        rides.get(vehicle).add(r);
                        removedRides[r.id] = true;
                    } else {
                        return;
                    }
                }
            }

            pq.poll();
        }

        randomRiding();

        solution = rides;
    }

    private void randomRiding() {
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
                vehicle.rideVehicle(ride);
            }
        }
    }

    public Ride pickRandomValidRide(Vehicle vehicle) {
        Ride currentBestRide = null;
        int randomRide = random.nextInt(sdr.numRides);
        Ride r = sdr.rides.get(randomRide);
        int currentBestScore = 0;
        int locationOfBestRide = 0;

        for (int i = 0; i < sdr.numRides / 3; i++) {
            int current = Utils.calculateScoreCostForOneRideWithCar(vehicle, r, sdr).getRandomHeuristicValue(1);

            if (currentBestScore < current && isValidRide(randomRide, vehicle, r)) {
                currentBestRide = r;
                currentBestScore = current;
                locationOfBestRide = randomRide;
            }

            r = sdr.rides.get(randomRide);
        }

        removedRides[locationOfBestRide] = true;
        return currentBestRide;
    }

    public boolean isValidRide(int rideID, Vehicle vehicle, Ride ride) {
        int totalDistance = vehicle.tick + (Utils.getDistance(vehicle.it, ride.from) + Utils.getDistance(ride.from, ride.to) ) + 5;
        return ((totalDistance < ride.latestFinish) && !removedRides[rideID]);
    }
}
