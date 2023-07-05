package lk.ijse.chat_app.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import lk.ijse.chat_app.dao.SQLUtil;
import lk.ijse.chat_app.dao.custom.UserDAO;
import lk.ijse.chat_app.dao.custom.impl.UserDAOImpl;
import lk.ijse.chat_app.entity.User;
import lk.ijse.chat_app.regEx.RegEx;

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
    boolean isValid = false;

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

        if (!isValid){

            new Alert(Alert.AlertType.ERROR, "Input Valid Details").show();

        }else {


            String name = txtName.getText();
            String contact = txtContact.getText();
            String address = txtAddress.getText();

            User user = new User(name, Integer.parseInt(contact), address);

            userDAO.addNewUser(user);


            stage = (Stage) btnCreateAccount.getScene().getWindow();
            stage.close();

            stage.setScene(new Scene(FXMLLoader.load(this.getClass().getResource("/lk/ijse/chat_app/view/login_form.fxml"))));
            stage.setTitle("Chat Room LogIn");
            stage.show();

        }

    }

    public void createAccount(ActionEvent event) throws SQLException, IOException {

        createNewClientAccount(event);

    }

    public void checkAddress(KeyEvent keyEvent) {

        if (!txtAddress.getText().matches(RegEx.addressRegEx())) {

            isValid = false;
            txtAddress.getStyleClass().remove("grey-text-field");
            txtAddress.getStyleClass().add("red-text-field1");

        }else {

            txtAddress.getStyleClass().remove("red-text-field1");
            txtAddress.getStyleClass().add("grey-text-field");
            isValid = true;

        }

    }

    public void checkName(KeyEvent keyEvent) {

        if (!txtName.getText().matches(RegEx.nameRegEx())) {

            isValid = false;
            txtName.getStyleClass().remove("grey-text-field");
            txtName.getStyleClass().add("red-text-field1");

        }else {

            txtName.getStyleClass().remove("red-text-field1");
            txtName.getStyleClass().add("grey-text-field");
            isValid = true;

        }

    }

    public void checkContact(KeyEvent keyEvent) {

        if (!txtContact.getText().matches(RegEx.contactRegEx())) {

            isValid = false;
            txtContact.getStyleClass().remove("grey-text-field");
            txtContact.getStyleClass().add("red-text-field1");

        }else {

            txtContact.getStyleClass().remove("red-text-field1");
            txtContact.getStyleClass().add("grey-text-field");
            isValid = true;

        }
    }

}
