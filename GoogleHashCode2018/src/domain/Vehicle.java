package domain;

import util.Utils;

public class Vehicle {
    public int id;
    public Intersection it;
    public int tick;

    public Vehicle(int id, Intersection it) {
        this.it = it;
        this.id = id;
        tick = 0;
    }
    
    public void addTicks(int ticks) {
        this.tick += ticks;
    }
    
    public void rideVehicle (Ride ride) 
    {
        this.tick += Utils.getDistance(ride.from, it);
        if(this.tick < ride.earliestStart) {
            this.tick = ride.earliestStart; //wait
        }
        this.tick += Utils.getDistance(ride.to, ride.from);
        this.it = ride.to;
    }
}
