package networksrc;

import clientsrc.ClientMain;

import java.util.ArrayList;

public class LaunchGameACK implements Action {

    // TODO: store the game state through a constructor
    public LaunchGameACK() {}

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
        ClientMain.displayOriginalBoard();
        // modify game objects based on the game state received

        // display the new board

        System.out.println("LaunchGameACK received");
    }

}
