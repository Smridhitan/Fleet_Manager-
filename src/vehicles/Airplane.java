package vehicles;

import exceptions.InsufficientFuelException;
import exceptions.InvalidOperationException;
import exceptions.OverloadException;
import interfaces.CargoCarrier;
import interfaces.FuelConsumable;
import interfaces.Maintainable;
import interfaces.PassengerCarrier;

public class Airplane extends AirVehicle implements FuelConsumable, PassengerCarrier, CargoCarrier, Maintainable {
    double fuelLevel;
    private int passengerCapacity = 200;
    private int currentPassengers;
    private double cargoCapacity = 10000;
    private double currentCargo;
    boolean maintenanceNeeded;

    public Airplane(String stringID, String model, double maxSpeed, double currentMileage, double maxAltitude,
               double fuelLevel, int passengerCapacity, int currentPassengers, double cargoCapacity,
               double currentCargo, boolean maintenanceNeeded){
        super(stringID, model, maxSpeed, currentMileage, maxAltitude);
        this.fuelLevel = fuelLevel;
        this.passengerCapacity = 50;
        this.currentPassengers = currentPassengers;
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
        System.out.printf("Flying at %.2f\n", getMaxAltitude());
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
        return 5.00;
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
        double fuelConsumed = distance/calculateFuelEfficiency();
        if(fuelLevel < fuelConsumed){
            throw new InsufficientFuelException("The airplane does not have enough fuel to travel.\n");
        }
        return fuelConsumed;
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


    // methods of interfaces.PassengerCarrier
    public void boardPassengers(int count) throws OverloadException {
        int passengerSpace = passengerCapacity - currentPassengers;
        if(count > passengerSpace){
            throw new OverloadException("The airplane does not have enough space.\n");
        }
        currentPassengers += count;

    };

    public void disembarkPassengers(int count) throws InvalidOperationException {
        if(count > currentPassengers){
            throw new InvalidOperationException("Not enough people in the airplane to disembark.\n");
        }
    };

    public int getPassengerCapacity(){
        return passengerCapacity;
    };

    public int getCurrentPassengers(){
        return currentPassengers;
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



