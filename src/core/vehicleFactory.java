package core;

import exceptions.InvalidOperationException;
import exceptions.InvalidInputException;
import vehicles.*;

import java.util.*;

public class vehicleFactory{
    private FleetManager fleetManager;   // store reference to core.FleetManager
    Scanner sc = new Scanner(System.in);

    /*Function ot check if the given parameter is empty. If it is, then it means
    that the vehicle does not have suffiecient information to be added to the fleet*/

    private static boolean hasEmpty(String... fields) {
        for (String f : fields) {
            if (f == null || f.trim().isEmpty()) return true;
        }
        return false;
    }
    // constructor
    public vehicleFactory(FleetManager fleetManager) {
        this.fleetManager = fleetManager;
    }
    public void createVehicleFromUser(String vehicleType) throws InvalidOperationException, InvalidInputException {
        switch(vehicleType){
            case "Car":
                System.out.printf("Enter an ID for the %s: ", vehicleType);
                String currId = sc.next();

                System.out.printf("Enter the %s model: ", vehicleType);
                String currModel = sc.next();
                System.out.printf("Enter the maximum speed for the %s: ", vehicleType);
                double currMaxSpeed = sc.nextDouble();
                if(currMaxSpeed <= 0) {
                    throw new InvalidInputException("Invalid Input. Maximum speed should be more than 0.");
                }
                System.out.printf("Enter the current mileage of the %s: ", vehicleType);
                double currMileage = sc.nextDouble();
                if(currMileage<= 0) {
                    throw new InvalidInputException("Invalid Input. Mileage should be more than 0.");
                }

                System.out.printf("Enter the number of wheels in the %s: ", vehicleType);
                int currWheels = sc.nextInt();
                if(currWheels <= 0) {
                    throw new InvalidInputException("Invalid Input. Wheels should be more than 0.");
                }

                Car car = new Car(currId, currModel, currMaxSpeed, currMileage, currWheels, 50, 5, 0, false);

                fleetManager.addVehicle(car);

                break;

            case "Bus":
                System.out.printf("Enter an ID for the %s: ", vehicleType);
                currId = sc.next();

                System.out.printf("Enter the %s model: ", vehicleType);
                currModel = sc.next();

                System.out.printf("Enter the maximum speed for the %s: ", vehicleType);
                currMaxSpeed = sc.nextDouble();
                if(currMaxSpeed <= 0) {
                    throw new InvalidInputException("Invalid Input. Maximum speed should be more than 0.");
                }
                System.out.printf("Enter the current mileage of the %s: ", vehicleType);
                currMileage = sc.nextDouble();
                if(currMileage<= 0) {
                    throw new InvalidInputException("Invalid Input. Mileage should be more than 0.");
                }

                System.out.printf("Enter the number of wheels in the %s: ", vehicleType);
                currWheels = sc.nextInt();
                if(currWheels <= 0) {
                    throw new InvalidInputException("Invalid Input. Wheels should be more than 0.");
                }

                Bus bus = new Bus(currId, currModel, currMaxSpeed, currMileage,  currWheels, 50, 50, 0, 5000, 0, false);

                fleetManager.addVehicle(bus);
                break;

            case "Truck":
                System.out.printf("Enter an ID for the %s: ", vehicleType);
                currId = sc.next();

                System.out.printf("Enter the %s model: ", vehicleType);
                currModel = sc.next();

                System.out.printf("Enter the maximum speed for the %s: ", vehicleType);
                currMaxSpeed = sc.nextDouble();
                if(currMaxSpeed <= 0) {
                    throw new InvalidInputException("Invalid Input. Maximum speed should be more than 0.");
                }

                System.out.printf("Enter the current mileage of the %s: ", vehicleType);
                currMileage = sc.nextDouble();
                if(currMileage<= 0) {
                    throw new InvalidInputException("Invalid Input. Mileage should be more than 0.");
                }

                System.out.printf("Enter the number of wheels in the %s: ", vehicleType);
                currWheels = sc.nextInt();
                if(currWheels <= 0) {
                    throw new InvalidInputException("Invalid Input. Wheels should be more than 0.");
                }

                Truck truck = new Truck(currId, currModel, currMaxSpeed, currMileage, currWheels, 50, 5000, 0, false);

                fleetManager.addVehicle(truck);
                break;

            case "Airplane":
                System.out.printf("Enter an ID for the %s: ", vehicleType);
                currId = sc.next();

                System.out.printf("Enter the %s model: ", vehicleType);
                currModel = sc.next();

                System.out.printf("Enter the maximum speed for the %s: ", vehicleType);
                currMaxSpeed = sc.nextDouble();
                if(currMaxSpeed <= 0) {
                    throw new InvalidInputException("Invalid Input. Maximum speed should be more than 0.");
                }

                System.out.printf("Enter the current mileage of the %s: ", vehicleType);
                currMileage = sc.nextDouble();
                if(currMileage<= 0) {
                    throw new InvalidInputException("Invalid Input. Mileage should be more than 0.");
                }

                System.out.printf("Enter the maximum altitude of flight for the %s: ", vehicleType);
                double currMaxAlt = sc.nextDouble();
                if(currMaxAlt <= 0) {
                    throw new InvalidInputException("Invalid Input. Maximum altitude should be more than 0.");
                }

                Airplane airplane = new Airplane(currId, currModel, currMaxSpeed, currMileage, currMaxAlt, 50, 50, 0, 5000, 0, false);

                fleetManager.addVehicle(airplane);
                break;

            case "Ship":
                System.out.printf("Enter an ID for the %s: ", vehicleType);
                currId = sc.next();

                System.out.printf("Enter the %s model: ", vehicleType);
                currModel = sc.next();

                System.out.printf("Enter the maximum speed for the %s: ", vehicleType);
                currMaxSpeed = sc.nextDouble();
                if(currMaxSpeed <= 0) {
                    throw new InvalidInputException("Invalid Input. Maximum speed should be more than 0.");
                }

                System.out.printf("Enter the current mileage of the %s: ", vehicleType);
                currMileage = sc.nextDouble();
                if(currMileage<= 0) {
                    throw new InvalidInputException("Invalid Input. Mileage should be more than 0.");
                }

                System.out.printf("Does the %s have sail (Enter true or false): ", vehicleType);
                boolean currSail = sc.nextBoolean();
                if(currSail != true && currSail!= false) {
                    throw new InvalidInputException("Invalid Input. It can be either 'true' or 'false.");
                }

                CargoShip ship = new CargoShip(currId, currModel, currMaxSpeed, currMileage, currSail, 5000, 0, false, 50);

                fleetManager.addVehicle(ship);
                break;

            default:
                System.out.println("You have entered an Invalid type of vehicle.");
        }
    }
    public static void createVehicleFromCSV(String vehicleType, List<Vehicle> fleet, HashSet<String> models, TreeSet<String> sortedModels, String[] currVehicle) {
        switch(vehicleType){
            case "Car":
                String id = currVehicle[1].trim();
                String model = currVehicle[2].trim();
                String maxSpeedStr = currVehicle[3].trim();
                String mileageStr = currVehicle[4].trim();
                String wheelsStr = currVehicle[5].trim();
                String fuelStr = currVehicle[6].trim();
                String passengerCapStr = currVehicle[7].trim();
                String currPassengerStr = currVehicle[8].trim();
                String maintenanceStr = currVehicle[12].trim();

                if (hasEmpty(id, model, maxSpeedStr, mileageStr, wheelsStr,
                        fuelStr, passengerCapStr, currPassengerStr, maintenanceStr)) {
                    System.out.println("Skipping Car entry, not enough parameters given. ");
                    return;
                }

                double maxSpeed = Double.parseDouble(maxSpeedStr);
                double mileage = Double.parseDouble(mileageStr);
                int wheels = Integer.parseInt(wheelsStr);
                double fuel = Double.parseDouble(fuelStr);
                int passengerCap = Integer.parseInt(passengerCapStr);
                int currPassenger = Integer.parseInt(currPassengerStr);
                boolean maintenance = Boolean.parseBoolean(maintenanceStr);

                Car car = new Car(id, model, maxSpeed, mileage, wheels, fuel, passengerCap, currPassenger, maintenance);
                fleet.add(car);
                models.add(car.getModel());
                sortedModels.add(car.getModel());

                break;

            case "Truck":
                id = currVehicle[1].trim();
                model = currVehicle[2].trim();
                maxSpeedStr = currVehicle[3].trim();
                mileageStr = currVehicle[4].trim();
                wheelsStr = currVehicle[5].trim();
                fuelStr = currVehicle[6].trim();
                String cargoCapStr = currVehicle[9].trim();
                String currCargoStr = currVehicle[10].trim();
                maintenanceStr = currVehicle[12].trim();

                if (hasEmpty(id, model, maxSpeedStr, mileageStr, wheelsStr,
                        fuelStr, cargoCapStr, currCargoStr, maintenanceStr)) {
                    System.out.println("Skipping Truck entry, not enough parameters given. ");
                    return;
                }

                maxSpeed = Double.parseDouble(maxSpeedStr);
                mileage = Double.parseDouble(mileageStr);
                wheels = Integer.parseInt(wheelsStr);
                fuel = Double.parseDouble(fuelStr);
                double cargoCap = Double.parseDouble(cargoCapStr);
                double currCargo = Double.parseDouble(currCargoStr);
                maintenance = Boolean.parseBoolean(maintenanceStr);

                Truck truck = new Truck(id, model, maxSpeed, mileage, wheels, fuel, cargoCap, currCargo, maintenance);
                fleet.add(truck);
                models.add(truck.getModel());
                sortedModels.add(truck.getModel());
                break;

            case "Bus":
                id = currVehicle[1].trim();
                model = currVehicle[2].trim();
                maxSpeedStr = currVehicle[3].trim();
                mileageStr = currVehicle[4].trim();
                wheelsStr = currVehicle[5].trim();
                fuelStr = currVehicle[6].trim();
                passengerCapStr = currVehicle[7].trim();
                currPassengerStr = currVehicle[8].trim();
                cargoCapStr = currVehicle[9].trim();
                currCargoStr = currVehicle[10].trim();
                maintenanceStr = currVehicle[12].trim();

                if (hasEmpty(id, model, maxSpeedStr, mileageStr, wheelsStr,
                        fuelStr, passengerCapStr, currPassengerStr, cargoCapStr, currCargoStr, maintenanceStr)) {
                    System.out.println("Skipping Bus entry, not enough parameters given. ");
                    return;
                }

                maxSpeed = Double.parseDouble(maxSpeedStr);
                mileage = Double.parseDouble(mileageStr);
                wheels = Integer.parseInt(wheelsStr);
                fuel = Double.parseDouble(fuelStr);
                passengerCap = Integer.parseInt(passengerCapStr);
                currPassenger = Integer.parseInt(currPassengerStr);
                cargoCap = Double.parseDouble(cargoCapStr);
                currCargo = Double.parseDouble(currCargoStr);
                maintenance = Boolean.parseBoolean(maintenanceStr);

                Bus bus = new Bus(id, model, maxSpeed, mileage, wheels, fuel,
                        passengerCap, currPassenger, cargoCap, currCargo, maintenance);
                fleet.add(bus);
                models.add(bus.getModel());
                sortedModels.add(bus.getModel());

            break;

            case "Airplane":
                id = currVehicle[1].trim();
                model = currVehicle[2].trim();
                maxSpeedStr = currVehicle[3].trim();
                mileageStr = currVehicle[4].trim();
                fuelStr = currVehicle[6].trim();
                passengerCapStr = currVehicle[7].trim();
                currPassengerStr = currVehicle[8].trim();
                cargoCapStr = currVehicle[9].trim();
                currCargoStr = currVehicle[10].trim();
                maintenanceStr = currVehicle[12].trim();
                String altitudeStr = currVehicle[13].trim();

                if (hasEmpty(id, model, maxSpeedStr, mileageStr, fuelStr,
                        passengerCapStr, currPassengerStr, cargoCapStr,
                        currCargoStr, maintenanceStr, altitudeStr)) {
                    System.out.println("Skipping Airplane entry, not enough parameters given. ");
                    return;
                }

                maxSpeed = Double.parseDouble(maxSpeedStr);
                mileage = Double.parseDouble(mileageStr);
                fuel = Double.parseDouble(fuelStr);
                passengerCap = Integer.parseInt(passengerCapStr);
                currPassenger = Integer.parseInt(currPassengerStr);
                cargoCap = Double.parseDouble(cargoCapStr);
                currCargo = Double.parseDouble(currCargoStr);
                maintenance = Boolean.parseBoolean(maintenanceStr);
                double altitude = Double.parseDouble(altitudeStr);

                Airplane airplane = new Airplane(id, model, maxSpeed, mileage, altitude,
                        fuel, passengerCap, currPassenger,
                        cargoCap, currCargo, maintenance);
                fleet.add(airplane);
                models.add(airplane.getModel());
                sortedModels.add(airplane.getModel());

                break;

            case "Ship":
                id = currVehicle[1].trim();
                model = currVehicle[2].trim();
                maxSpeedStr = currVehicle[3].trim();
                mileageStr = currVehicle[4].trim();
                fuelStr = currVehicle[6].trim();
                cargoCapStr = currVehicle[9].trim();
                currCargoStr = currVehicle[10].trim();
                String hasSailStr = currVehicle[11].trim();
                maintenanceStr = currVehicle[12].trim();

                if (hasEmpty(id, model, maxSpeedStr, mileageStr, fuelStr,
                        cargoCapStr, currCargoStr, hasSailStr, maintenanceStr)) {
                    System.out.println("Skipping Ship entry,  not enough parameters given. ");
                    return;
                }

                maxSpeed = Double.parseDouble(maxSpeedStr);
                mileage = Double.parseDouble(mileageStr);
                fuel = Double.parseDouble(fuelStr);
                cargoCap = Double.parseDouble(cargoCapStr);
                currCargo = Double.parseDouble(currCargoStr);
                boolean hasSail = Boolean.parseBoolean(hasSailStr);
                maintenance = Boolean.parseBoolean(maintenanceStr);

                CargoShip ship = new CargoShip(id, model, maxSpeed, mileage,
                        hasSail, cargoCap, currCargo,
                        maintenance, fuel);
                fleet.add(ship);
                models.add(ship.getModel());
                sortedModels.add(ship.getModel());
                break;

            default:
                System.out.println("Invalid type of vehicle.");
        }
    }
}
