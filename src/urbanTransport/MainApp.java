package urbanTransport;

import urbanTransport.beans.TransportType;
import urbanTransport.services.RouteService;

import java.util.GregorianCalendar;

public class MainApp {
    public static void main(String[] args){
        RouteService routeService = new RouteService();
        routeService.generateScheduleTransportOrderByRegular(40 ,
                new GregorianCalendar(2020, 03 ,8), TransportType.Bus);
        //routeService.generateScheduleTramOrderByRegularForTomorrow(9);

        //routeService.generateScheduleTramOrderByRegular(
        //        10 , new GregorianCalendar(2020, 03 ,10));
        routeService.exit();
    }
}
