package networksrc;

import org.minueto.MinuetoFileException;

import clientsrc.ClientMain;
import clientsrc.Player;
import clientsrc.Route;
import clientsrc.TokenImage;
import clientsrc.Town;

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
    public void execute() throws MinuetoFileException {
        // set token to route
        Route r = ClientMain.currentGame.getTownGraph().getRoute(Town.getTownByName(srcT), Town.getTownByName(destT));
        r.setToken(TokenImage.getTokenImageByString(tok));
    }
}
