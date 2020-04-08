package urbanTransport.beans;

import java.util.Date;

public class Route {
    int id;
    int idOrder;
    int number;
    Date date;
    Way direct;
    Weekday dayOfWeek;
    Date departure ;
    Date arriving;
    //int licensePlate;
    Transport transport;

    public Route(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Way getDirect() {
        return direct;
    }

    public void setDirect(Way direct) {
        this.direct = direct;
    }

    public Weekday getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(Weekday dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Date getDeparture() {
        return departure;
    }

    public void setDeparture(Date departure) {
        this.departure = departure;
    }

    public Date getArriving() {
        return arriving;
    }

    public void setArriving(Date arriving) {
        this.arriving = arriving;
    }

   // public int getLicensePlate() {
       // return licensePlate;
   // }

    //public void setLicensePlate(int licensePlate) {
       // this.licensePlate = licensePlate;
   // }


    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }

    public Transport getTransport() {
        return transport;
    }

    public void setTransport(Transport transport) {
        this.transport = transport;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Route{" +
                "id=" + id +
                ", idOrder=" + idOrder +
                ", number=" + number +
                ", direct=" + direct +
                ", date=" + date +
                ", dayOfWeek=" + dayOfWeek +
                ", departure=" + departure +
                ", arriving=" + arriving +
                ", licensePlate=" + transport.getLicensePlate() +
                '}';
    }
}
