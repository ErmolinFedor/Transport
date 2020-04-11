package main.urbanTransport.beans;

public class Trolleybus extends Transport {
    public Trolleybus(String licensePlate, int number, int idModel, String model, int mileage, int capacity, boolean available) {
        super(licensePlate, number, idModel, model, mileage, capacity, available);
    }

    @Override
    public String toString() {
        return "TROLLEYBUS{"
                + super.toString();
    }
}
