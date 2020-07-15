package sample;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class GameMenuController {

    private double xOffset;
    private double yOffset;
    public void computerBtnClicked(Event event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ComputerGameFxml.fxml"));
        Scene scene = new Scene(root,700,480);
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

    public void playerBtnClicked(Event event) throws IOException{

        Parent root = FXMLLoader.load(getClass().getResource("PlayerHomePageFxml.fxml"));
        Scene scene = new Scene(root,700  ,500);
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


}
