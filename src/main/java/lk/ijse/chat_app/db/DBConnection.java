package lk.ijse.chat_app.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

    private final static String URL = "jdbc:mysql://localhost:3306/chat_app";

    private final static Properties pros = new Properties();

    static{

        pros.setProperty("user","root");
        pros.setProperty("password","Kvkaviya@105");

    }

    private static DBConnection dbConnection;
    private Connection connection;

    private DBConnection() throws SQLException {
        connection = DriverManager.getConnection(URL,pros);
    }

    public static DBConnection getInstance() throws SQLException {

        if (dbConnection==null){
            return dbConnection = new DBConnection();
        }else {
            return dbConnection;
        }
    }

    public Connection getConnection(){
        return connection;
    }

}
