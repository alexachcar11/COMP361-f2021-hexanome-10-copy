package networksrc;

import org.minueto.MinuetoFileException;

import clientsrc.ClientMain;

public class AuctionWinnerACK implements Action {

    String token;

    public AuctionWinnerACK(String tok){
        this.token = tok;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void execute() {
        try {
            ClientMain.currentPlayer.addTokenString(this.token);
        } catch (MinuetoFileException e) {
            e.printStackTrace();
        }
        
    }

}
