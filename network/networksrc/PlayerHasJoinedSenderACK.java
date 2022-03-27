package networksrc;

import org.minueto.MinuetoFileException;

import clientsrc.ClientMain;
import clientsrc.Color;
import clientsrc.User;

public class PlayerHasJoinedSenderACK implements Action{

    private String joinerName;
    private String color;

    public PlayerHasJoinedSenderACK(String joinerName, String color) {
        this.joinerName = joinerName;
        this.color = color;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void execute() {
        // add the player to LobbyServiceGameSession
        User existing = ClientMain.currentUser;
        ClientMain.currentSession.addUser(existing);

        // set current game
        ClientMain.currentGame = ClientMain.currentSession.getGame();

        // assign the color
        if (color.equals("BLUE")) {
            existing.setColor(Color.BLUE);
        } else if (color.equals("BLACK")) {
            existing.setColor(Color.BLACK);
        } else if (color.equals("RED")) {
            existing.setColor(Color.RED);
        } else if (color.equals("GREEN")) {
            existing.setColor(Color.GREEN);
        } else if (color.equals("YELLOW")) {
            existing.setColor(Color.YELLOW);
        } else if (color.equals("PURPLE")) {
            existing.setColor(Color.PURPLE);
        } 

        // get other users' colors from the server
        ClientMain.currentSession.updateUsers();

        // display users
        try {
            ClientMain.displayUsers();
        } catch (MinuetoFileException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // display game info
        ClientMain.displayLobbyInfo();
        
    }
    
}
