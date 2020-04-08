package urbanTransport.dao;

import urbanTransport.beans.Tram;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class TramDAO  implements DAO<Tram> {

    @Override
    public void insert(Tram obj) {

    }

    @Override
    public void update(Tram obj) {

    }

    @Override
    public void delete(Tram obj) {

    }

    public LinkedList<Tram> getFreeAvilTram(){
        LinkedList<Tram> trams = new LinkedList<>();
        Connection connection = JDBCPostgree.getConnection();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            String sql = "select * from trams join tramModels on id = idModel \n" +
                    "where available = true order by random()";
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            while ( resultSet.next()) {
              /*  trams.add(new Tram(resultSet.getInt("licensePlate"),
                        resultSet.getString("model") ,
                        resultSet.getInt("mileage"),
                        resultSet.getInt("capacity"), true));*/
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            try {
                resultSet.close();
                preparedStatement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return trams;
    }
}
