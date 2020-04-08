package urbanTransport.beans;

public enum TransportType {
    Tram("trams" , "tramModels" , "scheduleTram" , "scheduleTramOrder" , "idScheduleTram"),
    Bus("buses" , "busModels", "scheduleBus", "scheduleBusOrder" , "idScheduleBus"),
    Trolleybus("trolleybuses" , "trolleybusModels", "scheduleTrolleybus" , "scheduleTrolleybusOrder" , "idScheduleBus");
    
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
