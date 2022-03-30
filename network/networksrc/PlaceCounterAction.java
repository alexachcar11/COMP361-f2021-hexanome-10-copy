package networksrc;

import serversrc.Player;
import serversrc.Route;
import serversrc.ServerGame;
import serversrc.Token;
import serversrc.Town;

public class PlaceCounterAction implements Action {

    String senderName;
    String tok;
    String srcTown;
    String destTown;

    public PlaceCounterAction(String sender, String src, String dest, String pTok) {
        this.tok = pTok;
        this.senderName = sender;
        this.srcTown = src;
        this.destTown = dest;
    }

    @Override
    public boolean isValid() {
        if (this.tok == null){
            return false;
        }
        // check if it's player's turn
        Player playerWhoSent = Player.getPlayerByName(senderName);
        ServerGame playersCurrentGame = playerWhoSent.getCurrentGame();
        Town s = Town.getTownByName(srcTown);
        Town d = Town.getTownByName(destTown);
        Route rou = playersCurrentGame.getTownGraph().getRoute(s, d);
        Token t = Token.getTokenByName(tok);
        if (!playerWhoSent.getIsTurn()){
            return false;
        }
        // check if it's phase 4 for placing counters
        // TODO: might need modification for elfengold
        if (playerWhoSent.getCurrentGame().getCurrentPhase() != 4){
            return false;
        }
        // check if there's already a token on road
        if (rou.hasCounter()){
            // if we're trying to place an obstacle
            // return true if there's no obstacle yet
            if (!rou.hasObstacle() && this.tok.equalsIgnoreCase("OBSTACLE")){
                return true;
            }
            else {
                return false;
            }
        }
        // player should have the token in hand
        return true;
    }

    @Override
    public void execute() {
        // server has received the message
        System.out.println("Executing the PlaceCounterAction on the server");
        Player playerWhoSent = Player.getPlayerByName(senderName);
        ServerGame playersCurrentGame = playerWhoSent.getCurrentGame();
        Town s = Town.getTownByName(srcTown);
        Town d = Town.getTownByName(destTown);
        Route rou = playersCurrentGame.getTownGraph().getRoute(s, d);
        Token t = Token.getTokenByName(tok);

        playersCurrentGame.playerPlaceCounter(playerWhoSent, rou, t);

        ActionManager ackManager = ActionManager.getInstance();
        // consume token on client side
        
        ConfirmPlaceCounterACK actionToSend = new ConfirmPlaceCounterACK(senderName, this.srcTown, this.destTown,
                this.tok);
        ackManager.sentToAllPlayersInGame(actionToSend, playersCurrentGame);

    }

}
