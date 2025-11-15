package core;

import exceptions.InputOutputException;
import exceptions.InsufficientFuelException;
import exceptions.InvalidOperationException;
import interfaces.FuelConsumable;
import interfaces.Maintainable;
import vehicles.*;

import java.io.*;
import java.util.*;

public class FleetManager {
    private List<Vehicle> fleet = new ArrayList<Vehicle>(); //This is the actual fleet
    private HashSet<String> models = new HashSet<>();   // This is a Hashset which contains of all the unique models that are present in the fleet
    private TreeSet<String> sortedModels = new TreeSet<>();     // This contains the models in a sorted order

    Scanner sc = new Scanner(System.in);

    void addVehicle(Vehicle v) throws InvalidOperationException { //Function to add vehicles into the fleet
        String vehicleId = v.getID();

        for (Vehicle currVehicle : fleet) {
            String currId = currVehicle.getID();
            if (vehicleId.equals(currId)) {
                throw new InvalidOperationException("Vehicle with same ID already in the fleet.\n");
            }

        }
        fleet.add(v);
        models.add(v.getModel());
        sortedModels.add(v.getModel());
        System.out.printf("%s added to the fleet\n", v.getClass().getSimpleName());
    }

    void removeVehicle() throws InvalidOperationException // Function to remove vehicles from fleet
    {
        boolean removed = false;
        for (Vehicle currVehicle : fleet){
            System.out.printf("%s: %s\n", currVehicle.getClass().getSimpleName(), currVehicle.getID());
        }
        System.out.print("Enter the ID of the vehicle you want to remove: ");
        String vehicleID = sc.next();

        for (Vehicle currVehicle : fleet) {
            String currId = currVehicle.getID();
            if (vehicleID.equals(currId)) {
                fleet.remove(currVehicle);
                models.remove(currVehicle.getModel());
                sortedModels.remove(currVehicle.getModel());
                removed = true;
                System.out.printf("%s with ID %s has been removed from the fleet\n", currVehicle.getClass().getSimpleName(), vehicleID);
                return;
            }
        }
        if (removed == false) {
            throw new InvalidOperationException("No vehicle with the given ID exists in the fleet.\n");
        }
    }

    void startAllJourneys(double distance) throws InputOutputException, InvalidOperationException{ //Function that starts all journeys
        if (distance <= 0) {
            throw new InvalidOperationException("Invalid Distance. Distance should be more than zero.");
        }
        for (Vehicle currVehicle : fleet) {
            try {
                currVehicle.move(distance);
            } catch (InvalidOperationException | InsufficientFuelException e) {
                System.out.printf("Journey failed for vehicle %s : %s.\n", currVehicle.getID(), e.getMessage());
            }
        }
    }

    void startParticularJourney(double distance) throws InputOutputException, InvalidOperationException{ // Function to start the journey of a particular vehicle
        if (distance <= 0) {
            throw new InvalidOperationException("Invalid Distance. Distance should be more than zero.");
        }
        boolean found = false;

        for (Vehicle currVehicle : fleet){
            System.out.printf("%s: %s\n", currVehicle.getClass().getSimpleName(), currVehicle.getID());
        }
        System.out.print("Enter the ID of the vehicle from the above list: ");
        String particularVehicleID = sc.next();

        for (Vehicle currVehicle : fleet){
            if(currVehicle.getID().equals(particularVehicleID)){
                found = true;
                try {
                    currVehicle.move(distance);
                } catch (InvalidOperationException | InsufficientFuelException e) {
                    System.out.printf("Journey failed for vehicle %s : %s.\n", currVehicle.getID(), e.getMessage());
                }
                break;
            }
        }
        if (!found) {
            throw new InvalidOperationException("Vehicle with ID " + particularVehicleID + " not found in the fleet.");
        }
    }


    double getTotalFuelConsumption(double distance) { // Function to get fuel consumption of all vehicles
        double totalFuelConsumption = 0;
        for (Vehicle currVehicle : fleet) {
            try {
                FuelConsumable vehicleWithFuel = (FuelConsumable) currVehicle;
                totalFuelConsumption += vehicleWithFuel.consumeFuel(distance);
            } catch (InsufficientFuelException e) {
                System.out.println(e.getMessage());
            }
        }
        return totalFuelConsumption;
    }

    void maintainAll() { // Takes all the vehicles for maintainence
        for (Vehicle currVehicle : fleet) {
            if (currVehicle instanceof Maintainable maintainVehicle) {
                if (maintainVehicle.needsMaintenance()) {
                    System.out.printf("Doing maintenance on: %s\n", currVehicle.getClass().getSimpleName());
                    maintainVehicle.scheduleMaintenance();
                    maintainVehicle.performMaintenance();
                    System.out.println("Maintenance done!!\n");
                }
            }
        }
    }

    List<String> searchByType(Class<?> type) { //Searches vehicles by type
        List<String> vehicleOfSomeType = new ArrayList<>();
        for (Vehicle currVehicle : fleet) {
            if (type.isInstance(currVehicle)) {
                String addition = currVehicle.getClass().getSimpleName() + ": " + currVehicle.getID();
                vehicleOfSomeType.add(addition);
            }
        }
        return vehicleOfSomeType;
    }

    void refuelVehicles(double fuelAmount) throws InvalidOperationException, InputOutputException{ //Refuels all vehicles
        if (fuelAmount <= 0) {
            throw new InvalidOperationException("Fuel amount must be positive.");
        }
        for (Vehicle currVehicle : fleet){
            if (currVehicle instanceof FuelConsumable vehicleToRefuel) {
                vehicleToRefuel.refuel(fuelAmount);
                System.out.print("\n\n");
                }
            }
    }
    void refuelParticularVehicle(double fuelAmount) throws InputOutputException, InvalidOperationException{ //Refuels a particular vehicle
        if (fuelAmount <= 0) {
            throw new InputOutputException("Invalid Amount. Fuel amount should be more than zero.");
        }
        boolean found = false;

        for (Vehicle currVehicle : fleet){
            System.out.printf("%s: %s\n", currVehicle.getClass().getSimpleName(), currVehicle.getID());
        }
        System.out.print("Enter the ID of the vehicle from the above list: ");
        String particularVehicleID = sc.next();

        for (Vehicle currVehicle : fleet){
            if(currVehicle.getID().equals(particularVehicleID)){
                found = true;
                if (currVehicle instanceof FuelConsumable vehicleToRefuel) {
                    vehicleToRefuel.refuel(fuelAmount);
                }
                System.out.print("\n\n");
                break;
            }
        }
        if (!found) {
            throw new InvalidOperationException("Vehicle with ID " + particularVehicleID + " not found in the fleet.");
        }
    }

    /* The following are multiple sorting functions based on different parameters*/

    void sortFleetByEfficiency() {
        Collections.sort(fleet);
        System.out.println("Fleet sorted by efficiency ~ \n");
        for(Vehicle currVehicle : fleet){
            System.out.println(currVehicle.getClass().getSimpleName() + " " + currVehicle.getID() + ": " + currVehicle.calculateFuelEfficiency());
        }
    }

    void sortFleetByModel(){
        Collections.sort(fleet, new Comparator<Vehicle>() {
            @Override
            public int compare(Vehicle v1, Vehicle v2) {
                return v1.getModel().compareToIgnoreCase(v2.getModel());
            }
        });
        System.out.println("Fleet sorted by model~ \n");
        for(Vehicle currVehicle : fleet){
            System.out.println(currVehicle.getClass().getSimpleName() + " " + currVehicle.getID() + ": " + currVehicle.getModel());
        }
    }

//    void sortFleetByModel(){
//        for(String currVehicle : sortedModels){
//            System.out.println(currVehicle);
//        }
//    }

    void sortFleetBySpeed(){
        Collections.sort(fleet, new Comparator<Vehicle>() {
            @Override
            public int compare(Vehicle v1, Vehicle v2) {
                return Double.compare(v1.getMaxSpeed(), v2.getMaxSpeed());
            }
        });
        System.out.println("Fleet sorted by speed~ \n");
        for(Vehicle currVehicle : fleet){
            System.out.println(currVehicle.getClass().getSimpleName() + " " + currVehicle.getID() + ": " + currVehicle.getMaxSpeed());
        }
    }
    // Function generates report
    String generateReport() {
        StringBuilder report = new StringBuilder();
        report.append("\nFleet Report ~\n");
        String totalVehicles = String.format("Total Vehicles : %d\n", fleet.size());
        report.append(totalVehicles);

        report.append("Unique models in the fleet: \n");
        report.append(models);

        report.append("\nSorted models in the fleet: \n");
        report.append(sortedModels + "\n");

        Map<String, Integer> vehicleByType = new HashMap<>();
        for (Vehicle currVehicle : fleet) {
            String vehicleType = currVehicle.getClass().getSimpleName();
            if (!vehicleByType.containsKey(vehicleType)) {
                vehicleByType.put(vehicleType, 1);
            } else {
                int currValueOfType = vehicleByType.get(vehicleType);
                vehicleByType.put(vehicleType, currValueOfType + 1);
            }
        }
        report.append("\n");
        report.append("Vehicles by type: \n");
        for (Map.Entry<String, Integer> Vehicle : vehicleByType.entrySet()) {
            String vehicleType = Vehicle.getKey();
            Integer typeCount = Vehicle.getValue();
            String vehicleCount = vehicleType + ": " + typeCount + "\n";
            report.append(vehicleCount);
        }
        report.append("\n");
        double totalEfficiency = 0;
        for (Vehicle currVehicle : fleet) {
            totalEfficiency += currVehicle.calculateFuelEfficiency();
        }
        double avgEfficiency = totalEfficiency / fleet.size();

        String averageEfficiency = String.format("Average Efficiency: %.2f \n", avgEfficiency);
        report.append(averageEfficiency);

        double totalMileage = 0;
        for (Vehicle currVehicle : fleet) {
            totalMileage += currVehicle.getCurrentMileage();
        }
        double avgMileage = totalMileage / fleet.size();
        report.append("\n");
        String averageMileage = String.format("Average Mileage: %.2f \n", avgMileage);
        report.append(averageMileage);
        report.append("\n");
        report.append("Maintenance report: \n");
//        List<vehicles.Vehicle> vehiclesNeedingMaintainence = new ArrayList<>();
        for (Vehicle currVehicle : fleet) {
            if (currVehicle instanceof Maintainable m) {
                boolean currVehicleStatus = m.needsMaintenance();
                String Status = currVehicle.getClass().getSimpleName() + ": " + currVehicleStatus+ "\n";
                report.append(Status);
            }
        }

        return report.toString();
    }

    // Gets list of vehicles needing maintenance
    List<String> getVehiclesNeedingMaintenance() {
        List<String> vehiclesNeedingMaintainence = new ArrayList<>();
        for (Vehicle currVehicle : fleet) {
            if (currVehicle instanceof Maintainable m) {
                if (m.needsMaintenance()) {
                    vehiclesNeedingMaintainence.add(currVehicle.getClass().getSimpleName() + ":" + currVehicle.getID());
                }
            }
        }
        return vehiclesNeedingMaintainence;
    }

    //This function tells the user the fastest and slowest vehicles using Collections.min() and Collections.max()
    void fastestAndSlowest(){
        if(fleet.isEmpty()){
            System.out.println("The fleet is empty :(\n");
            return;
        }
        Vehicle slowest = Collections.min(fleet, new Comparator<Vehicle>() {
            @Override
            public int compare(Vehicle v1, Vehicle v2) {
                return Double.compare(v1.getMaxSpeed(), v2.getMaxSpeed());
            }
        });

        Vehicle fastest = Collections.max(fleet, new Comparator<Vehicle>() {
            @Override
            public int compare(Vehicle v1, Vehicle v2) {
                return Double.compare(v1.getMaxSpeed(), v2.getMaxSpeed());
            }
        });
        System.out.println("The slowest and fastest vehicles in the fleet: \n");
        System.out.println("Slowest: " + slowest.getClass().getSimpleName() + ":" + slowest.getID() +  "\n\tSpeed: " + slowest.getMaxSpeed() + "km/hr\n\tModel: " + slowest.getModel() + "\n");
        System.out.println("Fastest: " + fastest.getClass().getSimpleName() + ":" + fastest.getID() +  "\n\tSpeed: " + fastest.getMaxSpeed() + "km/hr\n\tModel: " + fastest.getModel() + "\n");

    }

    // This function handles inputs via a CSV file
    void loadFromCsv(String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String vehicleData;
        reader.readLine();

        while((vehicleData = reader.readLine())!= null){
            String[] vehicleDataList = vehicleData.split(",");
            String currVehicleType = vehicleDataList[0];

            vehicleFactory.createVehicleFromCSV(currVehicleType, fleet, models, sortedModels, vehicleDataList);
        }
        reader.close();
    }

    // This functions handles saving the fleet to a CSV file
    void saveFleet(String fileName) throws InputOutputException, InvalidOperationException {
        try (Writer writer = new FileWriter(fileName)){

        writer.write("Type,ID,Model,MaxSpeed,CurrentMileage,NumWheels,FuelLevel,PassengerCapacity,CurrentPassengers,CargoCapacity,CurrentCargo,HasSail,MaintenanceNeeded\n");
        for (Vehicle currVehicle : fleet) {

            if (currVehicle instanceof Car) {
                Car car = (Car) currVehicle;
                writer.write(String.format("Car,%s,%s,%.3f,%.3f,%d,%.3f,%d,%d,,,,%b\n",
                                car.getID(), car.getModel(), car.getMaxSpeed(), car.getCurrentMileage(), car.getNumWheels(),
                                car.getFuelLevel(), car.getPassengerCapacity(), car.getCurrentPassengers(), car.needsMaintenance()
                        )
                );
            } else if (currVehicle instanceof Truck) {
                Truck truck = (Truck) currVehicle;
                writer.write(String.format("Truck,%s,%s,%.3f,%.3f,%d,%.3f,,,%.3f,%.3f,,%b\n",
                                truck.getID(), truck.getModel(), truck.getMaxSpeed(), truck.getCurrentMileage(), truck.getNumWheels(),
                                truck.getFuelLevel(), truck.getCargoCapacity(), truck.getCurrentCargo(), truck.needsMaintenance()
                        )
                );
            } else if (currVehicle instanceof Bus) {
                Bus bus = (Bus) currVehicle;
                writer.write(String.format("Bus,%s,%s,%.3f,%.3f,%d,%.3f,%d,%d,%.3f,%.3f,,%b\n",
                                bus.getID(), bus.getModel(), bus.getMaxSpeed(), bus.getCurrentMileage(), bus.getNumWheels(),
                                bus.getFuelLevel(), bus.getPassengerCapacity(), bus.getCurrentPassengers(), bus.getCargoCapacity(), bus.getCurrentCargo(), bus.needsMaintenance()
                        )
                );
            }
            else if (currVehicle instanceof Airplane) {
                Airplane airplane = (Airplane) currVehicle;
                writer.write(String.format("Airplane,%s,%s,%.3f,%.3f,,%.3f,%d,%d,%.3f,%.3f,%.3f,%b\n",
                                airplane.getID(), airplane.getModel(), airplane.getMaxSpeed(), airplane.getCurrentMileage(), airplane.getFuelLevel(),
                                airplane.getPassengerCapacity(), airplane.getCurrentPassengers(), airplane.getCargoCapacity(), airplane.getCurrentCargo(),
                                airplane.getMaxAltitude(), airplane.needsMaintenance()
                        )
                );
            } else if (currVehicle instanceof CargoShip) {
                CargoShip ship = (CargoShip) currVehicle;
                double shipFuelLevel = 0;
                if (ship.getHasSail()) {
                    shipFuelLevel = 0;
                } else {
                    shipFuelLevel = ship.getFuelLevel();
                }
                writer.write(String.format("Ship,%s,%s,%.3f,%.3f,,%.3f,,,%.3f,%.3f,%b,%b\n",
                                ship.getID(), ship.getModel(), ship.getMaxSpeed(),
                                ship.getCurrentMileage(), shipFuelLevel,
                                ship.getCargoCapacity(), ship.getCurrentCargo(),
                                ship.getHasSail(), ship.needsMaintenance()
                        )
                    );
                }
            }
        writer.close();
        } catch(IOException e) {
            throw new InputOutputException("Failed to save fleet: " + e.getMessage());
        }

    }
}

