package networksrc;

import clientsrc.ClientMain;
import clientsrc.Player;

public class PlaceCounterACK implements Action {

    public PlaceCounterACK() {

    }

    @Override
    // make sure not to click on edge cases (don't try to click on route with token
    // on it already)
    public boolean isValid() {
        return true;
    }

    @Override
    public void execute() {

        Player currentPlayer = ClientMain.currentPlayer;
        ClientMain.currentGame.setPhase(4);

        // last line
        currentPlayer.setTurn(true);

    }
}
