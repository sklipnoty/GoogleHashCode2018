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
 *
 * @author Sklipnoty
 */
public class Solver7 implements ISolver {

    private SelfDrivingRides sdr;
    private final Random random;
    public Map<Vehicle, List<Ride>> rides = new HashMap<>();
    public List<Vehicle> vehicles = new ArrayList<>();
    public PriorityQueue<Ride> pq = new PriorityQueue<>(new Utils.RideHeuristicComparator());
    public boolean[] removedRides;
    private static final Integer MAX_IT = 1000;
    public Map<Vehicle, List<Ride>> solution = new HashMap<>();
    private String name;

    public Solver7(SelfDrivingRides sdr, String name) {
        this.sdr = sdr;
        this.name = name;
        System.out.println("Solving " + name);
        this.random = new Random();
        init();
        ride();
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

        buildChains(new Vehicle(-1, new Intersection(0, 0)));
    }

    // Try making chains that are as long as possible.
    //TODO 
    //FIXME
    private Ride buildChains(Vehicle vehicle) {
        PriorityQueue<Ride> pq = new PriorityQueue<>(new Utils.RideHeuristicComparator());

        for (Ride next : sdr.rides) {

            if (!removedRides[next.id]) {
                next.setHeuristicScore(Utils.calculateScoreCostForOneRideWithCar(vehicle, next, sdr).getRandomHeuristicValue(1));
                pq.add(next);
            }
        }

        if (pq.peek() != null) {
            return pq.peek();
        }

        return null;

    }

    //We doen een poging om het aantal rides te maximaliseren door een chain van rides te maken die dicht bij elkaar ligt. 
    //MAW we houden rekening met de score. 
    public void ride() {
        int outerNumberOfIterations = 0;

        while (outerNumberOfIterations < 3) {
            
            System.out.println(outerNumberOfIterations);

            outerNumberOfIterations++;

            for (Vehicle vehicle : vehicles) {

                int numberOfIterations = 0;
                buildPQ(vehicle, sdr.rides);

                while (pq.size() > 0 && numberOfIterations < MAX_IT) {
                    //     System.out.println(name +" "  +pq.size());

                    numberOfIterations++;
                    Ride r = pq.peek();

                    if (random.nextInt(50) < 10) {
                        Ride next = buildChains(vehicle);
                        if (next != null) {
                            r = next;
                        }
                    }

                    //      System.out.println(pq.size() + " " + numberOfIterations);
                    if (isValidRide(vehicle.id, vehicle, r)) {

                        r = pq.poll();

                        if (r != null) {
                            vehicle.rideVehicle(r);
                            rides.get(vehicle).add(r);
                            removedRides[r.id] = true;
                            buildPQ(vehicle, sdr.rides);
                        } else {
                            return;
                        }
                    } else {
                        pq.poll();
                    }
                }
            }

            randomRiding();
        }

        solution = rides;
    }

    private void randomRiding() {
        int currentIterations = 0;

        while (currentIterations < random.nextInt(5)) {

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

    public void buildPQ(Vehicle vehicle, List<Ride> rides) {

        pq.clear();

        for (Ride r : rides) {

            if (!removedRides[r.id]) {
                //HS
                r.setHeuristicScore(Utils.calculateScoreCostForOneRideWithCar(vehicle, r, sdr).getRandomHeuristicValue((int) (r.latestFinish)));
                pq.add(r);
            }
        }
    }

    public boolean isValidRide(int rideID, Vehicle vehicle, Ride ride) {
        int totalDistance = vehicle.tick + (Utils.getDistance(vehicle.it, ride.from) + Utils.getDistance(ride.from, ride.to)) + 5;
        return ((totalDistance < ride.latestFinish) && !removedRides[rideID]);
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

}
