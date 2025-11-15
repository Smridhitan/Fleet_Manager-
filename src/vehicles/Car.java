package vehicles;

import exceptions.InsufficientFuelException;
import exceptions.InvalidOperationException;
import exceptions.OverloadException;
import interfaces.FuelConsumable;
import interfaces.Maintainable;
import interfaces.PassengerCarrier;

public class Car extends LandVehicle implements FuelConsumable, PassengerCarrier, Maintainable {
    private double fuelLevel = 0;
    private int passengerCapacity = 5;
    private int currentPassengers;
    private boolean maintenanceNeeded;

    public Car(String stringID, String model, double maxSpeed, double currentMileage, int numWheels,
               double fuelLevel, int passengerCapacity, int currentPassengers, boolean maintenanceNeeded){
        super(stringID, model, maxSpeed, currentMileage, numWheels);
        this.fuelLevel = fuelLevel;
        this.passengerCapacity = 5;
        this.currentPassengers = currentPassengers;
        this.maintenanceNeeded = maintenanceNeeded;
    }

    @Override
    public void move(double distance) throws InvalidOperationException, InsufficientFuelException {
        System.out.println(this.getClass().getSimpleName());
        if (distance < 0) {
            throw new InvalidOperationException("Invalid Distance. Distance should be more than zero.\n");
        }
        System.out.printf("Mileage before travel: %.2f\n", getCurrentMileage());
        System.out.printf("Fuel before travel: %.2f\n", getFuelLevel());
        double currMileage = getCurrentMileage();
        double newMileage = currMileage + distance;

        double fuelConsumed = consumeFuel(distance);
        fuelLevel = fuelLevel - fuelConsumed;
        setCurrentMileage(newMileage);

        System.out.println("Driving on the road.....");
        System.out.printf("Mileage after travel: %.2f\n", getCurrentMileage());
        System.out.printf("Fuel after travel: %.2f\n", getFuelLevel());

    }
    public double calculateFuelEfficiency(){
        return 15.00;
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
            throw new InsufficientFuelException("The vehicle does not have enough fuel to travel.\n");
        }
        return fuelConsumed;
    }

    // methods of interfaces.PassengerCarrier
    public void boardPassengers(int count) throws OverloadException {
        int passengerSpace = passengerCapacity - currentPassengers;
        if(count > passengerSpace){
            throw new OverloadException("The vehicle does not have enough space.\n");
        }
        currentPassengers += count;

    };

    public void disembarkPassengers(int count) throws InvalidOperationException {
        if(count > currentPassengers){
            throw new InvalidOperationException("Not enough people in the vehicle to disembark.\n");
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
