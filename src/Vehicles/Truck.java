package Vehicles;


public class Truck extends LandVehicle {
    private static final int DEFAULT_CONSUMPTION = 1;

    public Truck(String id, int initialFuel) {
        super(id, initialFuel, DEFAULT_CONSUMPTION, "Truck");
    }
}
