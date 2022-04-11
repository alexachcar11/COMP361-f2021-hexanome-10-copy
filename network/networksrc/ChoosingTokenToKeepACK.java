package networksrc;
import org.minueto.MinuetoFileException;

import clientsrc.ClientMain;
import clientsrc.ClientPlayer;
public class ChoosingTokenToKeepACK implements Action{

    public ChoosingTokenToKeepACK(){
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void execute() {
        System.out.println("It's now phase 6, choose token to keep");
        ClientPlayer currentPlayer = ClientMain.currentPlayer;
        ClientMain.currentGame.setPhase(6);
        // display
        try {
            ClientMain.displayBoardElements();
        } catch (MinuetoFileException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
