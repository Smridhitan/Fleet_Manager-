package vehicles;

import exceptions.InsufficientFuelException;
import exceptions.InvalidOperationException;
import exceptions.OverloadException;
import interfaces.CargoCarrier;
import interfaces.FuelConsumable;
import interfaces.Maintainable;

public class CargoShip  extends WaterVehicle implements CargoCarrier, Maintainable, FuelConsumable {
    private double cargoCapacity = 50000;
    private double currentCargo;
    boolean maintenanceNeeded;
    double fuelLevel;

    public CargoShip(String stringID, String model, double maxSpeed, double currentMileage, boolean hasSail,
                     double cargoCapacity, double currentCargo, boolean maintenanceNeeded, double fuelLevel ){
        super(stringID, model, maxSpeed, currentMileage, hasSail);
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
        if(getHasSail()){
            System.out.printf("Mileage before: %.2f\n", getCurrentMileage());
            double currMileage = getCurrentMileage();
            double newMileage = currMileage + distance;
            setCurrentMileage(newMileage);
            System.out.printf("Mileage after: %.2f\n", getCurrentMileage());
            System.out.println("No change in fuel, ship has sail.");
        }
        else{
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
        }
    public double calculateFuelEfficiency() {
        if (!getHasSail()) {
            return 4.00;
        } else {
            return 0;
        }
    }

    // methods of interfaces.FuelConsumable
    public void refuel(double amount) throws InvalidOperationException {
        if(amount <= 0){
            throw new InvalidOperationException("The fuel amount has to be more than zero for refuelling.\n");
        }
        if(!getHasSail()){
            fuelLevel += amount;
        }
        else{
            throw new InvalidOperationException("The vehicle has sail. Cannot add fuel.\n");
        }


    }

    public double getFuelLevel() throws InvalidOperationException {
        if(getHasSail()){
            throw new InvalidOperationException("The ship travels by sail and not fuel\n");
        }
        return this.fuelLevel;
    }

    public double consumeFuel(double distance) throws InsufficientFuelException {
        double fuelConsumed = 0;
        if (!getHasSail()) {
            fuelConsumed = distance/calculateFuelEfficiency();
            if (fuelLevel < fuelConsumed) {
                throw new InsufficientFuelException("The ship does not have enough fuel to travel.\n");
            }
            return fuelConsumed;
        }
        return fuelConsumed;
//        else{
//            throw new exceptions.InvalidOperationException("The ship travels by sail and not fuel");
//        }
    }

    // methods for cargoCarrier
    public void loadCargo(double weight) throws OverloadException {
        double cargoSpace = cargoCapacity - currentCargo;
        if(weight > cargoSpace){
            throw new OverloadException("The airplane does not have enough space for given cargo.\n");
        }
        currentCargo += weight;

    };

    public void unloadCargo(double weight) throws InvalidOperationException {
        if(weight > currentCargo){
            throw new InvalidOperationException("Not enough cargo in airplane to unload.\n");
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
