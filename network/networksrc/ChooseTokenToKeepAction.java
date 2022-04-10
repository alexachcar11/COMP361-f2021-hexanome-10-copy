package networksrc;

import serversrc.Player;
import serversrc.ServerGame;
import serversrc.Token;

public class ChooseTokenToKeepAction implements Action{

    String senderName;
    String token;

    public ChooseTokenToKeepAction(String name, String pTok){
        this.senderName = name;
        this.token = pTok;
    }
    @Override
    public boolean isValid() {
        Player playerWhoSent = Player.getPlayerByName(senderName);
        ServerGame playersCurrentGame = playerWhoSent.getCurrentGame();
        // check if it's player's turn
        if (!playerWhoSent.getIsTurn()){
            return false;
        }
        // check phase
        if (playersCurrentGame.getCurrentPhase() != 6){
            return false;
        }
        return true;
    }

    @Override
    public void execute() {
        Player playerWhoSent = Player.getPlayerByName(senderName);
        ServerGame playersCurrentGame = playerWhoSent.getCurrentGame();
        // sets the token player wants to keep
        playerWhoSent.setTokenToKeep(Token.getTokenByName(this.token));
    }
    
}
