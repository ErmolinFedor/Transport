package main.java.com.ermolin.urbanTransport.beans;

import java.util.LinkedList;

public class Schedule {
    LinkedList<Route> routeQueue;
    boolean ring;
    boolean direct;
    boolean back;

    public Schedule(){
        routeQueue = new LinkedList<>();
    }

    public LinkedList<Route> getRouteQueue() {
        return routeQueue;
    }

    public void setRouteQueue(LinkedList<Route> ringQueue) {
        this.routeQueue = ringQueue;
    }

    public boolean isRing() {
        return ring;
    }

    public void setRing(boolean ring) {
        this.ring = ring;
    }

    public boolean isDirect() {
        return direct;
    }

    public void setDirect(boolean direct) {
        this.direct = direct;
    }

    public boolean isBack() {
        return back;
    }

    public void setBack(boolean back) {
        this.back = back;
    }

    public void add(Route route){
        routeQueue.add(route);
    }

}
