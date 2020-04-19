package test.java.com.ermolin.urbanTransport.services;

import main.java.com.ermolin.urbanTransport.beans.Route;
import main.java.com.ermolin.urbanTransport.beans.Transport;
import main.java.com.ermolin.urbanTransport.services.RouteService;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Calendar;
import java.util.Comparator;
import java.util.LinkedList;

public class GenerateQueueRouteTest extends Assert {

    RouteService routeService = null;

    @BeforeClass
    public void setUp(){
        routeService = new RouteService();
    }

    @Test(dataProvider = "generateQueueRouteData", dataProviderClass = GenerateQueueRouteData.class)
    public void testGenerateQueueRouteData(LinkedList<Transport> transports , LinkedList<Route> regular,
                                           Calendar calendar, LinkedList<Route> expected){
        transports.sort(Comparator.comparing(Transport::getLicensePlate));
        LinkedList<Route> actual = routeService.generateQueueRoute(transports, regular, calendar);
        assertEquals(actual, expected);
    }

    @AfterClass
    public void clear(){
        routeService = null;
    }
}
