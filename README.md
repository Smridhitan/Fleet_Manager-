## Overview

### Major Implementations
1. Dynamic Fleet Management using Collections:
The fleet is now stored in an ArrayList. This allows vehicles to be dynamically added and removed during runtime.

2. Introduction to Sets:
- A HashSet is used to maintain unique model names, ensuring that no duplicates are present.
- A TreeSet provides automatic alphabetical ordering of model names or other comparable parameters.

3. Sorting and Comparison Features:
I have added multiple Comparator implementations to sort vehicles by -
- Speed
- Fuel Efficiency
- Model Name
These are demonstrated through both Collections.sort() and TreeSet-based ordering.

4. Use of Collections.min() and Collections.max():
The system can now determine and display the fastest and slowest vehicles currently in the fleet based on Speed.

6. Enhanced File Persistence:
- Vehicles can be loaded from a CSV file, the program automatically creates the object vehicle.
- Data can be saved back to a CSV file. If a file with the given name doesn’t exist, it is automatically created.
- All I/O operations are wrapped in robust try-catch-finally blocks with proper exception handling and validation

7. Proper Input, Output, and CLI
- A menu-driven command-line interface enables:
- Adding/removing vehicles
- Viewing the current fleet
- Sorting by various criteria
- Finding the fastest/slowest vehicles
- Saving/loading from CSV files

### Core OOPs Design
The same class hierarchy from Assignment 1 has been reused and extended.
- Abstract Base Class: Vehicle
- Abstract Subclass: Land Vehicle, Air Vehicle and Water Vehicle
- Concrete Class: Car, Truck, Bus, Airplane, CargoShip
- Interfaces: FuelConsumable, CargoCarrier, PassengerCarrier, Maintainable.
  
These classes demonstrate the following concepts -
- Abstraction: Abstract base classes for shared structure.
- Inheritance: Multi-level hierarchy for specialization.
- Polymorphism: Unified move(), calculateFuelEfficiency(), and displayInfo() behaviors across vehicle types.
- Encapsulation: All fields are private with controlled access through getters/setters.
- Interfaces: Used to modularize features like fuel management and maintenance.
The FleetManager class continues to orchestrate these objects using polymorphism.

### Collections Usage and Justification -
1. ArrayList<Vehicle> 
- Usage: Main storage for all vehicles in the fleet
- Justification: ArrayList makes dynamic resizing possible. It helps in easy iteration and sorting
2. HashSet<String>
- Usage: Stores unique model names
- Justification: Ensures no duplicate models are added
3. TreeSet<String>
- Usage: Maintains sorted order of model names
- Justification - Automatically keeps alphabetical order
4. Comparator Implementations
- Usage - Used for sorting by speed, efficiency, or model
- Justification - Demonstrates flexible rendering using comparator

### File Handling -
The fleet data is saved into or loaded from a CSV file.
On loading, the program parses each line of the file. The vehicle type is then identified, and
based on that, the vehicle object is constructed. If there are any missing parameters, then the
vehicle is not added ot the fleet. For the creation of the vehicleFactory is called, and within
that, the method createVehicleFromCSV is called, which creates and adds the vehicle to the
fleet.

When saving, if the specified file does not exist, it is automatically created. If it exists, then the
file is overwritten. Before exiting the program, the user is by default asked if they want to save
the current fleet to the file or not keep the changes.
