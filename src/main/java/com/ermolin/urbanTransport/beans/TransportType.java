package main.java.com.ermolin.urbanTransport.beans;

public enum TransportType {
    TRAM("trams" , "tramModels" , "scheduleTram" , "scheduleTramOrder" , "idScheduleTram"),
    BUS("buses" , "busModels", "scheduleBus", "scheduleBusOrder" , "idScheduleBus"),
    TROLLEYBUS("trolleybuses" , "trolleybusModels", "scheduleTrolleybus" , "scheduleTrolleybusOrder" , "idScheduleBus");
    
    public String sqlMain;
    public String sqlModel;
    public String sqlSchedule;
    public String sqlScheduleOrder;
    public String sqlIdScheduleTransport;


    TransportType(String sqlMain, String sqlModel, String sqlSchedule , String sqlScheduleOrder, String sqlIdScheduleTransport) {
        this.sqlMain = sqlMain;
        this.sqlModel = sqlModel;
        this.sqlSchedule = sqlSchedule;
        this.sqlScheduleOrder = sqlScheduleOrder;
        this.sqlIdScheduleTransport = sqlIdScheduleTransport;
    }
}
