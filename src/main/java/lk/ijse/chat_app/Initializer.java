package lk.ijse.chat_app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Initializer extends Application {

    @Override
    public void start(Stage stage) throws IOException {

        stage.setScene(new Scene(FXMLLoader.load(this.getClass().getResource("/lk/ijse/chat_app/view/open_form.fxml"))));
        stage.show();

    }

    public static void main(String[] args) {

        launch();

    }

}
