package Vehicles;

public class Bus extends LandVehicle {
    private static final int DEFAULT_CONSUMPTION = 1;

    public Bus(String id, int initialFuel) {
        super(id, initialFuel, DEFAULT_CONSUMPTION, "Bus");
    }
}

