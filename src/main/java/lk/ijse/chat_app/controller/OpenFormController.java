package lk.ijse.chat_app.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class OpenFormController {

    @FXML
    private Button btnSignIn;
    @FXML
    private Button btnSignUp;

    Stage stage = new Stage();
    public void signInOnAction(ActionEvent event) throws IOException {



        stage = (Stage) btnSignIn.getScene().getWindow();
        stage.close();
        Scene scene = new Scene(FXMLLoader.load(this.getClass().getResource("/lk/ijse/chat_app/view/login_form.fxml")));
        scene.getStylesheets().add(getClass().getResource("/lk/ijse/chat_app/css/fontChanger.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Chat Room LogIn");
        stage.show();

    }

    public void signUpOnAction(ActionEvent event) throws IOException {

        stage = (Stage) btnSignUp.getScene().getWindow();
        stage.close();
        Scene scene = new Scene(FXMLLoader.load(this.getClass().getResource("/lk/ijse/chat_app/view/sign_up_form.fxml")));
        scene.getStylesheets().add(getClass().getResource("/lk/ijse/chat_app/css/fontChanger.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("SignUp Form");
        stage.show();

    }
}
