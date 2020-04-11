package main.urbanTransport.services;

import main.urbanTransport.beans.Route;
import main.urbanTransport.beans.Schedule;
import main.urbanTransport.beans.Transport;
import main.urbanTransport.beans.TransportType;
import main.urbanTransport.beans.Weekday;
import main.urbanTransport.dao.JDBCPostgree;
import main.urbanTransport.dao.ScheduleDAO;
import main.urbanTransport.dao.TransportDAO;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class RouteService {

    public void getScheduleByRegularSchedule(int number, Calendar calendar, TransportType type) {
        LinkedList<Transport> transportsAtDepot = getFreeTransport(number, type);
        if (transportsAtDepot.isEmpty()) return;

        ScheduleDAO scheduleDAO = new ScheduleDAO();
        Schedule schedule = scheduleDAO.getScheduleRegular(number, getWeekday(calendar), type);
        LinkedList<Route> regularQueue = schedule.getRouteQueue();
        if (regularQueue.isEmpty()) return;
        LinkedList<Route> resultRoutes = generateQueueRoute(transportsAtDepot, regularQueue, calendar);

        printQueue(resultRoutes);
        schedule.setRouteQueue(resultRoutes);
        //if (schedule.getRouteQueue().size() > 0) scheduleDAO.insert(schedule);

    }

    private LinkedList<Transport> getFreeTransport(int number, TransportType type) {
        return new TransportDAO().getFreeAvailableTransport(number, type);
    }

    public LinkedList<Route> generateQueueRoute(LinkedList<Transport> transportsAtDepot,
                                                LinkedList<Route> regularQueue,
                                                Calendar calendar) {

        LinkedList<Route> result = new LinkedList<>();
        LinkedList<Transport> transportsAtDE = new LinkedList<>();// transport are ready to start at the ded end
        LinkedList<Route> directQ = new LinkedList<>();
        LinkedList<Route> backQ = new LinkedList<>();

        while (regularQueue.size() > 0) {
            Route route = regularQueue.pollFirst();
            route.setDate(calendar.getTime());

            switch (route.getDirect()) {
                case direct:
                    directRoute(backQ, transportsAtDepot, route, directQ, result);
                    break;
                case back:
                    backRoute(backQ, transportsAtDE, route, directQ, result);
                    break;
                case ring:
                    ringRoute(directQ, transportsAtDepot, route, result);
                    break;
            }
        }
        result.addAll(directQ);
        result.addAll(backQ);
        result.sort((o1, o2) -> (int) (o1.getDeparture().getTime() - o2.getDeparture().getTime()));
        return result;
    }

    public void printQueue(LinkedList<Route> routes) {
        System.out.println("Final schedule: ");
        routes.stream().forEach(System.out::println);
    }

    public Weekday getWeekday(Calendar calendar) {
        return calendar.get(Calendar.DAY_OF_WEEK) > 1 &&
                calendar.get(Calendar.DAY_OF_WEEK) < 7 ? Weekday.workings : Weekday.weekends;
    }

    private void directRoute(LinkedList<Route> backQ, LinkedList<Transport> transportsAtDepot,
                             Route route, LinkedList<Route> directQ,
                             LinkedList<Route> res) {
        if (backQ != null && !backQ.isEmpty()) {
            while (backQ.peekLast().getArriving().before(route.getDeparture())) {
                Route tmp = backQ.pollLast();
                transportsAtDepot.addFirst(tmp.getTransport());
                res.add(tmp);
                if (backQ.isEmpty()) break;
            }
        }

        route.setTransport(transportsAtDepot.pollFirst());
        directQ.addFirst(route);
    }

    private void backRoute(LinkedList<Route> backQ, LinkedList<Transport> tranportsAtDE,
                           Route route, LinkedList<Route> directQ,
                           LinkedList<Route> res) {
        if (directQ != null && !directQ.isEmpty()) {
            while (directQ.peekLast().getArriving().before(route.getDeparture())) {
                Route tmp = directQ.pollLast();
                tranportsAtDE.addFirst(tmp.getTransport());
                res.add(tmp);
                if (directQ.isEmpty()) break;
            }
        }

        route.setTransport(tranportsAtDE.pollLast());
        backQ.add(route);
    }

    private void ringRoute(LinkedList<Route> directQ, LinkedList<Transport> transportsAtDepot, Route route, LinkedList<Route> res) {
        if (directQ != null && !directQ.isEmpty()) {
            while (directQ.peekLast().getArriving().before(route.getDeparture())) {
                Route tmp = directQ.pollLast();
                transportsAtDepot.addFirst(tmp.getTransport());
                res.add(tmp);
                if (directQ.isEmpty()) break;
            }
        }

        route.setTransport(transportsAtDepot.pollFirst());
        directQ.addFirst(route);
    }

    public void exit() {
        JDBCPostgree.closeConnection();
    }

    // old code method

    public void generateScheduleTransportOrderByRegular(int number, Calendar day, TransportType type) {
        LinkedList<Transport>
                transportsAtDepot = new TransportDAO().getFreeAvailableTransport(number, type);// transport are ready to start at the depot
        LinkedList<Transport>
                transportsAtDE = new LinkedList<>();// transport are ready to start at the ded end

        LinkedList<Route> directQ = new LinkedList<>();
        LinkedList<Route> backQ = new LinkedList<>();

        LinkedList<Route> res = new LinkedList<>();

        if (transportsAtDepot.isEmpty()) return;

        ScheduleDAO scheduleDAO = new ScheduleDAO();
        Schedule schedule = scheduleDAO.getScheduleRegular(number, getWeekday(day), type);

        while (schedule.getRouteQueue().size() > 0) {
            Route route = schedule.getRouteQueue().pollFirst();
            route.setDate(day.getTime());
            switch (route.getDirect()) {
                case direct:
                    directRoute(backQ, transportsAtDepot, route, directQ, res);
                    break;
                case back:
                    backRoute(backQ, transportsAtDE, route, directQ, res);
                    break;
                case ring:
                    ringRoute(directQ, transportsAtDepot, route, res);
                    break;
            }//switch

        }

        res.addAll(directQ);
        res.addAll(backQ);
        /*for (Route route: directQ) { //return all not ended route to the schedule
            transportsAtDepot.addFirst(route.getTransport());
            res.add(route);
        }

        for (Route route: backQ) {  //return all not ended route to the schedule
            transportsAtDE.addFirst(route.getTransport());
            res.add(route);
        }*/

        System.out.println("Final schedule: ");
        res.stream().sorted((o1, o2) -> (int) (o1.getDeparture().getTime() - o2.getDeparture().getTime())
        ).collect(Collectors.toList()).forEach(System.out::println);
        // for (Route route: res
        //      ) {
        //     System.out.println(route);
        // }

        schedule.setRouteQueue(res);//schedule with transports
        //scheduleDAO.insert(schedule);//write to DB real schedule

    }


    public void generateScheduleTransportOrderByRegularForTomorrow(int number, TransportType type) {

        Calendar calendar = new GregorianCalendar();
        calendar.roll(Calendar.DATE, 1);//tomorrow

        generateScheduleTransportOrderByRegular(number, calendar, type);

    }


    public LinkedList<Route> getNotNullList() {
        LinkedList<Route> res = new LinkedList<>();
        Route route = new Route();
        route.setNumber(1);
        res.add(route);
        return res;
    }
}
