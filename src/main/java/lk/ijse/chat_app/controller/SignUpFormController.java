package lk.ijse.chat_app.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import lk.ijse.chat_app.dao.SQLUtil;
import lk.ijse.chat_app.dao.custom.UserDAO;
import lk.ijse.chat_app.dao.custom.impl.UserDAOImpl;
import lk.ijse.chat_app.entity.User;

import java.io.IOException;
import java.sql.SQLException;

public class SignUpFormController {


    @FXML
    private Button btnCreateAccount;
    @FXML
    private ImageView imgBack;
    @FXML
    private TextField txtAddress;
    @FXML
    private TextField txtContact;
    @FXML
    private TextField txtName;


    Stage stage = new Stage();
    UserDAO userDAO = new UserDAOImpl();

    @FXML
    void backToTheOpenForm(MouseEvent event) throws IOException {

        stage = (Stage) imgBack.getScene().getWindow();
        stage.close();

        stage.setScene(new Scene(FXMLLoader.load(this.getClass().getResource("/lk/ijse/chat_app/view/open_form.fxml"))));
        stage.setTitle("Chat Room LogIn");
        stage.show();

    }

    @FXML
    void createNewClientAccount(ActionEvent event) throws SQLException, IOException {

        String name = txtName.getText();
        String contact = txtContact.getText();
        String address = txtAddress.getText();

        User user = new User(name,Integer.parseInt(contact),address);

        userDAO.addNewUser(user);


        stage = (Stage) btnCreateAccount.getScene().getWindow();
        stage.close();

        stage.setScene(new Scene(FXMLLoader.load(this.getClass().getResource("/lk/ijse/chat_app/view/login_form.fxml"))));
        stage.setTitle("Chat Room LogIn");
        stage.show();

    }

    public void createAccount(ActionEvent event) throws SQLException, IOException {

        createNewClientAccount(event);

    }
}
