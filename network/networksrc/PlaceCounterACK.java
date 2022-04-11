package networksrc;

import org.minueto.MinuetoFileException;

import clientsrc.ClientMain;
import clientsrc.ClientPlayer;

public class PlaceCounterACK implements Action {

    public PlaceCounterACK(){
    }

    @Override
    // make sure not to click on edge cases (don't try to click on route with token
    // on it already)
    public boolean isValid() {
        return true;
    }

    @Override
    public void execute() {
        System.out.println("It's now phase 4, place counters");
        ClientPlayer currentPlayer = ClientMain.currentPlayer;
        ClientMain.currentGame.setPhase(4);

        // currentPlayer.setTurn(true);

        // display
        try {
            ClientMain.displayBoardElements();
        } catch (MinuetoFileException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
