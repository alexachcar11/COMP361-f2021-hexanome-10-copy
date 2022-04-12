package networksrc;

import org.minueto.MinuetoFileException;

import clientsrc.ClientMain;
import clientsrc.ClientPlayer;

public class MovingBootACK implements Action{

    public MovingBootACK(){
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void execute() {
        System.out.println("It's now phase 5, move boot");
        ClientPlayer currentPlayer = ClientMain.currentPlayer;
        ClientMain.currentGame.setPhase(5);

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