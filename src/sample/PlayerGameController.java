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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class PlayerGameController {
    @FXML
    Label wonLabel1, wonLabel2, lostLabel1, lostLabel2, tieLabel1, tieLabel2, opponentNameLabel;
    private double xOffset;
    private double yOffset;
    @FXML
    private Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;
    private Button[] btns = new Button[10];
    private boolean isFirstTime = true, yourTurn = true, gameEnded = false;
    private PlayerInfo opponent, yourPlayer = Main.client.getPlayer();
    private int moveCount =0;


    public void writePlayerInfo(Event event){
        if(isFirstTime){
            opponent = getOpponentInfo();
            isFirstTime = false;
        }
        opponentNameLabel.setText(opponent.getUserName());
        wonLabel1.setText(Main.client.getPlayer().getVsComputerWins()+"");
        lostLabel1.setText(Main.client.getPlayer().getVsComputerLost()+"");
        tieLabel1.setText(Main.client.getPlayer().getVsComputerTies()+"");

    }

    public PlayerInfo getOpponentInfo(){

        PlayerInfo player2 = new PlayerInfo(PlayerHomePageController.opponentName);
        player2.setFromPlayerGame(true);
        Main.client.sendSearchedPlayerToServer( player2 );

        return Main.client.getOpponent();
    }

    public void setButtonAction(Event event){

        btns[0] = btn1;
        btns[1] = btn2;
        btns[2] = btn3;
        btns[3] = btn4;
        btns[4] = btn5;
        btns[5] = btn6;
        btns[6] = btn7;
        btns[7] = btn8;
        btns[8] = btn9;

        for(int i = 0; i < 9; i++) {
            int j = i;
            btns[i].setOnMouseClicked(e -> {

                if(btns[j].getText().equals(" ") && !gameEnded && yourTurn) {

                    btns[j].setText("X");
                    yourTurn = false;

                }
            });
        }

    }
    private void boardCheck(){

        for (int i = 0; i <= 6; i = i+3) { //barrasi 3 satr
            if (btns[i].getText().equals(btns[i+1].getText()) && btns[i+1].getText().equals(btns[i+2].getText())){
                gameStatus(btns[i].getText());
                return;
            }
        }
        for (int i = 0; i <= 2; i++) { //barrasi 3 sotun
            if ( btns[i].getText().equals(btns[i+3].getText()) && btns[i+3].getText().equals(btns[i+6].getText()) ){
                gameStatus(btns[i].getText());
                return;
            }
        }
        for(int i=0; i<=2; i=i+2) { //barrasi 2 ghotr
            if (btns[i].getText().equals(btns[4].getText()) && btns[4].getText().equals(btns[8-i].getText())) {
                gameStatus(btns[i].getText());
                return;
            }
        }
    }
    //bord va bakht ro moshkhas mikone
    private void gameStatus(String name)
    {
        if(name.equals("X")) {

            gameEnded = true;
            return;
        }
        if(name.equals("O")){
            gameEnded = true;
            return;
        }

    }
    private void boardUpdate(){
        for(int i=0; i<9; i++){
            if( yourPlayer.getButtonValue(i) == 1)
            {
                btns[i].setText("O");
            }
            if( opponent.getButtonValue(i) == 1)
            {
                btns[i].setText("X");
            }
        }
    }

    public void onBackButtonClicked(Event event) throws IOException {
        isFirstTime = true;
        Main.client.sendComputerGameInfoToServer();
        Parent root = FXMLLoader.load(getClass().getResource("PlayerHomePageFxml.fxml"));
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

    public void windowButton(Event event) {
        Main.client.sendComputerGameInfoToServer();
        Platform.exit();
    }



}
