package networksrc;

import org.minueto.MinuetoFileException;

import clientsrc.ActionManager;
import clientsrc.ClientMain;
import clientsrc.Player;

public class PlaceCounterACK implements Action{
    

    public PlaceCounterACK(){
    }

    @Override
    // make sure not to click on edge cases (don't try to click on route with token on it already)
    public boolean isValid() {
        return true;
    }

    @Override
    public void execute() throws MinuetoFileException {
        System.out.printLn("It's now phase 4, place counters");
        Player currentPlayer = ClientMain.currentPlayer;
        ClientMain.currentGame.setPhase(4);

        // currentPlayer.setTurn(true);
    }
}
