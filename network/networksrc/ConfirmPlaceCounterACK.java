package networksrc;

import clientsrc.ClientMain;
import clientsrc.ClientPlayer;
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
    public void execute() {
        // TODO Auto-generated method stub
        ClientPlayer thePlayer = ClientPlayer.getPlayerByName(senderName);
        // thePlayer.consumeToken(TokenImage.getTokenByName(tok));

        ClientMain.currentGame.getTownGraph().getRoute(Town.getTownByName(srcT), Town.getTownByName(destT));
    }
}
