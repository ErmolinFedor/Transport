package test.urbanTransport.services;

import main.urbanTransport.beans.Weekday;
import org.testng.annotations.DataProvider;

import java.util.GregorianCalendar;

public class GetWeekdayData {
    @DataProvider(name = "getWeekdayData")
    public static Object[][] generateQueueRouteData(){

        return new Object[][]{
                {new GregorianCalendar(2020, 3 , 6), Weekday.workings},
                {new GregorianCalendar(2020, 3 , 7), Weekday.workings},
                {new GregorianCalendar(2020, 3 , 8), Weekday.workings},
                {new GregorianCalendar(2020, 3 , 9), Weekday.workings},
                {new GregorianCalendar(2020, 3 , 10), Weekday.workings},
                {new GregorianCalendar(2020, 3 , 11), Weekday.weekends},
                {new GregorianCalendar(2020, 3 , 12), Weekday.weekends},
        };
    }
}
