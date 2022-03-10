package networksrc;

import org.minueto.MinuetoColor;
import org.minueto.MinuetoFileException;

import clientsrc.ClientMain;

public class WinnerACK implements Action {

    private String winnerName;

    public WinnerACK(String name) {
        this.winnerName = name;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void execute() {
        // display winner
        // try {
        // ClientMain.displayWinnerByString(winnerName);
        // } catch (MinuetoFileException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }
    }

}
