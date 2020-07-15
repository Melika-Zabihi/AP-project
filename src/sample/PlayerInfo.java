package sample;

import java.io.Serializable;
import java.util.ArrayList;

public class PlayerInfo implements Serializable {
    private String userName;
    private String password;
    private String email;
    private String question;
    private String answer;
    private boolean fromLogin;
    private boolean fromSignUp;
    private boolean fromComputerGame;
    private boolean fromPlayerGame;
    private boolean fromHomePage = false;
    private int vsComputerWins ;
    private int vsComputerLost ;
    private int vsComputerTies ;
    private ArrayList<String> contacts = new ArrayList<>();
    private int[] boardButton = new int[10];
    public PlayerInfo(String userName)
    {
        this.userName = userName;
        fromHomePage = true;
    }
    public PlayerInfo(String userName, String password){
        this.userName = userName;
        this.password = password;
        fromLogin = true;
    }

    public PlayerInfo(String userName, String password, String email,
                      String question, String answer)
    {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.question = question;
        this.answer = answer;
        fromSignUp = true;
        vsComputerLost =0;
        vsComputerTies =0;
        vsComputerTies =0;
    }


    public int getButtonValue(int i){
        return boardButton[i];
    }


    public boolean isFromLogin() {
        return fromLogin;
    }

    public boolean isFromSignUp() {
        return fromSignUp;
    }

    public boolean isFromComputerGame() {
        return fromComputerGame;
    }

    public boolean isFromHomePage() {
        return fromHomePage;
    }

    public int getVsComputerLost() {
        return vsComputerLost;
    }

    public int getVsComputerTies() {
        return vsComputerTies;
    }

    public int getVsComputerWins() {
        return vsComputerWins;
    }

    public void setVsComputerLost(int vsComputerLost) {
        this.vsComputerLost = vsComputerLost;
    }

    public void setVsComputerTies(int vsComputerTies) {
        this.vsComputerTies = vsComputerTies;
    }

    public void setVsComputerWins(int vsComputerWins) {
        this.vsComputerWins = vsComputerWins;
    }

    public void setFromComputerGame(boolean fromComputerGame) {
        this.fromComputerGame = fromComputerGame;
        this.fromSignUp = false;
        this.fromLogin = false;
    }

    public void setFromPlayerGame(boolean fromPlayerGame) {
        this.fromPlayerGame = fromPlayerGame;
        this.fromHomePage = false;
    }

    public boolean isFromPlayerGame(){
        return fromPlayerGame;
    }

    public ArrayList<String> getContacts() {
        return contacts;
    }

    public void addContacts(String name){
        contacts.add(name);
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

}
