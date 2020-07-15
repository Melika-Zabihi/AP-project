package sample;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class PlayerServer {

    public static final int PORT = 8800;
    private static int id = 1;
    public static void main(String[] args) throws IOException {
        new PlayerServer();
    }
    public PlayerServer() throws IOException{

        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("waiting for connection...");
        try {
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("connected");
                try {
                    new ServeOnePlayer(socket);
                } catch (IOException e) {
                    socket.close();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }finally {
            serverSocket.close();
        }
    }
}
class ServeOnePlayer extends Thread implements Serializable{

    private ObjectOutputStream outputToFile;
    private ObjectInputStream inputFromClient;
    private ObjectOutputStream outputToClient;
    private Socket socket;
    private File file = new File("PlayersInfo.txt");
    public static ArrayList<Object> playerObjects = new ArrayList<>();

    public ServeOnePlayer(Socket s) throws IOException, ClassNotFoundException {
        socket = s;
        start();
    }

    @Override
    public void run() {
        Integer a = 0;
        try{
            inputFromClient = new ObjectInputStream(socket.getInputStream());
            outputToClient = new ObjectOutputStream(socket.getOutputStream());
            outputToFile = new ObjectOutputStream(new FileOutputStream(file,false));
            while(true) {
                Object object = inputFromClient.readObject();
                System.out.println("input from client received.");
                PlayerInfo inputPlayer = (PlayerInfo) object;

                if(inputPlayer.isFromLogin()){

                    outputToClient.writeObject(checkInfo(inputPlayer));
                }

                if(inputPlayer.isFromSignUp()) {
                    a = findError(inputPlayer);
                    if(a == 0) { //etelaat doroste va karbar ozv mishe
                        playerObjects.add(object);
                        PlayerInfo player = (PlayerInfo) object;
                        outputToFile.writeObject(playerObjects);
                    }
                    outputToClient.writeObject(a);
                }

                if(inputPlayer.isFromComputerGame()) {
                    savePlayerData(inputPlayer);
                    outputToFile.writeObject(playerObjects);
                    outputToClient.writeObject(a);
                }

                if(inputPlayer.isFromHomePage()){
                    a = findUser(inputPlayer);
                    outputToClient.writeObject(a);
                }

                if(inputPlayer.isFromPlayerGame()){

                    outputToClient.writeObject(checkInfo(inputPlayer));

                }

            }
        }catch (IOException e){
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }finally {
            try {
                socket.close();
            }catch (IOException e){
                System.out.println("Socket not closed");
            }
        }
    }

    //age player ghablan ozv shode bashe un ro peida mikone va be surat object
    //barmigardune. dar gheir un surat null return mikone
    private PlayerInfo checkInfo(PlayerInfo inputPlayer){


        try {
            ObjectInputStream objectInput = new ObjectInputStream(new FileInputStream(file));
            playerObjects = (ArrayList<Object>) objectInput.readObject();
        } catch (IOException e) {
            System.out.println("end of file");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //age az Game player ro gerefte faghat userNamesh check mishe
        //vagarna yani az login player ro gerfte va ramzesh ham check mishe

        if(inputPlayer.isFromPlayerGame()){

            for (Object user : playerObjects) {
                PlayerInfo player = (PlayerInfo) user;
                if (inputPlayer.getUserName().equals(player.getUserName()) )
                    return player;
            }
        }


        for (Object user : playerObjects) {
            PlayerInfo player = (PlayerInfo) user;
            if (inputPlayer.getUserName().equals(player.getUserName()) &&
                     inputPlayer.getPassword().equals(player.getPassword()))
                return player;
        }
        return null;
    }

    //etelaat eshtebah karbar peida mishe va be client frestade mishe
    private Integer findError(PlayerInfo inputPlayer) {
        boolean takenUserName = false;
        boolean shortPassword = false;
        boolean wrongEmail = false;
        try {
            ObjectInputStream objectInput = new ObjectInputStream(new FileInputStream(file));
            playerObjects = (ArrayList<Object>) objectInput.readObject();
        } catch (IOException e) {
            System.out.println("end of file");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        for (Object user : playerObjects) {
            PlayerInfo player = (PlayerInfo) user;
            System.out.println("**"+player.getUserName());
            if (inputPlayer.getUserName().equals(player.getUserName()))
                takenUserName = true; //userName tekrari
        }
        if(((!inputPlayer.getEmail().contains("@gmail.com") && !inputPlayer.getEmail().contains("@yahoo.com"))
                || inputPlayer.getEmail().length()<=10))
            wrongEmail = true;
        if(inputPlayer.getPassword().length()<8)
            shortPassword = true;

        if(inputPlayer.getPassword().isBlank() || inputPlayer.getUserName().isBlank() || inputPlayer.getAnswer().isBlank()
                || inputPlayer.getEmail().isBlank() || inputPlayer.getQuestion().isBlank())
            return 1;
        if(wrongEmail && shortPassword && takenUserName)
            return 2;
        if(wrongEmail && shortPassword)
            return 3;
        if(wrongEmail && takenUserName)
            return 4;
        if(shortPassword && takenUserName)
            return 5;
        if(takenUserName)
            return 6;
        if(shortPassword)
            return 7;
        if(wrongEmail)
            return 8;
        return 0;

    }

    public void savePlayerData(PlayerInfo unSavedPlayer){
        int index;
        try {
            ObjectInputStream objectInput = new ObjectInputStream(new FileInputStream(file));
            playerObjects = (ArrayList<Object>) objectInput.readObject();
        } catch (IOException e) {
            System.out.println("end of file");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            for (Object user : playerObjects) {
                PlayerInfo player = (PlayerInfo) user;
                if (unSavedPlayer.getUserName().equals(player.getUserName())) {
                    index = playerObjects.indexOf(user);
                    playerObjects.remove(user);
                    playerObjects.add(index, unSavedPlayer);
                    System.out.println(unSavedPlayer.getUserName() + " Data Saved.");
                }
            }
        }catch(ConcurrentModificationException e) {
        }
    }


    public Integer findUser(PlayerInfo inputPlayer){

        try {
            ObjectInputStream objectInput = new ObjectInputStream(new FileInputStream(file));
            playerObjects = (ArrayList<Object>) objectInput.readObject();
        } catch (IOException e) {
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        for (Object user : playerObjects) {
            PlayerInfo player = (PlayerInfo) user;
            System.out.println("**"+player.getUserName());
            if (inputPlayer.getUserName().equals(player.getUserName())) {
                return 1;
            }
        }
        return 0;
    }

}
