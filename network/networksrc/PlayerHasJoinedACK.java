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
        User existing = User.getUserByName(joinerName);
        if (existing == null) {
            ClientMain.currentSession.addUser(new User(joinerName));
        } else {
            ClientMain.currentSession.addUser(existing);
        }

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
        try {
            ClientMain.displayUsers();
            System.out.println("displaying users.");
        } catch (MinuetoFileException e) {
            e.printStackTrace();
        }
    }
    
}
