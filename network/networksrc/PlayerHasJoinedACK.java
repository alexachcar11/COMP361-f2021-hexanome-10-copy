package networksrc;

import org.minueto.MinuetoFileException;

import clientsrc.ClientMain;
import clientsrc.User;

public class PlayerHasJoinedACK implements Action {

    private String joinerName;

    public PlayerHasJoinedACK(String joinerName) {
        this.joinerName = joinerName;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void execute() {
        // add the player to LobbyServiceGameSession
        ClientMain.currentSession.addUser(new User(joinerName));
        // display users
        try {
            ClientMain.displayUsers();
        } catch (MinuetoFileException e) {
            e.printStackTrace();
        }
    }
    
}
