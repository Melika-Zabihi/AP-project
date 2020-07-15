package sample;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class PlayerClient{
    private Socket socket;
    private boolean canEnter;
    private boolean emailError;
    private boolean userNameError;
    private boolean inputError;
    private  boolean passwordError;
    private int userState;
    private PlayerInfo player;
    private PlayerInfo opponent;

    public void sendLoginInfoToServer(PlayerInfo player)
    {
        canEnter = false;
        try{
            socket = new Socket("LocalHost",PlayerServer.PORT);
        }catch (IOException e){
            System.out.println("socket fail");
        }
        try {
            ObjectOutputStream toServer = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream fromServer = new ObjectInputStream(socket.getInputStream());
            System.out.println("is sending data..");
            toServer.writeObject(player);
            System.out.println("sent");
            Object user;
            if((user = fromServer.readObject()) != null)
            {
                canEnter = true;
                this.setPlayer((PlayerInfo) user);
            }
            else {
                canEnter = false;
            }

        }catch (IOException e){
            try {
                socket.close();
            } catch (IOException ex) {
                System.out.println("socket was not closed");
            }
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void sendSignUpInfoToServer(PlayerInfo player){
        canEnter = false;
        emailError = false;
        userNameError = false;
        inputError = false;
        passwordError = false;
        try{
            socket = new Socket("LocalHost",PlayerServer.PORT);
        }catch (IOException e){
            System.out.println("socket fail");
        }
        try {
            ObjectOutputStream toServer = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream fromServer = new ObjectInputStream(socket.getInputStream());

            toServer.writeObject(player);
            userState = (int)fromServer.readObject();
            if(userState == 0){
                canEnter = true;
            }
            else if(userState == 1)
                inputError = true;

            else if(userState == 2) {
                emailError = true;
                userNameError = true;
                passwordError = true;
            }
            else if(userState == 3) {
                emailError = true;
                passwordError = true;
            }
            else if(userState == 4) {
                userNameError = true;
                emailError = true;
            }
            else if(userState == 5) {
                userNameError = true;
                passwordError = true;
            }
            else if(userState == 6)
                userNameError = true;
            else if(userState == 7)
                passwordError = true;
            else if(userState == 8)
                emailError = true;

        }catch (IOException e){
            try {
                socket.close();
                e.printStackTrace();
            } catch (IOException ex) {
                System.out.println("socket was not closed");
            }
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void sendComputerGameInfoToServer(){

        this.getPlayer().setFromComputerGame(true);

        try{
            socket = new Socket("LocalHost",PlayerServer.PORT);
        }catch (IOException e){
            System.out.println("socket fail");
        }
        try {
            ObjectOutputStream toServer = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream fromServer = new ObjectInputStream(socket.getInputStream());
            System.out.println("is sending data..");
            toServer.writeObject(this.getPlayer());
            System.out.println("sent");
            fromServer.readObject();

        }catch (IOException e){
            try {
                socket.close();
            } catch (IOException ex) {
                System.out.println("socket was not closed");
            }
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public Boolean sendSearchedPlayerToServer(PlayerInfo player){

        try{
            socket = new Socket("LocalHost",PlayerServer.PORT);
        }catch (IOException e){
            System.out.println("socket fail");
        }
        try {
            ObjectOutputStream toServer = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream fromServer = new ObjectInputStream(socket.getInputStream());
            System.out.println("is sending data..");
            toServer.writeObject(player);
            System.out.println("sent");
            if(player.isFromPlayerGame()){
                opponent = (PlayerInfo)fromServer.readObject();
            }
            else {
                int state = (int) fromServer.readObject();
                if (state == 1)
                    return true;

                else
                    return false;
            }

        }catch (IOException e){
            try {
                socket.close();
            } catch (IOException ex) {
                System.out.println("socket was not closed");
            }
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;

    }


    public PlayerClient(){

    }

    public boolean canEnter(){
        return canEnter;
    }

    public PlayerInfo getOpponent() {
        return opponent;
    }

    public boolean hasUserNameError() {
        return userNameError;
    }

    public boolean hasEmailError() {
        return emailError;
    }

    public boolean hasInputError() {
        return inputError;
    }

    public boolean hasPasswordError() {
        return passwordError;
    }

    public void setPlayer(PlayerInfo player) {
        this.player = player;
    }

    public PlayerInfo getPlayer() {
        return player;
    }
}

