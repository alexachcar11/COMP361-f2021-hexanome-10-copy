package networksrc;

import clientsrc.ClientMain;
import clientsrc.Game;
import clientsrc.LobbyServiceGame;
import clientsrc.Mode;

public class LaunchGameACK implements Action{

    // TODO: store the game state through a constructor

    @Override
    public boolean isValid() {
        // TODO: add validation
        return true;
    }

    @Override
    public void execute() {
        // set the session to launched
        ClientMain.currentSession.launch();

        // display board screen
    }
    
}
