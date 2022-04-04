package networksrc;
import org.minueto.MinuetoFileException;

import clientsrc.ClientMain;
public class AfterPhase6TokensACK implements Action{

    String senderName;
    String token;

    public AfterPhase6TokensACK(String name, String pTok){
        this.senderName = name;
        this.token = pTok;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void execute() {
        // clear all tokens in hand for player
        ClientMain.currentPlayer.clearTokenHand();
        // add token to hand if it's not "none"
        if (!this.token.equalsIgnoreCase("none")){
            try {
                ClientMain.currentPlayer.addTokenString(this.token);
            } catch (MinuetoFileException e) {
                e.printStackTrace();
            }
        }
        ClientMain.currentGame.clearAllTokensOnMap();
    }
    
}
