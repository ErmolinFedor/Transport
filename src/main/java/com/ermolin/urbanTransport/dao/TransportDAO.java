package main.java.com.ermolin.urbanTransport.dao;

import main.java.com.ermolin.urbanTransport.beans.Transport;
import main.java.com.ermolin.urbanTransport.beans.TransportType;
import main.java.com.ermolin.urbanTransport.beans.Bus;
import main.java.com.ermolin.urbanTransport.beans.Tram;
import main.java.com.ermolin.urbanTransport.beans.Trolleybus;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class TransportDAO implements DAO<Transport> {

    private int findIdModelByString(String model, TransportType transportType) {
        int res = 0;
        Connection connection = JDBCPostgree.getConnection();
        String sql = "select id from " + transportType.sqlModel + " where model = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, model);
            ResultSet resultSet = preparedStatement.executeQuery();
            res = resultSet.getInt("model");
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    @Override
    public void insert(Transport transport) {
        Connection connection = JDBCPostgree.getConnection();
        String sql = "insert into " + transport.getType().sqlMain + " values(\n" +
                "?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, transport.getLicensePlate());
            preparedStatement.setInt(2, transport.getNumber());
            preparedStatement.setInt(3, findIdModelByString(transport.getModel(), transport.getType()));
            preparedStatement.setInt(4, transport.getMileage());
            preparedStatement.setBoolean(5, transport.isAvailable());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void update(Transport transport) {
        Connection connection = JDBCPostgree.getConnection();
        String sql = "update " + transport.getType().sqlMain + " set\n" +
                "number = ?, idModel = ?, mileage = ?, available = ?\n" +
                "where licensePlate = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, transport.getNumber());
            preparedStatement.setInt(2, findIdModelByString(transport.getModel(), transport.getType()));
            preparedStatement.setInt(3, transport.getMileage());
            preparedStatement.setBoolean(4, transport.isAvailable());
            preparedStatement.setString(5, transport.getLicensePlate());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Transport transport) {
        Connection connection = JDBCPostgree.getConnection();
        String sql = "delete from " + transport.getType().sqlMain + " where licensePlate = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, transport.getLicensePlate());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Transport createTransport(ResultSet resultSet, TransportType type) throws SQLException {
        Transport transport = null;
        switch (type) {
            case BUS:
                transport = new Bus(resultSet.getString("licensePlate"),
                        resultSet.getInt("number"),
                        resultSet.getInt("idModel"),
                        resultSet.getString("model"),
                        resultSet.getInt("mileage"),
                        resultSet.getInt("capacity"),
                        resultSet.getInt("tank"),
                        resultSet.getBoolean("available"));
                break;
            case TRAM:
                transport = new Tram(resultSet.getString("licensePlate"),
                        resultSet.getInt("number"),
                        resultSet.getInt("idModel"),
                        resultSet.getString("model"),
                        resultSet.getInt("mileage"),
                        resultSet.getInt("capacity"),
                        resultSet.getBoolean("available"));
                break;
            case TROLLEYBUS:
                transport = new Trolleybus(resultSet.getString("licensePlate"),
                        resultSet.getInt("number"),
                        resultSet.getInt("idModel"),
                        resultSet.getString("model"),
                        resultSet.getInt("mileage"),
                        resultSet.getInt("capacity"),
                        resultSet.getBoolean("available"));
                break;
        }
        return transport;
    }

    public LinkedList<Transport> getFreeAvailableTransport(int number, TransportType type) {
        LinkedList<Transport> transports = new LinkedList<>();
        Connection connection = JDBCPostgree.getConnection();
        String sql = "select * from " + type.sqlMain + " join " + type.sqlModel + " on id = idModel \n" +
                "where number = ? and available = true order by random()";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, number);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    transports.add(createTransport(resultSet, type));
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transports;
    }
}
