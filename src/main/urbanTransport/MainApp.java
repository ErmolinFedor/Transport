package main.urbanTransport;

import main.urbanTransport.beans.TransportType;
import main.urbanTransport.services.RouteService;

import java.util.GregorianCalendar;

public class MainApp {
    public static void main(String[] args){
        RouteService routeService = new RouteService();

        routeService.getScheduleByRegularSchedule(40 ,
                new GregorianCalendar(2020, 03 ,14), TransportType.BUS);

        routeService.exit();
    }
}
