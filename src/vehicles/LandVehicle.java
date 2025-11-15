package vehicles;

import exceptions.InsufficientFuelException;
import exceptions.InvalidOperationException;

abstract class LandVehicle extends Vehicle {
    private int numWheels;

    public LandVehicle(String stringID, String model, double maxSpeed, double currentMileage, int numWheels){
        super(stringID, model, maxSpeed, currentMileage);
        this.numWheels = numWheels;
    }

    @Override
    public double estimateJourneyTime(double distance){
        double baseJourneyTimeLV = distance/getMaxSpeed();
        double journeyTimeLV = baseJourneyTimeLV*1.1;

        return journeyTimeLV;
    }

    public int getNumWheels(){
        return this.numWheels;
    }
    public abstract void move(double distance) throws InvalidOperationException, InsufficientFuelException;
    public abstract double calculateFuelEfficiency();

}
