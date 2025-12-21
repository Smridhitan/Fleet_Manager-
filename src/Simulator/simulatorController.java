package Simulator;

import Vehicles.Vehicle;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class simulationController {
    public static int totalDistanceCovered = 0;

    private static final AtomicInteger expectedDistanceCovered = new AtomicInteger(0);

    public volatile boolean syncEnabled = false;

    private final ReentrantLock vehicleLock = new ReentrantLock();

    private final List<Vehicle> vehicles = new ArrayList<>();
    private final List<Thread> vehicleThreads = new ArrayList<>();

    public synchronized void addVehicle(Vehicle v) {
        vehicles.add(v);

        if (!vehicleThreads.isEmpty()) {
            v.resetState();
            Thread t = new Thread(v, "Vehicle-" + v.getId());
            vehicleThreads.add(t);
            t.start();
        }
    }

    public synchronized List<Vehicle> getVehicles() {
        return new ArrayList<>(vehicles);
    }

    public int getTotalHighwayDistance() {
        return totalDistanceCovered;
    }

    public int getExpectedHighwayDistance() {
        return expectedDistanceCovered.get();
    }

    public void recordDistance(int km) {
        expectedDistanceCovered.addAndGet(km);

        if (!syncEnabled) {
            totalDistanceCovered += km;
        } else {
            vehicleLock.lock();
            try {
                totalDistanceCovered += km;
            } finally {
                vehicleLock.unlock();
            }
        }
    }

    public synchronized void startSimulation() {
        if (!vehicleThreads.isEmpty()) {
            return;
        }
        totalDistanceCovered = 0;
        expectedDistanceCovered.set(0);

        for (Vehicle v : vehicles) {
            v.resetState();
            Thread t = new Thread(v, "Vehicle-" + v.getId());
            vehicleThreads.add(t);
            t.start();
        }
    }

    public synchronized void pauseAll() {
        for (Vehicle v : vehicles){
            v.pauseVehicle();
        }
    }

    public synchronized void resumeAll() {
        for (Vehicle v : vehicles){
            v.resumeVehicle();
        }
    }

    public synchronized void stopAll(){
        for (Vehicle v : vehicles) {
            v.stopVehicle();
        }

        for (Thread t : vehicleThreads) {
            t.interrupt();
        }
        vehicleThreads.clear();

        totalDistanceCovered = 0;
        expectedDistanceCovered.set(0);

        for (Vehicle v : vehicles) {
            v.resetToZero();
        }
    }

    public synchronized void refuelVehicle(String id, int amount){
        for (Vehicle v : vehicles){
            if (v.getId().equals(id)){
                v.refuel(amount);
                break;
            }
        }
    }

    public boolean toggleSync(){
        syncEnabled = !syncEnabled;
        return syncEnabled;
    }
}
