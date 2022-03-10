package networksrc;


import org.minueto.MinuetoColor;

import clientsrc.ClientMain;

public class WinnerACK implements Action{

    private String winnerName;

    public WinnerACK(String winnerName) {
        this.winnerName = winnerName;

    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void execute() {
        // display winner
        //ClientMain.displayWinnerByString(winnerName);
    }

}
