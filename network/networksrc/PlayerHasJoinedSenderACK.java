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
        User existing = User.getUserByName(joinerName);
        ClientMain.currentSession.addUser(existing);

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
        ClientMain.currentSession.updateUsers();
        try {
            ClientMain.displayUsers();
            System.out.println("displaying users to the sender");
        } catch (MinuetoFileException e) {
            e.printStackTrace();
        }
        
    }
    
}
