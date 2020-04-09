package urbanTransport.services;

import urbanTransport.beans.Route;
import urbanTransport.beans.Schedule;
import urbanTransport.beans.Transport;
import urbanTransport.beans.TransportType;
import urbanTransport.beans.Weekday;
import urbanTransport.dao.JDBCPostgree;
import urbanTransport.dao.ScheduleDAO;
import urbanTransport.dao.TransportDAO;

import java.util.Calendar;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RouteService {

    public void generateScheduleTransportOrderByRegular(int number, Calendar day, TransportType type){
        LinkedList<Transport> transportsAtDepot = new TransportDAO().getFreeAvailableTransport(number, type);// transport are ready to start at the depot
        LinkedList<Transport> transportsAtDE = new LinkedList<>();// transport are ready to start at the ded end

        LinkedList<Route> directQ = new LinkedList<>();
        LinkedList<Route> backQ = new LinkedList<>();

        LinkedList<Route> res = new LinkedList<>();

        if (transportsAtDepot.isEmpty())return;

        ScheduleDAO scheduleDAO = new ScheduleDAO();
        Schedule schedule = scheduleDAO.getScheduleRegular(number, getWeekday(day) , type);

        while (schedule.getRouteQueue().size() > 0){
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
        res.stream().sorted((o1 , o2) -> {
            return  (int)(o1.getDeparture().getTime() - o2.getDeparture().getTime());
        }).collect(Collectors.toList()).forEach(System.out::println);
       // for (Route route: res
       //      ) {
       //     System.out.println(route);
       // }

        schedule.setRouteQueue(res);//schedule with transports
        scheduleDAO.insert(schedule);//write to DB real schedule

    }




    public void generateScheduleTransportOrderByRegularForTomorrow(int number, TransportType type ){

        Calendar calendar = new GregorianCalendar();
        calendar.roll(Calendar.DATE , 1);//tomorrow

       generateScheduleTransportOrderByRegular(number, calendar, type);

    }

    private Weekday getWeekday(Calendar calendar){
        return calendar.get(Calendar.DAY_OF_WEEK) > 1 &&
                calendar.get(Calendar.DAY_OF_WEEK) < 7 ? Weekday.workings : Weekday.weekends;
    }

    private void directRoute(LinkedList<Route> backQ, LinkedList<Transport> transportsAtDepot,
                             Route route, LinkedList<Route> directQ,
                             LinkedList<Route> res){
        if (backQ!=null && !backQ.isEmpty()){
            while ( backQ.peekLast().getArriving().before(route.getDeparture())){
                Route tmp = backQ.pollLast();
                transportsAtDepot.addFirst(tmp.getTransport());
                res.add(tmp);
            }
        }

        route.setTransport(transportsAtDepot.pollFirst());
        directQ.addFirst(route);
    }

    private void backRoute(LinkedList<Route> backQ, LinkedList<Transport> tranportsAtDE,
                             Route route, LinkedList<Route> directQ,
                             LinkedList<Route> res){
        if (directQ!=null && !directQ.isEmpty()){
            while ( directQ.peekLast().getArriving().before(route.getDeparture())){
                Route tmp = directQ.pollLast();
                tranportsAtDE.addFirst(tmp.getTransport());
                res.add(tmp);
            }
        }

        route.setTransport(tranportsAtDE.pollLast());
        backQ.add(route);
    }

    public void ringRoute(LinkedList<Route> directQ, LinkedList<Transport> transportsAtDepot,Route route , LinkedList<Route> res){
        if (directQ!=null && !directQ.isEmpty()){
            while ( directQ.peekLast().getArriving().before(route.getDeparture())){
                Route tmp = directQ.pollLast();
                transportsAtDepot.addFirst(tmp.getTransport());
                res.add(tmp);
            }
        }

        route.setTransport(transportsAtDepot.pollFirst());
        directQ.addFirst(route);
    }
    public void exit(){
        JDBCPostgree.closeConnection();
    }
}
