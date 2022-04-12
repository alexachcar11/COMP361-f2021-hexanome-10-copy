package networksrc;

import org.minueto.MinuetoFileException;

import clientsrc.ClientMain;
import clientsrc.TokenSprite;
import clientsrc.ClientPlayer;
import clientsrc.ClientRoute;
import clientsrc.ClientTown;
import clientsrc.Game;
import serversrc.Route;
import serversrc.ServerGame;

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
        ClientRoute r = ;
        for(ClientRoute rou : Game.getAllRoutes()) { 
            System.out.println("R: The source town is " + srcT + " and the dest town is " + destT);
            System.out.println("ROU: The source town is " + rou.getSourceTownString() + " and the dest town is " + rou.getDestTownString());
            if((rou.getSourceTownString().equals(srcT) && rou.getDestTownString().equals(destT)) || (rou.getSourceTownString().equals(destT) && rou.getDestTownString().equals(srcT))) { 
                r = rou;
            } 
        }
        // ClientRoute r = ClientMain.currentGame.getTownGraph().getRoute(ClientTown.getTownByName(srcT), ClientTown.getTownByName(destT));
        try {
            ClientPlayer p = ClientPlayer.getPlayerByName(senderName);
            p.consumeToken(TokenSprite.getTokenSpriteByString(tok));

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
