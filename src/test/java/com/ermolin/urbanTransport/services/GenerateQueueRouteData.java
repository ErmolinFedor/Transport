package test.java.com.ermolin.urbanTransport.services;

import main.java.com.ermolin.urbanTransport.beans.Bus;
import main.java.com.ermolin.urbanTransport.beans.Route;
import main.java.com.ermolin.urbanTransport.beans.Tram;
import main.java.com.ermolin.urbanTransport.beans.Transport;
import main.java.com.ermolin.urbanTransport.beans.Trolleybus;
import main.java.com.ermolin.urbanTransport.beans.Way;
import main.java.com.ermolin.urbanTransport.beans.Weekday;
import org.testng.annotations.DataProvider;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;

public class GenerateQueueRouteData {


    @DataProvider(name = "generateQueueRouteData")
    public static Object[][] generateQueueRouteData() {
        LinkedList<Transport> buses = fillBus();
        LinkedList<Transport> trams = fillTram();
        LinkedList<Transport> trolleybuses = fillTrolleybus();

        LinkedList<Route> regularWorksBusRingQueue = factoryRegularRingRoute(Weekday.workings, 40);// working day
        LinkedList<Route> expectedWorksBusRingQueue = factoryExpectedRingRoute(Weekday.workings, 40, buses);

        LinkedList<Route> regularWorksTramRingQueue = factoryRegularRingRoute(Weekday.workings, 9);
        LinkedList<Route> expectedWorksTramRingQueue = factoryExpectedRingRoute(Weekday.workings, 9, trams);

        LinkedList<Route> regularWorksTrolleybusRingQueue = factoryRegularRingRoute(Weekday.workings, 31);
        LinkedList<Route> expectedWorksTrolleybusRingQueue = factoryExpectedRingRoute(Weekday.workings, 31,trolleybuses);

        LinkedList<Route> regularWorksBusTwoWayQueue = factoryRegularTwoWay(Weekday.workings,40);
        LinkedList<Route> expectedWorksBusTwoWayQueue = factoryExpectedTwoWayRoute(Weekday.workings, 40, buses);

        LinkedList<Route> regularWorksTramTwoWayQueue = factoryRegularTwoWay(Weekday.workings, 9);
        LinkedList<Route> expectedWorksTramTwoWayQueue = factoryExpectedTwoWayRoute(Weekday.workings, 9,trams);

        LinkedList<Route> regularWorksTrolleybusTwoWayQueue = factoryRegularTwoWay(Weekday.workings, 31);
        LinkedList<Route> expectedWorksTrolleybusTwoWayQueue = factoryExpectedTwoWayRoute(Weekday.workings, 31, trolleybuses);

        LinkedList<Route> regularWeekendsBusRingQueue = factoryRegularRingRoute(Weekday.weekends, 40);// weekend
        LinkedList<Route> expectedWeekendsBusRingQueue = factoryExpectedRingRoute(Weekday.weekends, 40 , buses);

        LinkedList<Route> regularWeekendsTramRingQueue = factoryRegularRingRoute(Weekday.weekends, 9);
        LinkedList<Route> expectedWeekendsTramRingQueue = factoryExpectedRingRoute(Weekday.weekends,9,trams);

        LinkedList<Route> regularWeekendsTrolleybusRingQueue = factoryRegularRingRoute(Weekday.weekends, 31);
        LinkedList<Route> expectedWeekendsTrolleybusRingQueue = factoryExpectedRingRoute(Weekday.weekends,31,trolleybuses);

        LinkedList<Route> regularWeekendsBusTwoWayQueue = factoryRegularTwoWay(Weekday.weekends,40);
        LinkedList<Route> expectedWeekendsBusTwoWayQueue = factoryExpectedTwoWayRoute(Weekday.weekends, 40 , buses);

        LinkedList<Route> regularWeekendsTramTwoWayQueue = factoryRegularTwoWay(Weekday.weekends,9);
        LinkedList<Route> expectedWeekendsTramTwoWayQueue = factoryExpectedTwoWayRoute(Weekday.weekends,9,trams);

        LinkedList<Route> regularWeekendsTrolleybusTwoWayQueue = factoryRegularTwoWay(Weekday.weekends,31);
        LinkedList<Route> expectedWeekendsTrolleybusTwoWayQueue = factoryExpectedTwoWayRoute(Weekday.weekends,31,trolleybuses);

        Calendar calendarWorking = new GregorianCalendar(2020, 03, 14);
        Calendar calendarWeekend = new GregorianCalendar(2020, 03, 12);

        return new Object[][]{
                {buses,         regularWorksBusRingQueue,             calendarWorking, expectedWorksBusRingQueue},    //ring
                {trams,         regularWorksTramRingQueue,            calendarWorking, expectedWorksTramRingQueue},
                {trolleybuses,  regularWorksTrolleybusRingQueue,      calendarWorking, expectedWorksTrolleybusRingQueue},
                {buses,         regularWeekendsBusRingQueue,          calendarWeekend, expectedWeekendsBusRingQueue},
                {trams,         regularWeekendsTramRingQueue,         calendarWeekend, expectedWeekendsTramRingQueue},
                {trolleybuses,  regularWeekendsTrolleybusRingQueue,   calendarWeekend, expectedWeekendsTrolleybusRingQueue},
                {buses,         regularWorksBusTwoWayQueue,           calendarWorking, expectedWorksBusTwoWayQueue},         //two way
                {trams,         regularWorksTramTwoWayQueue,          calendarWorking, expectedWorksTramTwoWayQueue},
                {trolleybuses,  regularWorksTrolleybusTwoWayQueue,    calendarWorking, expectedWorksTrolleybusTwoWayQueue},
                {buses,         regularWeekendsBusTwoWayQueue,        calendarWeekend, expectedWeekendsBusTwoWayQueue},
                {trams,         regularWeekendsTramTwoWayQueue,       calendarWeekend, expectedWeekendsTramTwoWayQueue},
                {trolleybuses,  regularWeekendsTrolleybusTwoWayQueue, calendarWeekend, expectedWeekendsTrolleybusTwoWayQueue}
        };
    }

    private static LinkedList<Transport> fillBus(){
        LinkedList<Transport> buses = new LinkedList<>();
        Bus bus1 = new Bus("A001AA178RUS", 40, 1,
                "LiAZ-6213", 1234, 153, 440, true);
        Bus bus2 = new Bus("A002AA178RUS", 40, 1,
                "LiAZ-6213", 1234, 153, 440, true);
        Bus bus3 = new Bus("A003AA178RUS", 40, 1,
                "LiAZ-6213", 1234, 153, 440, true);
        Bus bus4 = new Bus("A004AA178RUS", 40, 1,
                "LiAZ-6213", 1234, 153, 440, true);
        Bus bus5 = new Bus("A005AA178RUS", 40, 1,
                "LiAZ-6213", 1234, 153, 440, true);
        Bus bus6 = new Bus("A006AA178RUS", 40, 1,
                "LiAZ-6213", 1234, 153, 440, true);
        Bus bus7 = new Bus("A007AA178RUS", 40, 1,
                "LiAZ-6213", 1234, 153, 440, true);
        Bus bus8 = new Bus("A008AA178RUS", 40, 1,
                "LiAZ-6213", 1234, 153, 440, true);

        buses.add(bus1);
        buses.add(bus2);
        buses.add(bus3);
        buses.add(bus4);
        buses.add(bus5);
        buses.add(bus6);
        buses.add(bus7);
        buses.add(bus8);
        return buses;
    }

    private static LinkedList<Transport> fillTram(){
        LinkedList<Transport> trams = new LinkedList<>();
        Tram tram1 = new Tram("0001", 9, 1, "LMZ-001", 1234, 110, true);
        Tram tram2 = new Tram("0002", 9, 1, "LMZ-001", 1234, 110, true);
        Tram tram3 = new Tram("0003", 9, 1, "LMZ-001", 1234, 110, true);
        Tram tram4 = new Tram("0004", 9, 1, "LMZ-001", 1234, 110, true);
        Tram tram5 = new Tram("0005", 9, 1, "LMZ-001", 1234, 110, true);
        Tram tram6 = new Tram("0006", 9, 1, "LMZ-001", 1234, 110, true);
        Tram tram7 = new Tram("0007", 9, 1, "LMZ-001", 1234, 110, true);
        Tram tram8 = new Tram("0008", 9, 1, "LMZ-001", 1234, 110, true);

        trams.add(tram1);
        trams.add(tram2);
        trams.add(tram3);
        trams.add(tram4);
        trams.add(tram5);
        trams.add(tram6);
        trams.add(tram7);
        trams.add(tram8);
        return trams;
    }

    private static LinkedList<Transport> fillTrolleybus(){
        LinkedList<Transport> trolleybuses = new LinkedList<>();
        Trolleybus trolleybus1 = new Trolleybus("0001",
                31, 1, "Ziu-9", 1100, 110, true);
        Trolleybus trolleybus2 = new Trolleybus("0002",
                31, 1, "Ziu-9", 1100, 110, true);
        Trolleybus trolleybus3 = new Trolleybus("0003",
                31, 1, "Ziu-9", 1100, 110, true);
        Trolleybus trolleybus4 = new Trolleybus("0004",
                31, 1, "Ziu-9", 1100, 110, true);
        Trolleybus trolleybus5 = new Trolleybus("0005",
                31, 1, "Ziu-9", 1100, 110, true);
        Trolleybus trolleybus6 = new Trolleybus("0006",
                31, 1, "Ziu-9", 1100, 110, true);
        Trolleybus trolleybus7 = new Trolleybus("0007",
                31, 1, "Ziu-9", 1100, 110, true);
        Trolleybus trolleybus8 = new Trolleybus("0008",
                31, 1, "Ziu-9", 1100, 110, true);

        trolleybuses.add(trolleybus1);
        trolleybuses.add(trolleybus2);
        trolleybuses.add(trolleybus3);
        trolleybuses.add(trolleybus4);
        trolleybuses.add(trolleybus5);
        trolleybuses.add(trolleybus6);
        trolleybuses.add(trolleybus7);
        trolleybuses.add(trolleybus8);
        return trolleybuses;
    }

    private static LinkedList<Route> factoryRegularRingRoute(Weekday weekday, int number){
        LinkedList<Route> regular = new LinkedList<>();
        Calendar calendar =weekday == Weekday.workings ?
                new GregorianCalendar(2020, 03, 14):
                new GregorianCalendar(2020, 03, 12);
        Calendar time = weekday == Weekday.workings ?
                new GregorianCalendar(2020,3, 14,8,0,0):
                new GregorianCalendar(2020,3, 12,8,0,0);
        for (int i = 1; i < 7; i++) {
            Calendar in = new GregorianCalendar();
            in.setTime(time.getTime());
            Route route = new Route();
            route.setId(i);
            route.setDayOfWeek(weekday);
            route.setNumber(number);
            route.setDirect(Way.ring);
            route.setDate(calendar.getTime());
            route.setDeparture(time.getTime());
            in.add(12, 35);
            route.setArriving(in.getTime());
            regular.add(route);
            time.add(12, 15);
        }
        return regular;
    }

    private static LinkedList<Route> factoryExpectedRingRoute(Weekday weekday, int number, LinkedList<Transport> transports){
        LinkedList<Route> expected = new LinkedList<>();
        Calendar calendar = weekday == Weekday.workings ?
                new GregorianCalendar(2020, 03, 14):
                new GregorianCalendar(2020, 03, 12);
        Calendar time = weekday == Weekday.workings ?
                new GregorianCalendar(2020,3, 14,8,0,0):
                new GregorianCalendar(2020,3, 12,8,0,0);
        for (int i = 1; i < 7; i++) {
            Calendar in = new GregorianCalendar();
            in.setTime(time.getTime());
            Route route = new Route();
            route.setId(i);
            route.setDayOfWeek(weekday);
            route.setNumber(number);
            route.setDirect(Way.ring);
            route.setDate(calendar.getTime());
            route.setDeparture(time.getTime());
            in.add(12, 35);
            route.setArriving(in.getTime());
            expected.add(route);
            time.add(12, 15);
        }

        expected.get(0).setTransport(transports.get(0));
        expected.get(1).setTransport(transports.get(1));
        expected.get(2).setTransport(transports.get(2));
        expected.get(3).setTransport(transports.get(0));
        expected.get(4).setTransport(transports.get(1));
        expected.get(5).setTransport(transports.get(2));
        return expected;
    }

    private static LinkedList<Route> factoryRegularTwoWay(Weekday weekday, int number){
        LinkedList<Route> regular = new LinkedList<>();
        Calendar calendar = weekday == Weekday.workings ?
                new GregorianCalendar(2020, 03, 14):
                new GregorianCalendar(2020, 03, 12);
        Calendar time = weekday == Weekday.workings ?
                new GregorianCalendar(2020,3, 14,8,0,0):
                new GregorianCalendar(2020,3, 12,8,0,0);//direct
        for (int i = 1; i < 5; i++) {
            Calendar in = new GregorianCalendar();
            in.setTime(time.getTime());
            Route route = new Route();
            route.setId(i);
            route.setDayOfWeek(weekday);
            route.setNumber(number);
            route.setDirect(Way.direct);
            route.setDate(calendar.getTime());
            route.setDeparture(time.getTime());
            in.add(12, 35);
            route.setArriving(in.getTime());
            regular.add(route);
            time.add(12, 15);
        }

        time = weekday == Weekday.workings ?
                new GregorianCalendar(2020,3, 14,8,40,0):
                new GregorianCalendar(2020,3, 12,8,40,0);//back
        for (int i = 5; i < 8; i++) {
            Calendar in = new GregorianCalendar();
            in.setTime(time.getTime());
            Route route = new Route();
            route.setId(i);
            route.setDayOfWeek(weekday);
            route.setNumber(number);
            route.setDirect(Way.back);
            route.setDate(calendar.getTime());
            route.setDeparture(time.getTime());
            in.add(12, 35);
            route.setArriving(in.getTime());
            regular.add(route);
            time.add(12, 15);
        }
        return regular;
    }

    private static LinkedList<Route> factoryExpectedTwoWayRoute(Weekday weekday, int number, LinkedList<Transport> transports){
        LinkedList<Route> expected = new LinkedList<>();
        Calendar calendar = weekday == Weekday.workings ?
                new GregorianCalendar(2020, 03, 14):
                new GregorianCalendar(2020, 03, 12);
        Calendar time = weekday == Weekday.workings ?
                new GregorianCalendar(2020,3, 14,8,0,0):
                new GregorianCalendar(2020,3, 12,8,0,0);//direct
        for (int i = 1; i < 5; i++) {
            Calendar in = new GregorianCalendar();
            in.setTime(time.getTime());
            Route route = new Route();
            route.setId(i);
            route.setDayOfWeek(weekday);
            route.setNumber(number);
            route.setDirect(Way.direct);
            route.setDate(calendar.getTime());
            route.setDeparture(time.getTime());
            in.add(12, 35);
            route.setArriving(in.getTime());
            expected.add(route);
            time.add(12, 15);
        }

        time = weekday == Weekday.workings ?
                new GregorianCalendar(2020,3, 14,8,40,0):
                new GregorianCalendar(2020,3, 12,8,40,0);//back
        for (int i = 5; i < 8; i++) {
            Calendar in = new GregorianCalendar();
            in.setTime(time.getTime());
            Route route = new Route();
            route.setId(i);
            route.setDayOfWeek(weekday);
            route.setNumber(number);
            route.setDirect(Way.back);
            route.setDate(calendar.getTime());
            route.setDeparture(time.getTime());
            in.add(12, 35);
            route.setArriving(in.getTime());
            expected.add(route);
            time.add(12, 15);
        }

        expected.get(0).setTransport(transports.get(0));
        expected.get(1).setTransport(transports.get(1));
        expected.get(2).setTransport(transports.get(2));
        expected.get(3).setTransport(transports.get(3));
        expected.get(4).setTransport(transports.get(0));
        expected.get(5).setTransport(transports.get(1));
        expected.get(6).setTransport(transports.get(2));
        expected.sort((o1, o2) -> (int) (o1.getDeparture().getTime() - o2.getDeparture().getTime()));
        return expected;
    }

}
