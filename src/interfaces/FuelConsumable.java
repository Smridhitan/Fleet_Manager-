package interfaces;

import exceptions.InsufficientFuelException;
import exceptions.InvalidOperationException;

public interface FuelConsumable {

    void refuel(double amount) throws InvalidOperationException;
    double getFuelLevel() throws InvalidOperationException;
    double consumeFuel(double distance) throws InsufficientFuelException;
}
