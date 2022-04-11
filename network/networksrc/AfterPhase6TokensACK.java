package networksrc;
import org.minueto.MinuetoFileException;

import clientsrc.ClientMain;
public class AfterPhase6TokensACK implements Action{

    String senderName;
    String token;
    int currentRound;

    public AfterPhase6TokensACK(String name, String pTok, int currRound){
        this.senderName = name;
        this.token = pTok;
        this.currentRound = currRound;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void execute() {
        // clear all tokens in hand for player
        ClientMain.currentPlayer.clearTokenHand();
        ClientMain.clearPickedTok();
        ClientMain.currentGame.setPhase(1);
        // add token to hand if it's not "none"
        if (!this.token.equalsIgnoreCase("none")){
            try {
                ClientMain.currentPlayer.addTokenString(this.token);
            } catch (MinuetoFileException e) {
                e.printStackTrace();
            }
        }
        ClientMain.currentGame.clearAllTokensOnMap();
        // update round
        ClientMain.currentGame.setRound(currentRound);

        // display
        try {
            ClientMain.displayBoardElements();
        } catch (MinuetoFileException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
}
