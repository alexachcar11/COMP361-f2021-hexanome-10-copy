package networksrc;

import org.minueto.MinuetoFileException;

import clientsrc.ClientMain;
import clientsrc.Color;
import clientsrc.User;

public class PlayerHasJoinedACK implements Action {

    private String joinerName;
    private String color;

    public PlayerHasJoinedACK(String joinerName, String color) {
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
        User newUser = new User(joinerName);
        ClientMain.currentSession.addUser(newUser);

        // assign the color
        if (color.equals("BLUE")) {
            newUser.setColor(Color.BLUE);
        } else if (color.equals("BLACK")) {
            newUser.setColor(Color.BLACK);
        } else if (color.equals("RED")) {
            newUser.setColor(Color.RED);
        } else if (color.equals("GREEN")) {
            newUser.setColor(Color.GREEN);
        } else if (color.equals("YELLOW")) {
            newUser.setColor(Color.YELLOW);
        } else if (color.equals("PURPLE")) {
            newUser.setColor(Color.PURPLE);
        } 

        // display
        try {
            ClientMain.displayUsers();
        } catch (MinuetoFileException e) {
            e.printStackTrace();
        }
    }
    
}
