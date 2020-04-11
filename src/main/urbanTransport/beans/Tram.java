package main.urbanTransport.beans;

public class Tram extends Transport {
    public Tram(String licensePlate, int number, int idModel, String model, int mileage, int capacity, boolean available) {
        super(licensePlate, number, idModel, model, mileage, capacity, available);
    }

    @Override
    public String toString() {
        return "TRAM{" +
                super.toString();

    }

}