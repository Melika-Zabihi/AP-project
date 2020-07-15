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

public class SignUpController {

    @FXML
    private TextField userName ,password ,email ,question ,answer;
    @FXML
    private Label userErrorLabel, emailErrorLabel, inputErrorLabel, passwordErrorLabel;
    private double xOffset;
    private double yOffset;

    public void onLoginClick(Event event) throws IOException{

        Parent root = FXMLLoader.load(getClass().getResource("LoginFxml.fxml"));
        Scene scene = new Scene(root,700,500);
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

    public void WindowButton(Event event) {
        Platform.exit();
    }


    public void clientActivator(Event event) throws IOException{

        PlayerInfo player = new PlayerInfo(getUserName(),getPassword(),getEmail()
                                ,getQuestion(),getAnswer());

        Main.client.sendSignUpInfoToServer(player);

        //check mikone kodum eror ha rokh dade va label marbut be un ro namayesh mide
        if(Main.client.hasInputError())
            inputErrorLabel.setVisible(true);
        else
            inputErrorLabel.setVisible(false);

        if(Main.client.hasUserNameError())
            userErrorLabel.setVisible(true);
        else
            userErrorLabel.setVisible(false);

        if(Main.client.hasEmailError())
            emailErrorLabel.setVisible(true);
        else
            emailErrorLabel.setVisible(false);

        if(Main.client.hasPasswordError())
            passwordErrorLabel.setVisible(true);
        else
            passwordErrorLabel.setVisible(false);

        //age etelaat hich moshkeli nadash vared bazi mishe
        if(Main.client.canEnter()){
            Main.client.setPlayer(player);
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
    }

    public  String getUserName() {
        return userName.getText();
    }

    public String getPassword() {
        return password.getText();
    }

    public String getQuestion() {
        return question.getText();
    }

    public String getAnswer() {
        return answer.getText();
    }

    public String getEmail() {
        return email.getText();
    }

}
