package test.java.com.ermolin.urbanTransport.services;

import main.java.com.ermolin.urbanTransport.beans.Weekday;
import main.java.com.ermolin.urbanTransport.services.RouteService;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Calendar;

public class GetWeekdayTest extends Assert {
    RouteService routeService = null;

    @BeforeClass
    public void setUp(){
        routeService = new RouteService();
    }

    @Test(dataProvider = "getWeekdayData", dataProviderClass = GetWeekdayData.class)
    public void testGetWeekday(Calendar calendar , Weekday weekday){
        assertEquals(routeService.getWeekday(calendar), weekday);
    }

    @AfterClass
    public void clean(){
        routeService = null;
    }
}
