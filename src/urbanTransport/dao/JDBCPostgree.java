package urbanTransport.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCPostgree {
    private static Connection connection = null;

    static {
        //System.out.println("Testing connection to PostgreSQL JDBC");
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver is not found. Include it in your library path ");
            e.printStackTrace();
        }
        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream("src/resources/properties.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String DB_URL = properties.getProperty("URL");
        String USER = properties.getProperty("USER");
        String PASS = properties.getProperty("PASS");
        try {
            connection = DriverManager
                    .getConnection(DB_URL, USER, PASS);
          //  System.out.println("PostgreSQL JDBC Driver successfully connected");
        } catch (SQLException e) {
            e.printStackTrace();//подумать что делать если не те данные для подключеиния
        }
    }

    public JDBCPostgree() {}

    public static Connection getConnection() {
        return connection;
    }

    public static void closeConnection(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
