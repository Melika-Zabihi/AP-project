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
import java.util.Random;

public class ComputerGameController {

    @FXML
    private Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;
    private Button[] btns = new Button[10];
    @FXML
    private Label X, O, N, playerNameLabel;
    private double xOffset;
    private double yOffset;
    private boolean gameEnded = false;
    private static int moveCount = 0;

    public void writePlayerInfo(Event event){
        playerNameLabel.setText(Main.client.getPlayer().getUserName());
        X.setText(Main.client.getPlayer().getVsComputerWins() + "");
        O.setText(Main.client.getPlayer().getVsComputerLost() + "");
        N.setText(Main.client.getPlayer().getVsComputerTies() + "");
    }

    public void onCLick(Event evnet){

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

            //age button ghablan entekhab nashode bashe (meghdaresh " " bashe)
            // va bazi ham tamum nashode bashe ba click player meghdaresh be X
            // taghir peida mikone
            btns[i].setOnMouseClicked(e -> {

                if(btns[j].getText().equals(" ") && !gameEnded) {
                    btns[j].setText("X");
                    moveCount++;
                    boardCheck();
                    computerMove();
                }
            });
        }
    }

    // ba zadan dokme Play again bazi reset mishe
    public void resetClicked(Event event){
        for(int i = 0; i < 9; i++) {
            btns[i].setText(" ");
        }
        gameEnded = false;
        moveCount = 0;
    }

    //avalin harkat computer randome
    private  void computerRandomMove()
    {
        boolean wrongPlace = true;
        Random rand = new Random();
        int n = 0;
        moveCount++;
        while( wrongPlace ){
            n = rand.nextInt(8);
            if( btns[n].getText().equals(" ") )
                wrongPlace = false;
        }
        btns[n].setText("O");
        boardCheck();

    }
    private void computerMove(){
        if( gameEnded )
            return;

        if(moveCount == 9){
            gameStatus(" ");
            return;
        }
        //avalin harkat computer randome
        if(moveCount == 1)
            computerRandomMove();

        else {
            moveCount++;
            int check = 0;
            String xo = "O";
            // yedor safhe ro baraye borde khodesh check mikone yebar ham baraye jologiri az bord player
            while (check != 2) {

                if (check == 1)
                    xo = "X";
                for (int i = 0; i <= 6; i = i + 3) { //barrasi 3 satr
                    if (btns[i].getText().equals(btns[i + 1].getText()) && btns[i].getText().equals(xo)
                            && btns[i + 2].getText().equals(" ")) {
                        btns[i + 2].setText("O");
                        boardCheck();
                        return;
                    } else if (btns[i + 1].getText().equals(btns[i + 2].getText()) && btns[i + 1].getText().equals(xo)
                            && btns[i].getText().equals(" ")) {
                        btns[i].setText("O");
                        boardCheck();
                        return;
                    } else if (btns[i].getText().equals(btns[i + 2].getText()) && btns[i].getText().equals(xo)
                            && btns[i + 1].getText().equals(" ")) {
                        btns[i + 1].setText("O");
                        boardCheck();
                        return;
                    }
                }
                for (int i = 0; i <= 2; i++) { //barrasi 3 sotun
                    if (btns[i].getText().equals(btns[i + 3].getText()) && btns[i].getText().equals(xo)
                            && btns[i + 6].getText().equals(" ")) {
                        btns[i + 6].setText("O");
                        boardCheck();
                        return;
                    } else if (btns[i + 3].getText().equals(btns[i + 6].getText()) && btns[i + 3].getText().equals(xo)
                            && btns[i].getText().equals(" ")) {
                        btns[i].setText("O");
                        boardCheck();
                        return;
                    } else if (btns[i].getText().equals(btns[i + 6].getText()) && btns[i].getText().equals(xo)
                            && btns[i + 3].getText().equals(" ")) {
                        btns[i + 3].setText("O");
                        boardCheck();
                        return;
                    }
                }
                //barrasi 2 ghotr
                for (int j = 0; j <= 2; j = j + 2) {
                    if (btns[j].getText().equals(btns[4].getText()) && btns[j].getText().equals(xo)
                            && btns[8 - j].getText().equals(" ")) {
                        btns[8 - j].setText("O");
                        boardCheck();
                        return;
                    }
                    if (btns[8 - j].getText().equals(btns[4].getText()) && btns[4].getText().equals(xo)
                            && btns[j].getText().equals(" ")) {
                        btns[j].setText("O");
                        boardCheck();
                        return;
                    }
                    if (btns[j].getText().equals(btns[8 - j].getText()) && btns[j].getText().equals(xo)
                            && btns[4].getText().equals(" ")) {
                        btns[4].setText("O");
                        boardCheck();
                        return;
                    }
                }
                check++;
            }
            moveCount--; //chon dar 2 for ghabli harkat nakarde
            computerRandomMove();
        }
    }

    //har harkati ke anjam mishe, board baraye bord va bakht check mishe
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
            Main.client.getPlayer().setVsComputerWins(Main.client.getPlayer().getVsComputerWins() + 1);
            gameEnded = true;
            return;
        }
        if(name.equals("O")){
            int lostCount = Main.client.getPlayer().getVsComputerLost() + 1;
            Main.client.getPlayer().setVsComputerLost(lostCount);
            gameEnded = true;
            return;
        }
        if( moveCount == 9 && !gameEnded){
            Main.client.getPlayer().setVsComputerTies(Main.client.getPlayer().getVsComputerTies() + 1);
        }
    }

    public void windowButton(Event event) {
        Main.client.sendComputerGameInfoToServer();

        Platform.exit();
    }
    public void backToMenu(Event event) throws IOException {
        Main.client.sendComputerGameInfoToServer();
        moveCount = 0;
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