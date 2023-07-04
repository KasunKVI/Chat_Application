package lk.ijse.chat_app.dao;

import lk.ijse.chat_app.db.DBConnection;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLUtil {

    public static <T>T execute(String sql, Object... args) throws SQLException {

        PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement(sql);

        for (int i=0; i< args.length; i++){
            preparedStatement.setObject((i+1), args[i]);
        }

        if (sql.startsWith("SELECT") || sql.startsWith("select")){

            return (T) preparedStatement.executeQuery();

        }else {

            return (T)(Boolean) (preparedStatement.executeUpdate()>0);
        }
    }

}
