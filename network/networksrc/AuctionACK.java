package networksrc;

import org.minueto.MinuetoFileException;

import clientsrc.ClientMain;
import clientsrc.TokenSprite;

public class AuctionACK implements Action {

    private String tok;
    public AuctionACK(String pTok){
        this.tok = pTok;
    }
    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void execute() {
        // TODO: set phase in client to auction phase, make it 10 ?
        ClientMain.currentGame.setPhase(10);
        // let everyone know about the auction'd item
        try {
            ClientMain.currentGame.setAuctionToken(TokenSprite.getTokenSpriteByString(this.tok));
        } catch (MinuetoFileException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }
    
}
