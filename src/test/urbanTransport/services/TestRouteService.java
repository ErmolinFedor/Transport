package test.urbanTransport.services;

import main.urbanTransport.beans.Bus;
import main.urbanTransport.beans.Route;
import main.urbanTransport.beans.Transport;
import main.urbanTransport.beans.Way;
import main.urbanTransport.beans.Weekday;
import main.urbanTransport.services.RouteService;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class TestRouteService extends Assert{

    private RouteService routeService = null;
    private final Map<Calendar, Weekday> weekDays = new HashMap<>();
    private final LinkedList<Transport> transportsAtDepot = new LinkedList<>();
    LinkedList<Route> regularQueue = new LinkedList<>();
    LinkedList<Route> expectedQueue = new LinkedList<>();
    Calendar calendar = new GregorianCalendar(2020, 03 ,14);

    @BeforeClass
    public void setUp(){
        routeService = new RouteService();
/*
        weekDays.put(new GregorianCalendar(2020, 3 , 6), Weekday.workings);
        weekDays.put(new GregorianCalendar(2020, 3 , 7), Weekday.workings);
        weekDays.put(new GregorianCalendar(2020, 3 , 8), Weekday.workings);
        weekDays.put(new GregorianCalendar(2020, 3 , 9), Weekday.workings);
        weekDays.put(new GregorianCalendar(2020, 3 , 10), Weekday.workings);
        weekDays.put(new GregorianCalendar(2020, 3 , 11), Weekday.weekends);
        weekDays.put(new GregorianCalendar(2020, 3 , 12), Weekday.weekends);
*/
        Bus bus1 = new Bus("A001AA178RUS", 40, 1,
                "Zil", 1234, 110,440 , true);
        Bus bus2 = new Bus("A002AA178RUS", 40, 1,
                "Zil", 1234, 110,440 , true);
        Bus bus3 = new Bus("A003AA178RUS", 40, 1,
                "Zil", 1234, 110,440 , true);
        Bus bus4 = new Bus("A004AA178RUS", 40, 1,
                "Zil", 1234, 110,440 , true);
        Bus bus5 = new Bus("A005AA178RUS", 40, 1,
                "Zil", 1234, 110,440 , true);
        Bus bus6 = new Bus("A006AA178RUS", 40, 1,
                "Zil", 1234, 110,440 , true);
        Bus bus7 = new Bus("A007AA178RUS", 40, 1,
                "Zil", 1234, 110,440 , true);

        transportsAtDepot.add(bus1);
        transportsAtDepot.add(bus2);
        transportsAtDepot.add(bus3);
        transportsAtDepot.add(bus4);
        transportsAtDepot.add(bus5);
        transportsAtDepot.add(bus6);
        transportsAtDepot.add(bus7);

        Calendar time = new GregorianCalendar(2020, 3, 14, 8, 0, 0);
        for (int i = 1 ; i < 7 ; i++){
            Calendar in = new GregorianCalendar();
            in.setTime(time.getTime());
            Route route = new Route();
            route.setId(i);
            route.setDayOfWeek(Weekday.workings);
            route.setNumber(40);
            route.setDirect(Way.ring);
            route.setDate(calendar.getTime());
            route.setDeparture(time.getTime());
            in.add(12, 35);
            route.setArriving(in.getTime());
            regularQueue.add(route);
            time.add(12, 15);
        }

        Calendar time1 = new GregorianCalendar(2020, 3, 14, 8, 0, 0);
        for (int i = 1 ; i < 7 ; i++){
            Calendar in = new GregorianCalendar();
            in.setTime(time1.getTime());
            Route route = new Route();
            route.setId(i);
            route.setDayOfWeek(Weekday.workings);
            route.setNumber(40);
            route.setDirect(Way.ring);
            route.setDate(calendar.getTime());
            route.setDeparture(time1.getTime());
            in.add(12, 35);
            route.setArriving(in.getTime());
            expectedQueue.add(route);
            time1.add(12, 15);
        }

        expectedQueue.get(0).setTransport(bus1);
        expectedQueue.get(1).setTransport(bus2);
        expectedQueue.get(2).setTransport(bus3);
        expectedQueue.get(3).setTransport(bus1);
        expectedQueue.get(4).setTransport(bus2);
        expectedQueue.get(5).setTransport(bus3);
    }


    /*@Test
    public void testGetWeekday(){
        for (Map.Entry<Calendar , Weekday> entry : weekDays.entrySet()){
            final Weekday actual = routeService.getWeekday(entry.getKey());
            final Weekday expected = entry.getValue();
            assertEquals(actual , expected);
        }
    }*/

    @Test
    public void testGenerateQueueRoute(){
        LinkedList<Route> actual = routeService.generateQueueRoute(transportsAtDepot, regularQueue, calendar);
        assertEquals(actual ,expectedQueue);
    }


    @AfterClass
    public void clear(){
        routeService = null;
        weekDays.clear();
        expectedQueue.clear();
        transportsAtDepot.clear();
        regularQueue.clear();
    }

}
