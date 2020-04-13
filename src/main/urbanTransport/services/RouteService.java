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
        if (transportsAtDepot.isEmpty()){
            printError("no corresponding transport");
            return;
        }

        ScheduleDAO scheduleDAO = new ScheduleDAO();
        Schedule schedule = scheduleDAO.getScheduleRegular(number, getWeekday(calendar), type);
        LinkedList<Route> regularQueue = schedule.getRouteQueue();
        if (regularQueue.isEmpty()){
            printError("no corresponding schedule");
            return;
        }
        LinkedList<Route> resultRoutes = generateQueueRoute(transportsAtDepot, regularQueue, calendar);

        printQueue(resultRoutes);
        schedule.setRouteQueue(resultRoutes);
        //if (schedule.getRouteQueue().size() > 0) scheduleDAO.insert(schedule);

    }

    public void getScheduleByRegularScheduleForTomorrow(int number, TransportType type){
        Calendar calendar = new GregorianCalendar();
        calendar.roll(Calendar.DAY_OF_MONTH, true);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        getScheduleByRegularSchedule(number, calendar, type);
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


        regularQueue.sort((o1, o2) -> (int) (o1.getDeparture().getTime() - o2.getDeparture().getTime()));

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

        for (Route route: directQ) { //return all not ended route to the schedule
            transportsAtDepot.addFirst(route.getTransport());
            result.add(route);
        }

        for (Route route: backQ) {  //return all not ended route to the schedule
            transportsAtDepot.addFirst(route.getTransport());
            result.add(route);
        }


        /*result.addAll(directQ);
        result.addAll(backQ);*/
        result.sort((o1, o2) -> (int) (o1.getDeparture().getTime() - o2.getDeparture().getTime()));
        return result;
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

    private void printQueue(LinkedList<Route> routes) {
        System.out.println("Final schedule: ");
        routes.stream().forEach(System.out::println);
    }

    private void printError(String msg){
        System.err.println("The operation cannot be performed for the following reason: " + msg);
    }

    public void exit() {
        JDBCPostgree.closeConnection();
    }
}
