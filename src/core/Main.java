package core;

import exceptions.InputOutputException;
import exceptions.InvalidInputException;
import exceptions.InvalidOperationException;
import vehicles.*;

import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws InvalidOperationException, InputOutputException {
        Scanner sc = new Scanner(System.in);
        FleetManager fleetManager = new FleetManager();
        vehicleFactory worker = new vehicleFactory(fleetManager);

        String option = "0";
        String menu = "\n-*-*- Fleet Manager Menu -*-*- \n" +
                "1. Add vehicles\n" +
                "2. Remove vehicles\n" +
                "3. Start Journey\n" +
                "4. Start journey for a particular vehicle\n" +
                "5. Refuel all vehicles\n" +
                "6. Refuel a particular vehicle\n" +
                "7. Perform Maintenance on all vehicles\n" +
                "8. Generate Report\n" +
                "9. Sort fleet\n" +
                "10. Save Fleet to File\n" +
                "11. Load from File to Fleet\n" +
                "12. Search by Type\n" +
                "13. List Vehicles Needing Maintenance\n" +
                "14. Get total Fuel Consumption\n" +
                "15. Find fastest and slowest vehicle\n" +
                "16. Exit\n";

        while (!option.equals("16")) {
            System.out.print(menu);
            System.out.print("Enter an option from the given menu: ");

            option = sc.next();


            switch (option) {
                case "1":
                    try {
                        System.out.print(" ~ Car\n ~ Truck\n ~ Bus\n ~ Airplane\n ~ Ship\nEnter one of the above mentioned vehicles: ");
                        String vehicleType = sc.next();
                        worker.createVehicleFromUser(vehicleType);
                    } catch(InvalidOperationException | InvalidInputException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case "2":
                    try {
                        fleetManager.removeVehicle();
                    } catch (InvalidOperationException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case "3":
                    System.out.print("Enter the distance you want the vehicles to cover: ");
                    try{double vehicleDistance = sc.nextDouble();
                        fleetManager.startAllJourneys(vehicleDistance);
                    }catch (InputOutputException | InvalidOperationException e) {
                        System.out.println(e.getMessage());
                    }finally {
                        System.out.println("Exited from Journey task.\n");
                    }
                    break;

                case "4":
                    System.out.print("Enter the distance you want the vehicle to cover: ");
                    double vehicleDistance = sc.nextDouble();


                    try{
                        fleetManager.startParticularJourney(vehicleDistance);
                    }catch (InputOutputException | InvalidOperationException e) {
                        System.out.println(e.getMessage());
                    }finally {
                        System.out.println("Exited from Journey task.\n");
                    }
                    break;

                case "5":
                    System.out.print("Enter the amount of fuel you want to add in the vehicles: ");
                    try{double vehicleFuelAmt = sc.nextDouble();
                        fleetManager.refuelVehicles(vehicleFuelAmt);
                        System.out.println("Fuel added.");

                    } catch (InvalidOperationException | InputOutputException e) {
                        System.out.println(e.getMessage());
                    } finally {
                        System.out.println("Exited from Fuel filling task.\n");
                    }
                    break;

                case "6":
                    System.out.print("Enter the amount of fuel you want to add in the vehicles: ");
                    double vehicleFuelAmt = sc.nextDouble();

                    try{
                        fleetManager.refuelParticularVehicle(vehicleFuelAmt);
                        System.out.println("Fuel added.");

                    } catch (InvalidOperationException | InputOutputException e) {
                        System.out.println(e.getMessage());
                    } finally {
                        System.out.println("Exited from Fuel filling task.\n");
                    }
                    break;

                case "7":
                    System.out.println("Performing maintenance on the vehicles: ");
                    fleetManager.maintainAll();
                    break;

                case "8":
                    System.out.print("Generating report.....\n");
                    String vehicleReport = fleetManager.generateReport();
                    System.out.println(vehicleReport);

                    break;

                case "9":
                    System.out.print("a. Sort by Speed\nb. Sort by Model\nc. Sort by Efficiency\nEnter the option for how you want to sort (a/b/c): \n");
                    char choice = sc.next().charAt(0);
                    switch (choice) {
                        case 'a':
                            System.out.println("Sorting fleet by speed.....\n");
                            fleetManager.sortFleetBySpeed();
                            break;
                        case 'b':
                            System.out.println("Sorting fleet by model.....\n");
                            fleetManager.sortFleetByModel();
                            break;
                        case 'c':
                            System.out.println("Sorting fleet by efficiency.....\n");
                            fleetManager.sortFleetByEfficiency();
                            break;
                        default:
                            System.out.println("Invalid choice.");
                    }
                    break;

                case "10":
                    try {
                        System.out.println("Enter the path of the file you want to save fleet information to: ");
                        String filePath = sc.next();
                        fleetManager.saveFleet(filePath);
                        System.out.println("Fleet successfully saved to file.");
                    } catch (InputOutputException e) {
                        System.out.println("Error saving fleet to file: " + e.getMessage());
                    } catch (InvalidOperationException e) {
                        System.out.println(e.getMessage());
                    } finally {
                        System.out.println("Exited from save fleet to file operation.");
                    }
                    break;

                case "11":
                    try {
                        System.out.println("Enter the path of the file you want to load to fleet from: ");
                        String filePath = sc.next();
                        fleetManager.loadFromCsv(filePath);
                        System.out.println("Fleet successfully loaded");
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    } finally {
                        System.out.println("Exited from save to fleet from file operation.");
                    }
                    break;

                case "12":
                    System.out.println("1. Car\n2. Truck\n3. Bus\n4. Airplane\n5. Ship\nEnter number of one of the above mentioned vehicles: ");
                    int vehicleType = sc.nextInt();
                    List<String> vehicles = new ArrayList<>();

                    switch (vehicleType) {
                        case 1:
                            vehicles = fleetManager.searchByType(Car.class);
                            if (!vehicles.isEmpty()) {
                                System.out.println("Vehicles found:");
                                for (String currVehicle : vehicles) {
                                    System.out.println(currVehicle);
                                }
                            } else {
                                System.out.println("No vehicles of this type found.");
                            }
                            break;

                        case 2:
                            vehicles = fleetManager.searchByType(Truck.class);
                            if (!vehicles.isEmpty()) {
                                System.out.println("Vehicles found:");
                                for (String currVehicle : vehicles) {
                                    System.out.println(currVehicle);
                                }
                            } else {
                                System.out.println("No vehicles of this type found.");
                            }
                            break;

                        case 3:
                            vehicles = fleetManager.searchByType(Bus.class);
                            if (!vehicles.isEmpty()) {
                                System.out.println("Vehicles found:");
                                for (String currVehicle : vehicles) {
                                    System.out.println(currVehicle);
                                }
                            } else {
                                System.out.println("No vehicles of this type found.");
                            }
                            break;

                        case 4:
                            vehicles = fleetManager.searchByType(Airplane.class);
                            if (!vehicles.isEmpty()) {
                                System.out.println("Vehicles found:");
                                for (String currVehicle : vehicles) {
                                    System.out.println(currVehicle);
                                }
                            } else {
                                System.out.println("No vehicles of this type found.");
                            }
                            break;

                        case 5:
                            vehicles = fleetManager.searchByType(CargoShip.class);
                            if (!vehicles.isEmpty()) {
                                System.out.println("Vehicles found:");
                                for (String currVehicle : vehicles) {
                                    System.out.println(currVehicle);
                                }
                            } else {
                                System.out.println("No vehicles of this type found.");
                            }
                            break;

                        default:
                            System.out.println("Invalid option entered.");

                    }
                    break;

                case "13":
                    System.out.println("Getting vehicles needing maintenance....\n");
                    List <String> maintenanceVehicles = new ArrayList<>();
                    maintenanceVehicles = fleetManager.getVehiclesNeedingMaintenance();
                    if(!maintenanceVehicles.isEmpty()){
                        for(String currVehicle : maintenanceVehicles){
                            System.out.println(currVehicle);
                        }
                    }
                    else{
                        System.out.println("No vehicle needs maintenance");
                    }
                    break;

                case "14":
                    System.out.print("Enter the distance you want to find the consumption for: \n");
                    double distanceToTravel = sc.nextDouble();
                    double totalFuel = fleetManager.getTotalFuelConsumption(distanceToTravel);
                    System.out.printf("Total fuel consumption is: %.2f\n", totalFuel);
                    break;

                case "15":
                    fleetManager.fastestAndSlowest();
                    break;

                case "16":
                    try {
                        System.out.print("Do you want to save fleet to file? Enter 'yes' or 'no': ");
                        String saveOrNot = sc.next();

                        if (saveOrNot.equalsIgnoreCase("yes")) {
                            System.out.print("Enter the path of the file you want to save fleet information to: ");
                            String filePath = sc.next().trim(); // use nextLine() if paths may contain spaces

                            java.io.File file = new java.io.File(filePath);

                            if (file.exists()) {
                                System.out.println("This file already exists. The file will be overwritten.");
                            } else {
                                System.out.println("File with this name does not exist. Data is being saved in new file.");
                            }

                            try {
                                fleetManager.saveFleet(filePath);
                                System.out.println("Fleet saved.\n");
                            } catch (InputOutputException e) {
                                System.err.println("Error while saving fleet data: " + e.getMessage());
                            }
                        }
                    } finally {
                        System.out.println("Exiting from the program....\n");
                    }
                    break;

                default:
                    System.out.println("Invalid choice entered. \n");
            }
        }
    }
}

