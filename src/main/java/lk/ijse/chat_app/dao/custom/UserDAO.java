package lk.ijse.chat_app.dao.custom;

import lk.ijse.chat_app.entity.User;

import java.sql.SQLException;

public interface UserDAO {

    public boolean userExist(String name) throws SQLException;

    public boolean addNewUser(User user) throws SQLException;

}
