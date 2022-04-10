package networksrc;
import serversrc.*;

public class PassTurnAction implements Action {

    private String senderName;

    public PassTurnAction(String senderName){
        this.senderName = senderName;
    }

    // is valid if this player has true for isTurn
    @Override
    public boolean isValid() {
        Player playerWhoSent = Player.getPlayerByName(senderName);
        return playerWhoSent.getIsTurn();
    }

    @Override
    public void execute() {
        Player playerWhoSent = Player.getPlayerByName(senderName);
        ServerGame playersCurrentGame = playerWhoSent.getCurrentGame();
        if (playersCurrentGame.getCurrentPhase()==10){
            playersCurrentGame.getAuction().setLastPassedPlayer(playerWhoSent);
            playersCurrentGame.getAuction().getBiddersList().remove(playerWhoSent);
        }
        playersCurrentGame.nextPlayer();
    }
}