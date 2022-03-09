package networksrc;

import clientsrc.ClientMain;

import java.util.ArrayList;

public class LaunchGameACK implements Action {

    private ArrayList<String> playerNames;
    private ArrayList<ArrayList<String>> playerCards;

    // TODO: store the game state through a constructor
    public LaunchGameACK(ArrayList<String> playerNames, ArrayList<ArrayList<String>> playerCards) {

        this.playerNames = playerNames;
        this.playerCards = playerCards;
    }

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
    }

}
