package domain;

public class Vehicle {
    public int id;
    public Intersection it;
    public int tick;

    public Vehicle(int id, Intersection it) {
        this.it = it;
        this.id = id;
    }
    
    public void addTicks(int ticks) {
        this.tick += ticks;
    }
}
