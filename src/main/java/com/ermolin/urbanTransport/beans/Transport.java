package main.java.com.ermolin.urbanTransport.beans;

import java.util.Objects;

public class Transport {

    private String licensePlate;
    private int number;
    private String model;
    private int idModel;
    private int mileage;
    private int capacity;
    private boolean available;
    TransportType type;

    public Transport(){}

    public Transport(String licensePlate,int number, int idModel, String model, int mileage, int capacity, boolean available) {
        this.licensePlate = licensePlate;
        this.number = number;
        this.idModel = idModel;
        this.model = model;
        this.mileage = mileage;
        this.capacity = capacity;
        this.available = available;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public int getIdModel() {
        return idModel;
    }

    public void setIdModel(int idModel) {
        this.idModel = idModel;
    }

    public TransportType getType() {
        if (this instanceof Tram) type = TransportType.TRAM;
        if (this instanceof Trolleybus) type = TransportType.TROLLEYBUS;
        if (this instanceof Bus) type = TransportType.BUS;
        return type;
    }

    @Override
    public String toString() {
        return  "licensePlate='" + licensePlate + + '\''+
                "model='" + model + '\'' +
                ", mileage=" + mileage +
                ", capacity=" + capacity +
                ", available=" + available +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transport transport = (Transport) o;
        return Objects.equals(licensePlate, transport.licensePlate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(licensePlate);
    }
}
