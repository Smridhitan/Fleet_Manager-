package Vehicles;

public abstract class LandVehicle extends Vehicle {
    protected final String typeName;

    public LandVehicle(String id, int initialFuel, int consumptionPerTick, String typeName) {
        super(id, initialFuel, consumptionPerTick);
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }

    @Override
    public synchronized String getStatus() {
        // Keep base statuses but include type optionally (UI can call getTypeName() separately)
        return super.getStatus();
    }
}
