package vehicles;

import exceptions.InsufficientFuelException;
import exceptions.InvalidOperationException;

abstract class WaterVehicle extends Vehicle {
    private boolean hasSail;

    public WaterVehicle(String stringID, String model, double maxSpeed, double currentMileage, boolean hasSail){
        super(stringID, model, maxSpeed, currentMileage);
        this.hasSail = hasSail;
    }

    @Override public double estimateJourneyTime(double distance){
        double baseJourneyTimeWV = distance/getMaxSpeed();
        double journeyTimeWV = baseJourneyTimeWV*1.15;

        return journeyTimeWV;
    }

    public boolean getHasSail(){
        return hasSail;
    }
    public abstract void move(double distance) throws InvalidOperationException, InsufficientFuelException;
    public abstract double calculateFuelEfficiency();
}
