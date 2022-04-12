package networksrc;

import org.minueto.MinuetoFileException;

import clientsrc.ClientMain;
import clientsrc.TokenSprite;
import clientsrc.ClientPlayer;
import clientsrc.ClientRoute;
import clientsrc.ClientTown;

public class ConfirmPlaceCounterACK implements Action {

    String senderName;
    String srcT;
    String destT;
    String tok;

    public ConfirmPlaceCounterACK(String senderName, String srcTown, String destTown, String tok) {
        this.senderName = senderName;
        this.srcT = srcTown;
        this.destT = destTown;
        this.tok = tok;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void execute() {
        // set token to route
        ClientRoute r = ClientMain.currentGame.getTownGraph().getRoute(ClientTown.getTownByName(srcT), ClientTown.getTownByName(destT));
        try {
            System.out.println("Setting token on ClientRoute: Token: " + this.tok);
            r.setToken(TokenSprite.getTokenSpriteByString(tok));
            System.out.println("After setting token on ClientRoute: Token: " + this.tok);
        } catch (MinuetoFileException e) {
            System.out.println("MinuetoFileException");
        } catch (IllegalArgumentException e) {
            System.out.println("IllegalArgumentException inside ConfirmPlaceCounterACK");
        }

        // display
        try {
            ClientMain.displayBoardElements();
        } catch (MinuetoFileException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
