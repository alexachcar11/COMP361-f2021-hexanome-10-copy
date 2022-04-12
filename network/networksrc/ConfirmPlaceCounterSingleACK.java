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
        ClientMain.clearPickedRoute();
        ClientMain.clearPickedTok();
        // consume token from player's hand
        try {
            System.out.println("Consuming token: " + this.tok);
            ClientMain.currentPlayer.consumeToken(TokenSprite.getTokenSpriteByString(tok));
            System.out.println("After consuming token: " + this.tok);
        } catch (MinuetoFileException e) {
            System.out.println("MinuetoFileException");
        } catch (IllegalArgumentException e) {
            System.out.println("IllegalArgumentException inside ConfirmPlaceCounterSingleACK");
        }
        // note: setting token to route is done in ConfirmPlaceCounterACK

        // display
        try {
            ClientMain.displayBoardElements();
        } catch (MinuetoFileException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}