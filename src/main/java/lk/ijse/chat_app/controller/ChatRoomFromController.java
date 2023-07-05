package lk.ijse.chat_app.controller;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;

public class ChatRoomFromController {

    @FXML
    private TextField txtMassage;
    @FXML
    private Button btnSend;
    @FXML
    private VBox vbox;
    @FXML
    private ScrollPane chatAreaPane;
    @FXML
    private AnchorPane mainPane;
    @FXML
    private ImageView imgEmojiOption;
    @FXML
    private AnchorPane emojiPane;
    @FXML
    private ImageView imgMinimize;
    @FXML
    private ImageView theme1;
    @FXML
    private ImageView theme2;
    @FXML
    private ImageView theme3;
    @FXML
    private ImageView theme4;
    @FXML
    private ImageView theme5;
    @FXML
    private ImageView theme6;
    @FXML
    private ImageView theme7;
    @FXML
    private ImageView theme8;
    @FXML
    private AnchorPane themePane;

    Socket socket;
    String massage="";
    String stageName;
    String name="######################";
    FileChooser fileChooser;
    File selectedFile;
    String imgPath="";
    boolean imageSelected=false;
    AnchorPane pane;
    BufferedImage sendImage;
    DataOutputStream dataOutputStream;
    DataInputStream dataInputStream;
    boolean isClientClose = false;
    int count = 0;
    boolean isThemeChanged = false;


    public void sendMassageToChatRoom(ActionEvent event) {

        Stage stage = new Stage();

        stage = (Stage) btnSend.getScene().getWindow();
        stageName = stage.getTitle();

        int endIndex = stageName.indexOf("'s Chat Room");
        name = stageName.substring(0, endIndex);


        try {

            if (imageSelected) {

                dataOutputStream.writeUTF(name + " : " + imgPath.trim());
                dataOutputStream.flush();
                imageSelected = false;


            } else if (isClientClose){

                dataOutputStream.writeUTF("finish");
                dataOutputStream.flush();
                imageSelected = false;



            }else {


                String reply = name + " : " + txtMassage.getText();


                dataOutputStream.writeUTF(reply);
                dataOutputStream.flush();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        txtMassage.clear();

    }

    public void initialize(){


        String style = ("-fx-background-image: url(lk/ijse/chat_app/img/wall2.png);"
                + "-fx-background-size: cover;");

        vbox.setStyle(style);

        mainPane.setPrefWidth(516);
        mainPane.setPrefHeight(629);



        Platform.setImplicitExit(false);
        chatAreaPane.vvalueProperty().bind(vbox.heightProperty());
        chatAreaPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        chatAreaPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        new Thread(()->{
            try {

                socket = new Socket("localhost",3005);

                dataOutputStream = new DataOutputStream(socket.getOutputStream());
                dataInputStream = new DataInputStream(socket.getInputStream());



                while (!massage.equalsIgnoreCase("finish")) {

                    pane = new AnchorPane();
                    HBox hBox = new HBox();
                    HBox mgBox = new HBox();
                    mgBox.setSpacing(10);

                    massage = dataInputStream.readUTF();
                    String[] parts = massage.split(":");

                    pane.setPadding(new Insets(10, 10, 10, 10));
                    Platform.runLater(new Runnable() {


                        @Override
                        public void run() {

                            hBox.setPrefWidth(455);
                            Label label;
                            ImageView imageView = new ImageView();

                            if (massage.startsWith(name)) {

                                hBox.setAlignment(Pos.CENTER_RIGHT);

                                if (parts[1].contains("icons8-")) {

                                    try {

                                        sendImage = ImageIO.read(new File(parts[1].trim()));

                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }


                                    label = new Label("You : ");

                                  pane.getStyleClass().add("pane-style1");
                                    pane.setPadding(new Insets(10, 10, 10, 10));

                                    addFullMassage(pane,sendImage,imageView,label,48,48,mgBox,hBox,vbox);


                                } else if (parts[1].trim().startsWith("/")) {


                                    try {

                                        sendImage = ImageIO.read(new File(parts[1].trim()));

                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                    label = new Label("You : ");

                                    pane.getStyleClass().add("pane-style1");
                                    pane.setPadding(new Insets(10, 10, 10, 10));

                                    addFullMassage(pane,sendImage,imageView,label,150,150,mgBox,hBox,vbox);


                                } else {

                                    label = new Label("You : " + parts[1].trim());
                                    chatAreaPane.setFitToWidth(true);

                                   pane.getStyleClass().add("pane-style2");
                                    pane.setPadding(new Insets(10, 10, 10, 10));

                                    addLabel(pane,vbox,hBox,label);

                                }
                            } else if (parts[1].trim().contains("icons8-")) {

                                try {

                                    sendImage = ImageIO.read(new File(parts[1].trim()));

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }


                                label = new Label(parts[0] + " : ");

                                pane.getStyleClass().add("pane-style3");
                                pane.setPadding(new Insets(10, 10, 10, 10));

                                addFullMassage(pane,sendImage,imageView,label,48,48,mgBox,hBox,vbox);

                            } else if (parts[1].trim().startsWith("/")) {


                                try {

                                    sendImage = ImageIO.read(new File(parts[1].trim()));

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                label = new Label(parts[0].trim() + " : ");

                                pane.getStyleClass().add("pane-style3");
                                pane.setPadding(new Insets(10, 10, 10, 10));

                                addFullMassage(pane,sendImage,imageView,label,150,150,mgBox,hBox,vbox);

                            } else {

                                label = new Label(massage);

                                pane.getStyleClass().add("pane-style4");
                                pane.setPadding(new Insets(10, 10, 10, 10));

                                addLabel(pane,vbox,hBox,label);

                            }
                        }
                    });

                };


            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();

    }
    public void addImageToChat(ActionEvent event) {

        fileChooser = new FileChooser();

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));

        selectedFile = fileChooser.showOpenDialog(null);


        if (selectedFile != null) {

            imgPath =selectedFile.getPath();
            imageSelected=true;

        }


        sendMassageToChatRoom(event);

    }

    public void addEmojiToTheChat(MouseEvent mouseEvent) {

        imgEmojiOption.setDisable(true);

       chatAreaPane.setStyle(
               "-fx-blend-mode: MULTIPLY;"
       );
        emojiPane.setDisable(false);

        doTransaction(emojiPane,0,1,-334);

        emojiPane.setVisible(true);

    }

    public void sendEmoji(MouseEvent mouseEvent) {

        imageSelected=true;
        ImageView clickedImageView = (ImageView) mouseEvent.getSource();

        String fullPath = clickedImageView.getImage().getUrl();

        String desiredPath = fullPath.substring(fullPath.indexOf("/lk/"));
        imgPath = "/media/kaviyakv/S Directory1/Chat_App/src/main/resources"+desiredPath;

        sendMassageToChatRoom(new ActionEvent());

    }

    public void closeEmojiPanel(MouseEvent mouseEvent) {

      //  chatAreaPane.setPrefHeight(616);
        emojiPane.setDisable(false);

        doTransaction(emojiPane,2,0,+334);

        emojiPane.setVisible(false);
        imgEmojiOption.setDisable(false);

    }

    public void closeTheChat(MouseEvent mouseEvent) {



        isClientClose=true;
        sendMassageToChatRoom(new ActionEvent());
        try {
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Remote Socket Closed  :  "+socket);
        System.exit(0);

    }

    public void minimizeTheChat(MouseEvent mouseEvent) throws IOException {

        Stage stage = (Stage)imgMinimize.getScene().getWindow();
        // is stage minimizable into task bar. (true | false)
        stage.setIconified(true);

    }

    public void selectThemeOnAction(MouseEvent mouseEvent) {


        doTransaction(themePane,0,1,400);

        themePane.setVisible(true);
        
    }

    public void changeTheme(MouseEvent mouseEvent) {

        ImageView clickedImageView = (ImageView) mouseEvent.getSource();

        String fullPath = clickedImageView.getImage().getUrl();

        String desiredPath = fullPath.substring(fullPath.indexOf("/lk/"));

        String style = ("-fx-background-image: url('"+desiredPath+"');"
                + "-fx-background-size: cover;");

        vbox.setStyle(style);

        isThemeChanged =true;

        doTransaction(themePane,1,0,-400);

        themePane.setVisible(false);

    }

    public void changeMode(MouseEvent mouseEvent) {


        if (isThemeChanged){

            if (count == 0) {


                mainPane.setStyle("-fx-background-color:  #485460;");
                txtMassage.setStyle("-fx-background-color: #34495e;");
                txtMassage.setStyle("-fx-text-fill: white;");
                chatAreaPane.setStyle("-fx-background-color: #485460; ");
                count = 1;

            } else {

                mainPane.setStyle("-fx-background-color:  #dfe6e9;");
                txtMassage.setStyle("-fx-background-color:  #ecf0f1;");
                chatAreaPane.setStyle("-fx-background-color: #dfe6e9; ");

                count = 0;

            }

        }else {


            if (count == 0) {

                String style = ("-fx-background-image: url(lk/ijse/chat_app/img/2376052.jpg);"
                        + "-fx-background-size: cover;");

                vbox.setStyle(style);

                mainPane.setStyle("-fx-background-color:  #485460;");
                txtMassage.setStyle("-fx-background-color: #34495e;");
                chatAreaPane.setStyle("-fx-background-color: #485460 ");
                count = 1;

            } else {


                String style = ("-fx-background-image: url(lk/ijse/chat_app/img/wall2.png);"
                        + "-fx-background-size: cover;");

                vbox.setStyle(style);

                mainPane.setStyle("-fx-background-color:  #dfe6e9;");
                txtMassage.setStyle("-fx-background-color:  #ecf0f17");
                chatAreaPane.setStyle("-fx-background-color: #dfe6e9 ");

                count = 0;

            }
        }
    }

    public void doTransaction(AnchorPane pane,int from, int to, int yValue){

        FadeTransition fadeTransition1 = new FadeTransition(Duration.seconds(0.5), pane);
        fadeTransition1.setFromValue(from);
        fadeTransition1.setToValue(to);
        fadeTransition1.play();

        TranslateTransition translateTransition1 = new TranslateTransition(Duration.seconds(0.5), pane);
        translateTransition1.setByY(yValue);
        translateTransition1.play();

    }

    public void addLabel(AnchorPane pane, VBox vbox, HBox hBox, Label label){

        pane.getChildren().add(label);
        hBox.getChildren().add(pane);
        vbox.getChildren().add(hBox);

    }

    public void addFullMassage(AnchorPane pane,BufferedImage sendImage, ImageView imageView,Label label, int hight, int width, HBox mgBox, HBox hBox, VBox vbox){

        Image img = SwingFXUtils.toFXImage(sendImage, null);
        imageView = new ImageView(img);
        imageView.setFitHeight(hight);
        imageView.setFitWidth(width);

        mgBox.getChildren().addAll(label, imageView);
        pane.getChildren().add(mgBox);
        hBox.getChildren().add(pane);
        vbox.getChildren().add(hBox);

    }
}
