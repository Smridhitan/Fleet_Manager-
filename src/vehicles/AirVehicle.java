package vehicles;

import exceptions.InsufficientFuelException;
import exceptions.InvalidOperationException;

abstract class AirVehicle extends Vehicle {
    private double maxAltitude;

    public AirVehicle(String stringID, String model, double maxSpeed, double currentMileage, double maxAltitude){
        super(stringID, model, maxSpeed, currentMileage);
        this.maxAltitude = maxAltitude;
    }

    @Override public double estimateJourneyTime(double distance){
        double baseJourneyTimeAV = distance/getMaxSpeed();
        double journeyTimeAV = baseJourneyTimeAV*0.9;

        return journeyTimeAV;
    }

    public double getMaxAltitude(){

        return this.maxAltitude;
    }

    public abstract void move(double distance) throws InvalidOperationException, InsufficientFuelException;
    public abstract double calculateFuelEfficiency();
}
