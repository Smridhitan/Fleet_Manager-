package Vehicles;

public class Car extends LandVehicle {
    private static final int DEFAULT_CONSUMPTION = 1;

    public Car(String id, int initialFuel) {
        super(id, initialFuel, DEFAULT_CONSUMPTION, "Car");
    }
}
