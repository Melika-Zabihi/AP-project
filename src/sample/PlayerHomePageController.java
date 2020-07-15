package sample;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.ArrayList;

public class PlayerHomePageController {

    @FXML
    TextField textField;
    @FXML
    Label wonLabel1, wonLabel2, lostLabel1, lostLabel2, tieLabel1, tieLabel2, nameLabel;
    @FXML
    VBox buttonList;

    private ArrayList<String> contactsList = Main.client.getPlayer().getContacts();
    private double xOffset;
    private double yOffset;
    public static String opponentName;
    private boolean isFirst = true;

    public void onClicked(Event event){

        boolean alreadyAdded = false;

        String userName = textField.getText();

        for(String name : contactsList) {
            if (name.equals(userName))
                alreadyAdded = true;
        }
        //age user ghablan dar contactList nabashe va jozv karbar haye
        //ozv shode bashe va esme khode karbar nabashe be list ezafe mishe
        if(!alreadyAdded && Main.client.sendSearchedPlayerToServer(new PlayerInfo(userName))
                && !Main.client.getPlayer().getUserName().equals(userName)) {
            Main.client.getPlayer().addContacts(userName);
            buttonList.getChildren().add(buttonMaker(userName));
        }
    }

    //etelaat karbar ro namayesh mide
    public void writePlayerInfo(Event event){

        if(isFirst) {
            for (String users : contactsList) {
                buttonList.getChildren().add(buttonMaker(users));
            }
            isFirst = false;
        }
        nameLabel.setText(Main.client.getPlayer().getUserName());
        wonLabel1.setText(Main.client.getPlayer().getVsComputerWins()+"");
        lostLabel1.setText(Main.client.getPlayer().getVsComputerLost()+"");
        tieLabel1.setText(Main.client.getPlayer().getVsComputerTies()+"");

    }

    // dokme haye contakt list ro dorost mikone
    private Button buttonMaker(String name){

        Button btn = new Button(name);
        btn.setPrefSize(170,20);
        btn.setTextFill(Color.WHITE);
        btn.setStyle("-fx-background-color: #085f7f");
        btn.setOnMouseClicked(e ->{

            opponentName = btn.getText();
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("PlayerGameFxml.fxml"));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            Scene scene = new Scene(root,800,500);
            Stage window = (Stage)((Node)e.getSource()).getScene().getWindow();
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

        });

        return btn;
    }

    public void onBackButtonClicked(Event event) throws IOException {
        Main.client.sendComputerGameInfoToServer();
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

    public void WindowButton(Event event) {
        Main.client.sendComputerGameInfoToServer();
        Platform.exit();
    }
}

