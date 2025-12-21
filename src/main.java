import GUI.simulatorUI;
import Simulator.simulationController;
import Vehicles.*;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        simulationController controller = new simulationController();
        SimulationControllerHolder.setController(controller);

        controller.addVehicle(VehicleFactory.createCar("V1", 50));
        controller.addVehicle(VehicleFactory.createTruck("V2", 57));
        controller.addVehicle(VehicleFactory.createBus("V3", 65));

        SwingUtilities.invokeLater(() -> {
            new simulatorUI(controller).setVisible(true);
        });
    }
}