The Fleet Highway Simulator is a Java based project which is based on the concept of multithreading. It simulates multiple vehicles like Car, Bus and Truck driving on a highway. Each vehicle has its own thread. Its mileage and fuel is tracked and the thread updates the global
highway distance counter.
The GUI is swing based. It provides real time visualisation and control of the simulation including -
- Start, Stop, Resume, Stop and Refue alll buttons
- Distance covered by each vehicle
- Automatic refresh of the UI every 0.5 seconds
- Expected and actual value of the highway counter

This project demonstrates OOP, inheritance, multithreading, synchronization (locks), UI programming, and factory-based object creation.
### 1. Steps to Compile, Run and Test the Program
Prerequisites -
- Java 8 or higher
- All .java files placed under the following folder structure:
``` text
src/
|-- Main.java
|-- GUI
    |-- simulatorUI.java
|-- Simulator
    |-- simulationController.java
|-- Vehicles
    |-- Vehicle.java
    |-- LandVehicle.java
    |-- SimulationControllerHolder.java
    |-- Car.java
    |-- Bus.java
    |-- Truck.java
    |-- VehicleFactory.java
```
- Compilation -
From the src directory, give the command in the terminal

javac Main.java GUI/*.java Simulator/*.java Vehicles/*.java
- Run
java Main

## How to Test the Program
1. Start Simulation
- Click Start, all vehicle threads begin running.
- Mileage values should increase every second.
- Highway Distance updates accordingly.
2. Pause Simulation
- Click Pause, all vehicles stop incrementing mileage and fuel stops decreasing.
3. Refuel While Paused
- Click Refuel, fuel levels of that particular increse by a certain fixed amount
- Once refueled, the vehicle will start running automatically
4. Stop Simulation
- Click Stop
  - All threads stop and are joined.
  - Mileage resets to 0.
  - Highway distance resets to 0.
  - Fuel resets to initial value.
5. Toggle Sync
- Toggles the addition of highway Distance from sunchronised to unsynchrinised and vice-versa
6. Add Vehicle
- Opens a dialog box to add a running vehicle


## 2. Overview of the Design and GUI Layout

### Vehicle
- Base class implementing `Runnable`
- Manages mileage, fuel, and thread lifecycle

### LandVehicle
- Abstract subclass extending `Vehicle`
- Adds vehicle type name

### Car, Bus, Truck
- Extend `LandVehicle`
- Created via `VehicleFactory`

### SimulationController
Manages:
- Vehicle list
- Threads
- Highway distance (shared resource)
- Pause / Resume / Stop controls
- Add Vehicle

### SimulationControllerHolder
- Allows vehicle threads to update the shared highway counter

### SimulatorUI
- Swing-based GUI
- Contains a main frame with multiple control buttons
- Buttons control the simulation
- Displays mileage and fuel level of all vehicles
- UI updates every 0.5 seconds

---

## Components of the GUI Layout

- **JFrame**  
  Creates the main application window

- **Top Bar**  
  Contains simulation control buttons

- **Center Panel**  
  Displays a list of vehicles using a vertical `BoxLayout` inside a `JScrollPane`

- **Bottom Bar**  
  Displays highwa

## 3. How Simulation Threads Are Controlled via the GUI

The GUI communicates with the vehicle threads through the `SimulationController`.

### Start — `startSimulation()`
- Creates a thread for each vehicle
- Calls `v.resetState()`
- Starts each thread using `Thread.start()`

### Pause — `pauseAll()`
- Calls `v.pauseVehicle()` on each vehicle
- Inside each vehicle thread:
  - The run loop checks if `paused == true`
  - If paused, the thread sleeps for 500 ms and performs no action

### Resume — `resumeAll()`
- Clears the pause flag for each vehicle
- Threads resume execution in the next loop iteration

### Stop — `stopAll()`
- Calls `v.stopVehicle()` which sets `running = false`
- Interrupts all running threads
- Joins threads to ensure clean shutdown
- Resets all values using `resetToInitial()`

### Refuel — `refue()`
- Adds fuel to the vehicle during the paused state
- Threads resume later without disruption

## 4. Race Condition & Synchronisation Fix

Initially, the shared variable `highwayDistance` was updated by multiple threads without any
locking or synchronisation:

```java
highwayDistance += km;
```
### Race Condition Identification

Since the `highwayDistance` variable was accessed and modified concurrently by multiple
vehicle threads, race conditions occurred. This issue was visibly detected in the GUI by
observing a mismatch between the expected highway distance and the actual displayed value.

### Synchronisation Fix

To resolve this issue, a `ReentrantLock` named `vehicleLock` was introduced.

- The `recordDistance()` method was updated to acquire the lock before modifying
  `highwayDistance`
- This ensures that only **one thread updates the shared counter at a time**
- The lock is released immediately after the update is completed

This fix guarantees **correct, consistent, and thread-safe updates** to the shared
highway distance.

## 5. GUI Thread-Safety (Event Dispatch Thread)

Swing requires that **all UI operations occur on the Event Dispatch Thread (EDT)**.

### Safe UI Updates
- The UI is launched safely using `SwingUtilities.invokeLater()`
- Background vehicle threads **never directly interact with Swing components**
- Vehicle threads only update their internal state
- The GUI polls vehicle data every **500 ms** using a **Swing `Timer`**, which is a
  thread-safe way to update Swing components

### Why the EDT Is Important
Swing is **not thread-safe**. Updating UI components from outside the EDT may cause:
- Random UI freezes
- Invalid or inconsistent UI state
- Race conditions during rendering

### Benefits of Using the EDT
Using the Event Dispatch Thread ensures:
- Consistent and reliable UI rendering
- No cross-thread UI modification errors
- Smooth, predictable application behavior

