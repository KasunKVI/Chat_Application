package lk.ijse.chat_app.controller;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import lk.ijse.chat_app.dao.SQLUtil;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;


public class LoginFormController {

    @FXML
    private Button btnLogIn;
    @FXML
    private ImageView imgUser;
    @FXML
    private TextField txtUserName;
    @FXML
    private AnchorPane unlockPane;
    @FXML
    private AnchorPane mainPane;

    Stage stage = new Stage();
    private double xOffset = 0;
    private double yOffset = 0;

    public void executeLogInMethod(ActionEvent event) {

        logInToChatRoom(event);

    }

    public void logInToChatRoom(ActionEvent event) {

        String userName = txtUserName.getText();

        try {
            if (exist(userName)) {


                unlockPane.setVisible(true);
                FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2), unlockPane);
                fadeTransition.setFromValue(0);
                fadeTransition.setToValue(1);
                fadeTransition.play();


                fadeTransition.setOnFinished(e -> {



                    Duration delay = Duration.seconds(0.2);
                    KeyFrame keyFrame = new KeyFrame(delay, events -> {

                       // ChatRoomServer server = new ChatRoomServer();
                       // server.startServer();

                        stage= (Stage) btnLogIn.getScene().getWindow();
                        stage.close();

                        Stage stage = new Stage();

                        try {
                            Parent root = FXMLLoader.load(getClass().getResource("/lk/ijse/chat_app/view/chat_room_form.fxml"));
                            Scene scene = new Scene(root);
                            scene.setOnMousePressed(even -> {
                                xOffset = even.getSceneX();
                                yOffset = even.getSceneY();
                            });
                            scene.setOnMouseDragged(even -> {
                                stage.setX(even.getScreenX() - xOffset);
                                stage.setY(even.getScreenY() - yOffset);

                            });
                              stage.setWidth(493);
                              stage.setHeight(928);
                           stage.initStyle(StageStyle.UNDECORATED);
                            stage.setTitle(txtUserName.getText() + "'s Chat Room");
                            stage.setScene(scene);
                            stage.show();
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                        unlockPane.setVisible(false);
                    });

                    Timeline timeline = new Timeline(keyFrame);
                    timeline.play();
                });

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void backBtnOnAction(MouseEvent event) throws IOException {

        stage= (Stage) btnLogIn.getScene().getWindow();
        stage.close();

        stage.setScene(new Scene(FXMLLoader.load(this.getClass().getResource("/lk/ijse/chat_app/view/open_form.fxml"))));
        stage.show();

    }

    public boolean exist(String user_name) throws SQLException {

        String sql = "SELECT user_id FROM User WHERE user_name = ? ";
        ResultSet resultSet = SQLUtil.execute(sql,user_name);

        return  resultSet.next();
    }

}
