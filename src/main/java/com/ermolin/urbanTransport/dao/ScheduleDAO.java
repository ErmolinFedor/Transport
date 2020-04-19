package main.java.com.ermolin.urbanTransport.dao;


import main.java.com.ermolin.urbanTransport.beans.Route;
import main.java.com.ermolin.urbanTransport.beans.Schedule;
import main.java.com.ermolin.urbanTransport.beans.TransportType;
import main.java.com.ermolin.urbanTransport.beans.Way;
import main.java.com.ermolin.urbanTransport.beans.Weekday;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

public class ScheduleDAO implements  DAO<Schedule>{

    @Override
    public void insert(Schedule schedule) {
        Connection connection = JDBCPostgree.getConnection();
        TransportType t = schedule.getRouteQueue().getFirst().getTransport().getType();
        String sql = "insert into " +
                schedule.getRouteQueue().getFirst().getTransport().getType().sqlScheduleOrder
                + " (number, day, idScheduleTransport, licensePlate)\n" +
                "\tvalues(?, ?, ?, ?)"; // int date int int
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            for (Route route: schedule.getRouteQueue()) {
                preparedStatement.setInt(1, route.getNumber());
                preparedStatement.setDate(2 , new Date(route.getDate().getTime()));
                preparedStatement.setInt(3, route.getId());
                preparedStatement.setString(4 , route.getTransport().getLicensePlate());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void update(Schedule schedule) {
        Connection connection = JDBCPostgree.getConnection();
        String sql = "update " + schedule.getRouteQueue().getFirst().getTransport().getType().sqlScheduleOrder+
                " set number = ?, licensePlate = ?"
                + " where idScheduleTransport = ? and day = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            for (Route route: schedule.getRouteQueue()) {
                preparedStatement.setInt(1, route.getNumber());
                preparedStatement.setString(2, route.getTransport().getLicensePlate());
                preparedStatement.setInt(3, route.getId());
                preparedStatement.setDate(4 , new Date(route.getDate().getTime()));
            }
            preparedStatement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Schedule schedule) {
        Connection connection = JDBCPostgree.getConnection();
        String sql = "delete from " + schedule.getRouteQueue().getFirst().getTransport().getType().sqlScheduleOrder
                + " where idScheduleTransport = ? and day = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            for (Route route: schedule.getRouteQueue()) {
                preparedStatement.setInt(1, route.getId());
                preparedStatement.setDate(2 , new Date(route.getDate().getTime()));
            }
            preparedStatement.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void setWay(Route route, String way){
        switch (way){
            case "direct" :
                route.setDirect(Way.direct);
                break;
            case "back" :
                route.setDirect(Way.back);
                break;
            case "ring" :
                route.setDirect(Way.ring);
                break;
        }
    }

    public Schedule getScheduleRegular(int number, Weekday weekday, TransportType type){
        Schedule schedule = new Schedule();
        Connection connection = JDBCPostgree.getConnection();
        String sql = "select * from " + type.sqlSchedule
                + " where number = ? and dayOfWeek = ?::weekday order by departure";
        try(PreparedStatement preparedStatement =connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, number);
            preparedStatement.setString(2, weekday.name());

            try(ResultSet resultSet = preparedStatement.executeQuery()){

            while (resultSet.next()){
                Route route = new Route();
                String way = resultSet.getString("direct");
                route.setId(resultSet.getInt("id"));
                route.setDeparture(resultSet.getTime("departure"));
                route.setArriving(resultSet.getTime("arriving"));
                route.setDayOfWeek(weekday);
                route.setNumber(number);
                setWay(route, way);
                schedule.add(route);
            }
        }}catch (SQLException e){
            e.printStackTrace();
        }

        return schedule;
    }

}
