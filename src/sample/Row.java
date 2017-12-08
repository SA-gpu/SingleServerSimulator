package sample;

public class Row {

    private String type;
    private double clock;
    private int floor;
    private int routes;
    private String capacity;
    private int floorQueue;

    Row(String type, double clock, int floor, int routes, String capacity, int floorQueue) {
        this.type = type;
        this.clock = clock;
        this.floor = floor;
        this.routes = routes;
        this.capacity = capacity;
        this.floorQueue = floorQueue;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setClock(double clock) {
        this.clock = clock;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public void setRoutes(int routes) {
        this.routes = routes;
    }

    public void setCapacity(String capacity) {
        this.capacity = capacity;
    }

    public void setFloorQueue(int floorQueue) {
        this.floorQueue = floorQueue;
    }

    public String getType() {
        return type;
    }

    public double getClock() {
        return clock;
    }

    public int getFloor() {
        return floor;
    }

    public int getRoutes() {
        return routes;
    }

    public String getCapacity() {
        return capacity;
    }

    public int getFloorQueue() {
        return floorQueue;
    }
}