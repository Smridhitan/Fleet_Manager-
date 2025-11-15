package vehicles;

import exceptions.InsufficientFuelException;
import exceptions.InvalidOperationException;
import exceptions.OverloadException;
import interfaces.CargoCarrier;
import interfaces.FuelConsumable;
import interfaces.Maintainable;

public class Truck extends LandVehicle implements FuelConsumable, CargoCarrier, Maintainable {
    double fuelLevel;
    private double cargoCapacity = 5000;
    private double currentCargo;
    boolean maintenanceNeeded;

    public Truck(String stringID, String model, double maxSpeed, double currentMileage, int numWheels,
               double fuelLevel, double cargoCapacity, double currentCargo, boolean maintenanceNeeded){
        super(stringID, model, maxSpeed, currentMileage, numWheels);
        this.fuelLevel = fuelLevel;
        this.cargoCapacity = 5000;
        this.currentCargo = currentCargo;
        this.maintenanceNeeded = maintenanceNeeded;
    }

    @Override
    public void move(double distance) throws InvalidOperationException, InsufficientFuelException {
        System.out.println(this.getClass().getSimpleName());
        if (distance < 0) {
            throw new InvalidOperationException("Invalid Distance. Distance should be more than zero.\n");
        }
        System.out.println("Hauling Cargo....");
        System.out.printf("Mileage before: %.2f\n", getCurrentMileage());
        System.out.printf("Fuel level before travel: %.2f\n", fuelLevel);
        double currMileage = getCurrentMileage();
        double newMileage = currMileage + distance;

        setCurrentMileage(newMileage);
        double fuelConsumed2 = consumeFuel(distance);
        fuelLevel = fuelLevel - fuelConsumed2;
        System.out.printf("Mileage after: %.2f\n", getCurrentMileage());
        System.out.printf("Fuel level before travel: %.2f\n", fuelLevel);
        System.out.printf("Fuel consumed: %.2f\n", fuelConsumed2);
    }

    public double calculateFuelEfficiency(){
        return 8.00;
    }

    // methods of interfaces.FuelConsumable
    public void refuel(double amount) throws InvalidOperationException {
        if(amount <= 0){
            throw new InvalidOperationException("The fuel amount has to be more than zero for refuelling.\n");
        }
        fuelLevel += amount;

    }

    public double getFuelLevel(){
        return this.fuelLevel;
    }

    public double consumeFuel(double distance) throws InsufficientFuelException {
        double fuelEfficency = calculateFuelEfficiency();
        if(currentCargo > 0.5*cargoCapacity){
            fuelEfficency = fuelEfficency*0.9;
        }
        double fuelConsumed = distance/fuelEfficency;
        if(fuelLevel < fuelConsumed){
            throw new InsufficientFuelException("The truck does not have enough fuel to travel.\n");
        }
        return fuelConsumed;
    }

    // methods for cargoCarrier
    public void loadCargo(double weight) throws OverloadException {
        double cargoSpace = cargoCapacity - currentCargo;
        if(weight > cargoSpace){
            throw new OverloadException("The truck does not have enough space for given cargo.\n");
        }
        currentCargo += weight;

    };

    public void unloadCargo(double weight) throws InvalidOperationException {
        if(weight > currentCargo){
            throw new InvalidOperationException("Not enough cargo in truck to unload.\n");
        }
    };

    public double getCargoCapacity(){
        return cargoCapacity;
    };

    public double getCurrentCargo(){
        return currentCargo;
    };


    // methods of interfaces.Maintainable
    public void scheduleMaintenance() {
        maintenanceNeeded = true;
    };

    public boolean needsMaintenance(){
        return getCurrentMileage() > 10000;
    };

    public void performMaintenance(){
        maintenanceNeeded = false;
    };

}
