package networksrc;

import org.minueto.MinuetoFileException;

import clientsrc.ClientMain;
import clientsrc.TokenSprite;


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
    public void execute() {
        // consume token from player's hand
        try {
            ClientMain.currentPlayer.consumeToken(TokenSprite.getTokenSpriteByString(tok));
        } catch (MinuetoFileException e) {
            System.out.println("MinuetoFileException");
        } catch (IllegalArgumentException e) {
            System.out.println("IllegalArgumentException");
        }
        // note: setting token to route is done in ConfirmPlaceCounterACK
    }
}