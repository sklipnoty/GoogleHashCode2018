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
public class Solver5 implements ISolver {

    private SelfDrivingRides sdr;
    private final Random random;
    public Map<Vehicle, List<Ride>> rides = new HashMap<>();
    public List<Vehicle> vehicles = new ArrayList<>();
    public PriorityQueue<Ride> pq = new PriorityQueue<>(new Utils.RideHeuristicComparator());
    public boolean[] removedRides;
    private static final Integer MAX_IT = 100000;
    public Map<Vehicle, List<Ride>> solution = new HashMap<>();
    private String name;

    public Solver5(SelfDrivingRides sdr, String name) {
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
    }

    //We doen een poging om het aantal rides te maximaliseren door een chain van rides te maken die dicht bij elkaar ligt. 
    //MAW we houden rekening met de score. 
    public void ride() {
        int outerNumberOfIterations = 0;

        while (outerNumberOfIterations < 20) {

            outerNumberOfIterations++;

            for (Vehicle vehicle : vehicles) {

                int numberOfIterations = 0;
                buildPQ(vehicle, sdr.rides);

                while (pq.size() > 0 && numberOfIterations < MAX_IT) {
                    //     System.out.println(name +" "  +pq.size());

                    numberOfIterations++;
                    Ride r = pq.peek();

                    //      System.out.println(pq.size() + " " + numberOfIterations);
                    if (isValidRide(vehicle.id, vehicle, r)) {
//
//                        if (random.nextInt(50) < 10) {
//                            continue;
//                        }

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
        }

        solution = rides;
    }

    public void buildPQ(Vehicle vehicle, List<Ride> rides) {

        pq.clear();

        for (Ride r : rides) {

            if (!removedRides[r.id]) {
                //HS
                r.setHeuristicScore(Utils.calculateScoreCostForOneRideWithCar(vehicle, r, sdr).getRandomHeuristicValue(r.latestFinish));
                pq.add(r);
            }
        }
    }

    public boolean isValidRide(int rideID, Vehicle vehicle, Ride ride) {
        int totalDistance = vehicle.tick + (Utils.getDistance(vehicle.it, ride.from) + Utils.getDistance(ride.from, ride.to)) + 5;
        return ((totalDistance < ride.latestFinish) && !removedRides[rideID]);
    }

}
