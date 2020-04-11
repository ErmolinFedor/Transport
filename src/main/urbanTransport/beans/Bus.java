package main.urbanTransport.beans;

public class Bus extends Transport {
    private int tank;
    public Bus(String licensePlate, int number, int idModel, String model, int mileage, int capacity,
               int tank, boolean available) {
        super(licensePlate, number, idModel, model, mileage, capacity, available);
        this.tank = tank;
    }

    @Override
    public String toString() {
        return "BUS{" + super.toString();
    }
}
