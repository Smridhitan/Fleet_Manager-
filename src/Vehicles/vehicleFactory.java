package Vehicles;

public class VehicleFactory {
    public static Vehicle createCar(String id, int fuel) {
        return new Car(id, fuel);
    }
    public static Vehicle createTruck(String id, int fuel) {
        return new Truck(id, fuel);
    }
    public static Vehicle createBus(String id, int fuel) {
        return new Bus(id, fuel);
    }
}

