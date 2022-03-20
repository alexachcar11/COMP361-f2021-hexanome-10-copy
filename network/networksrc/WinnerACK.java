package networksrc;

import org.minueto.MinuetoFileException;

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
        try {
            ClientMain.displayWinnerByString(winnerName);
        } catch (MinuetoFileException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
