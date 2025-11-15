package vehicles;

import exceptions.InsufficientFuelException;
import exceptions.InvalidOperationException;

public abstract class Vehicle implements Comparable<Vehicle> {
    private String stringID;
    private String model;
    private double maxSpeed;
    private double currentMileage;

    public Vehicle(String stringID, String model, double maxSpeed, double currentMileage) {
        if (stringID == null || stringID.isEmpty()) {
            throw new IllegalArgumentException("Invalid ID for the vehicle."); //do try and catch while creting the object
        } else {
            this.stringID = stringID;
            this.model = model;
            this.maxSpeed = maxSpeed;
            this.currentMileage = currentMileage;
        }
        return;
    }

    @Override
    public int compareTo(Vehicle that) {
        double efficiencyOfThis = this.calculateFuelEfficiency();
        double efficiencyOfThat = that.calculateFuelEfficiency();

        if (efficiencyOfThis < efficiencyOfThat) {
            return -1;
        } else if (efficiencyOfThis == efficiencyOfThat) {
            return 0;
        }
        return 1;
    }

    public abstract void move(double distance) throws InvalidOperationException, InsufficientFuelException;

    public abstract double calculateFuelEfficiency();

    public abstract double estimateJourneyTime(double distance);

    public void displayInfo() {
        System.out.println("Vehicle information ~");
        System.out.printf("ID - %s\n", stringID);
        System.out.printf("Model - %s\n", model);
        System.out.printf("Maximum Speed  - %s\n", maxSpeed);
        System.out.printf("Current Mileage - %s\n", currentMileage);
    }

    public double getCurrentMileage() {

        return this.currentMileage;
    }

    public void setCurrentMileage(double newCurrMileage) {

        this.currentMileage = newCurrMileage;
    }

    public String getID() {

        return this.stringID;
    }

    public String getModel() {

        return this.model;
    }

    public double getMaxSpeed() {

        return this.maxSpeed;
    }

}