package main.java.com.ermolin.urbanTransport;

import main.java.com.ermolin.urbanTransport.services.RouteService;
import main.java.com.ermolin.urbanTransport.beans.TransportType;

import java.util.GregorianCalendar;

public class MainApp {
    public static void main(String[] args){
        RouteService routeService = new RouteService();

        routeService.getScheduleByRegularSchedule(40 ,
                new GregorianCalendar(2020, 03 ,14), TransportType.BUS);

        routeService.exit();
    }
}
