package lk.ijse.chat_app.dao.custom.impl;

import lk.ijse.chat_app.dao.SQLUtil;
import lk.ijse.chat_app.dao.custom.UserDAO;
import lk.ijse.chat_app.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAOImpl implements UserDAO {


    @Override
    public boolean userExist(String name) throws SQLException {

        String sql = "SELECT user_id FROM User WHERE user_name = ? ";
        ResultSet resultSet = SQLUtil.execute(sql,name);

        return  resultSet.next();

    }

    @Override
    public boolean addNewUser(User user) throws SQLException {

        String sql = "INSERT INTO User(user_name,user_id,address) VALUES(?,?,?)";
        return SQLUtil.execute(sql,user.getUser_name(),user.getUserI_id(),user.getAddress());

    }
}
