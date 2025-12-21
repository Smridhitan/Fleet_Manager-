package GUI;

import Simulator.simulationController;
import Vehicles.Vehicle;
import Vehicles.VehicleFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class simulatorUI extends JFrame {
    private final simulationController controller;
    private final JPanel vehiclesPanel;
    private final JLabel highwayLabel;
    private final JLabel expectedHighwayLabel;
    private final JLabel syncStatusLabel;
    private Timer refreshTimer;

    public simulatorUI(simulationController controller) {
        super("Fleet Highway Simulator");
        this.controller = controller;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 420);
        setLocationRelativeTo(null);

        // Top controls
        JButton startBtn = new JButton("Start");
        JButton pauseBtn = new JButton("Pause");
        JButton resumeBtn = new JButton("Resume");
        JButton stopBtn = new JButton("Stop");
        JButton addVehicleBtn = new JButton("Add Vehicle");
        JButton toggleSyncBtn = new JButton("Toggle Sync");

        syncStatusLabel = new JLabel("SYNC: OFF");
        syncStatusLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(startBtn);
        topPanel.add(pauseBtn);
        topPanel.add(resumeBtn);
        topPanel.add(stopBtn);
        topPanel.add(toggleSyncBtn);
        topPanel.add(addVehicleBtn);
        topPanel.add(syncStatusLabel);

        // Vehicles display
        vehiclesPanel = new JPanel();
        vehiclesPanel.setLayout(new BoxLayout(vehiclesPanel, BoxLayout.Y_AXIS));
        JScrollPane scroll = new JScrollPane(vehiclesPanel);

        // Highway counters (actual and expected)
        highwayLabel = new JLabel("Actual Distance: 0");
        expectedHighwayLabel = new JLabel("Expected Distance: 0");
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottom.add(highwayLabel);
        bottom.add(Box.createHorizontalStrut(20));
        bottom.add(expectedHighwayLabel);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(scroll, BorderLayout.CENTER);
        getContentPane().add(bottom, BorderLayout.SOUTH);

        // Wire buttons
        startBtn.addActionListener(e -> controller.startSimulation());
        pauseBtn.addActionListener(e -> controller.pauseAll());
        resumeBtn.addActionListener(e -> controller.resumeAll());
        stopBtn.addActionListener(e -> controller.stopAll());

        addVehicleBtn.addActionListener(e -> onAddVehicleClicked());
        toggleSyncBtn.addActionListener(this::onToggleSync);

        buildVehicleRows();

        refreshTimer = new Timer(1000, e -> refresh());
        refreshTimer.start();
    }

    private void buildVehicleRows() {
        vehiclesPanel.removeAll();
        List<Vehicle> list = controller.getVehicles();
        for (Vehicle v : list) {
            JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT));
            row.setName("row-" + v.getId());

            JLabel idLabel = new JLabel("ID: " + v.getId());
            JLabel mileageLabel = new JLabel("Mileage: " + v.getMileage());
            JLabel fuelLabel = new JLabel("Fuel: " + v.getFuel());
            JLabel statusLabel = new JLabel("Status: " + v.getStatus());

            JButton refuelBtn = new JButton("Refuel");
            refuelBtn.addActionListener(e -> {
                controller.refuelVehicle(v.getId(), 10); // default +10 fuel
            });

            // keep component names so refresh() can find them by order
            row.add(idLabel);
            row.add(Box.createHorizontalStrut(10));
            row.add(mileageLabel);
            row.add(Box.createHorizontalStrut(10));
            row.add(fuelLabel);
            row.add(Box.createHorizontalStrut(10));
            row.add(statusLabel);
            row.add(Box.createHorizontalStrut(10));
            row.add(refuelBtn);

            // store user data in client property to fetch later
            row.putClientProperty("vehicleId", v.getId());
            vehiclesPanel.add(row);
        }
        vehiclesPanel.revalidate();
        vehiclesPanel.repaint();
    }

    private void refresh() {
        // Refresh highway counters: actual + expected
        int actual = controller.getTotalHighwayDistance();
        int expected = controller.getExpectedHighwayDistance();

        highwayLabel.setText("Actual Distance: " + actual);
        expectedHighwayLabel.setText("Expected Distance: " + expected);

        // highlight mismatch
        if (actual != expected) {
            highwayLabel.setForeground(Color.RED);
        } else {
            highwayLabel.setForeground(Color.BLACK);
        }

        // Refresh sync status
        syncStatusLabel.setText("SYNC: " + (controller.syncEnabled ? "ON" : "OFF"));

        // Refresh each vehicle row in order
        Component[] components = vehiclesPanel.getComponents();
        for (Component comp : components) {
            if (!(comp instanceof JPanel)) continue;
            JPanel row = (JPanel) comp;
            Object idObj = row.getClientProperty("vehicleId");
            if (idObj == null) continue;
            String vid = idObj.toString();

            Vehicle v = controller.getVehicles().stream()
                    .filter(x -> x.getId().equals(vid))
                    .findFirst()
                    .orElse(null);
            if (v == null) continue;

            // update labels inside row (assumes positions)
            // index 0: idLabel, 2: mileageLabel, 4: fuelLabel, 6: statusLabel
            Component[] rowComps = row.getComponents();
            if (rowComps.length >= 7) {
                ((JLabel) rowComps[0]).setText("ID: " + v.getId());
                ((JLabel) rowComps[2]).setText("Mileage: " + v.getMileage());
                ((JLabel) rowComps[4]).setText("Fuel: " + v.getFuel());
                ((JLabel) rowComps[6]).setText("Status: " + v.getStatus());
            }
        }
    }

    private void onToggleSync(ActionEvent e) {
        boolean now = controller.toggleSync();
        syncStatusLabel.setText("SYNC: " + (now ? "ON" : "OFF"));
    }

    private void onAddVehicleClicked() {

        JTextField idField = new JTextField(10);
        JTextField fuelField = new JTextField(6);

        String[] types = {"Car", "Truck", "Bus"};
        JComboBox<String> typeBox = new JComboBox<>(types);

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 4, 4, 4);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        form.add(new JLabel("Vehicle ID:"), gbc);
        gbc.gridx = 1;
        form.add(idField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        form.add(new JLabel("Vehicle Type:"), gbc);
        gbc.gridx = 1;
        form.add(typeBox, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        form.add(new JLabel("Initial Fuel:"), gbc);
        gbc.gridx = 1;
        form.add(fuelField, gbc);

        int result = JOptionPane.showConfirmDialog(
                this, form, "Add Vehicle", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE
        );

        if (result != JOptionPane.OK_OPTION) return;

        String id = idField.getText().trim();
        String type = (String) typeBox.getSelectedItem();
        String fuelText = fuelField.getText().trim();

        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vehicle ID cannot be empty.", "Validation", JOptionPane.WARNING_MESSAGE);
            return;
        }

        boolean exists = controller.getVehicles().stream()
                .anyMatch(v -> v.getId().equals(id));
        if (exists) {
            JOptionPane.showMessageDialog(this, "A vehicle with this ID already exists.", "Validation", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int fuel;
        try {
            fuel = Integer.parseInt(fuelText);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Fuel must be an integer.", "Validation", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Vehicle v = null;
        switch (type) {
            case "Car":
                v = VehicleFactory.createCar(id, fuel);
                break;
            case "Truck":
                v = VehicleFactory.createTruck(id, fuel);
                break;
            case "Bus":
                v = VehicleFactory.createBus(id, fuel);
                break;
        }

        if (v == null) {
            JOptionPane.showMessageDialog(this, "Vehicle creation failed.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        controller.addVehicle(v);

        SwingUtilities.invokeLater(this::buildVehicleRows);
    }
}


