package lk.ijse.chat_app.controller;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import lk.ijse.chat_app.dao.SQLUtil;
import lk.ijse.chat_app.dao.custom.UserDAO;
import lk.ijse.chat_app.dao.custom.impl.UserDAOImpl;
import lk.ijse.chat_app.regEx.RegEx;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;


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
    UserDAO userDAO = new UserDAOImpl();
    boolean isUserNameValid =  false;

    public void executeLogInMethod(ActionEvent event) throws SQLException, IOException {

        logInToChatRoom(event);

    }

    public void logInToChatRoom(ActionEvent event) throws SQLException, IOException {

        String userName = txtUserName.getText();

        if(!isUserNameValid){

            new Alert(Alert.AlertType.ERROR, "Please enter a valid user name").show();

        }else {

            boolean isExist = userDAO.userExist(userName);

            if (!isExist){

                ButtonType SignUp = new ButtonType("SighUp", ButtonBar.ButtonData.YES);
                ButtonType TryAgain = new ButtonType("Try Again", ButtonBar.ButtonData.YES);

                Alert alert = new Alert(Alert.AlertType.ERROR, "This user is not exist! Please use Signup or Try again", SignUp, TryAgain);
                alert.setHeaderText("Please Enter Valid Details");
                // Customize the alert
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStylesheets().add(getClass().getResource("/lk/ijse/chat_app/css/customAlert.css").toExternalForm()); // Apply custom CSS

                ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("/lk/ijse/chat_app/img/icons8-error.gif")));
                imageView.setFitWidth(50);
                imageView.setFitHeight(50);
                alert.setGraphic(imageView);



                Optional<ButtonType> result = alert.showAndWait();

                if (result.orElse(null)==TryAgain) {

                        txtUserName.requestFocus();

                }else{


                    stage= (Stage) btnLogIn.getScene().getWindow();
                    stage.close();

                    stage.setScene(new Scene(FXMLLoader.load(this.getClass().getResource("/lk/ijse/chat_app/view/sign_up_form.fxml"))));
                    stage.show();

                }

            }else {

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

                                stage = (Stage) btnLogIn.getScene().getWindow();
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
                                    scene.getStylesheets().add(getClass().getResource("/lk/ijse/chat_app/css/massageStyle.css").toExternalForm());
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
        }

    }

    public void backBtnOnAction(MouseEvent event) throws IOException {

        stage= (Stage) btnLogIn.getScene().getWindow();
        stage.close();

        stage.setScene(new Scene(FXMLLoader.load(this.getClass().getResource("/lk/ijse/chat_app/view/open_form.fxml"))));
        stage.show();

    }

    public boolean exist(String user_name) throws SQLException {

       return userDAO.userExist(user_name);

    }

    public void checkUserName(KeyEvent keyEvent) {

        if (!txtUserName.getText().matches(RegEx.nameRegEx())) {

            isUserNameValid = false;
            txtUserName.getStyleClass().remove("black-text-field");
            txtUserName.getStyleClass().add("red-text-field");

        }else {

            txtUserName.getStyleClass().remove("red-text-field");
            txtUserName.getStyleClass().add("black-text-field");
            isUserNameValid = true;

        }
    }
}
