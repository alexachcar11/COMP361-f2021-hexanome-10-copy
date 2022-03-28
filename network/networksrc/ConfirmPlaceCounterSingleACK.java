package networksrc;

import org.minueto.MinuetoFileException;

import clientsrc.ClientMain;
import clientsrc.TokenImage;


public class ConfirmPlaceCounterSingleACK implements Action {

    String tok;

    public ConfirmPlaceCounterSingleACK(String tok) {
        this.tok = tok;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void execute() throws MinuetoFileException {
        // consume token from player's hand
        ClientMain.currentPlayer.consumeToken(TokenImage.getTokenImageByString(tok));
        // note: setting token to route is done in ConfirmPlaceCounterACK
    }
}