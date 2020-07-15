package sample;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;

public class LoginController {
    @FXML
    Button LoginOkButton, signUpButton, closeBtn;
    @FXML
    TextField userName, password;
    @FXML
    Label wrongInputLabel;

    private double xOffset;
    private double yOffset;

    public void onSignUpClick(Event event) throws IOException
    {

        Parent root = FXMLLoader.load(getClass().getResource("SignUpFxml.fxml"));
        Scene scene = new Scene(root);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                window.setX(event.getScreenX() - xOffset);
                window.setY(event.getScreenY() - yOffset);
            }
        });
        window.setScene(scene);
        window.show();
    }

    public void clientActivator(Event event) throws IOException {
        PlayerInfo player = new PlayerInfo(getUserName(),getPassword());
        Main.client.sendLoginInfoToServer(player);

        if(Main.client.canEnter()){

            Parent root = FXMLLoader.load(getClass().getResource("GameMenuFxml.fxml"));
            Scene scene = new Scene(root,650,450);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            root.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    xOffset = event.getSceneX();
                    yOffset = event.getSceneY();
                }
            });
            root.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    window.setX(event.getScreenX() - xOffset);
                    window.setY(event.getScreenY() - yOffset);
                }
            });
            window.setScene(scene);
            window.show();
        }
        else{
            wrongInputLabel.setVisible(true);
        }
    }

    public  String getUserName() {
        return userName.getText();
    }

    public String getPassword() {
        return password.getText();
    }

    public void WindowButton(Event event) {
        Platform.exit();
    }

}
