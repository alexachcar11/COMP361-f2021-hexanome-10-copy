package networksrc;

import java.util.Arrays;

import clientsrc.ClientRoute;
import serversrc.Obstacle;
import serversrc.Player;
import serversrc.Route;
import serversrc.ServerGame;
import serversrc.Token;
import serversrc.Town;

public class PlaceCounterAction implements Action {

    String senderName;
    String tok;
    
    ClientRoute pickedRoute;
    boolean isWater;

    public PlaceCounterAction(String sender, ClientRoute pickedRoute, Boolean pIsWater, String pTok) {
        this.tok = pTok;
        this.senderName = sender;
        this.pickedRoute = pickedRoute;
        this.isWater = pIsWater;
    }

    @Override
    public boolean isValid() {
        if (this.tok == null){
            return false;
        }
        // check if it's player's turn
        Player playerWhoSent = Player.getPlayerByName(senderName);
        if (!playerWhoSent.getIsTurn()){
            return false;
        }
        ServerGame playersCurrentGame = playerWhoSent.getCurrentGame();
        // Town s = Town.getTownByName(srcTown);
        // Town d = Town.getTownByName(destTown);
        // Route rou = playersCurrentGame.getTownGraph().getRoute(s, d, this.isWater);

        Route selectedRoute = ServerGame.getAllRoutes().get(0);
        
        for(Route r: ServerGame.getAllRoutes()) { 
            if(Arrays.equals(r.getHitbox(), pickedRoute.getHitbox())) { 
                selectedRoute = r;
            }
        }
        
        Token t = Token.getTokenByName(tok);
        // check if it's phase 4 for placing counters
        // TODO: might need modification for elfengold
        if (playerWhoSent.getCurrentGame().getCurrentPhase() != 4){
            return false;
        }
        if (this.tok.equalsIgnoreCase("OBSTACLE") && !selectedRoute.hasCounter()){
            return false;
        }
        // check if it's river
        if (selectedRoute.isWater()){
            return false;
        }
        // check if there's already a token on road
        if (selectedRoute.hasCounter()){
            // if we're trying to place an obstacle
            // return true if there's no obstacle yet
            if (!selectedRoute.hasObstacle() && this.tok.equalsIgnoreCase("OBSTACLE")){
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
        
        Route selectedRoute = ServerGame.getAllRoutes().get(0);
        
        for(Route r: ServerGame.getAllRoutes()) { 
            if(Arrays.equals(r.getHitbox(), pickedRoute.getHitbox())) { 
                selectedRoute = r;
            }
        }

        Token t;
        if (tok.equalsIgnoreCase("obstacle")){
            System.out.println("it's an obstacle !");
            t = new Obstacle();
            t = (Obstacle) t;
        }
        else {
            System.out.println("it's a Token !");
            t = Token.getTokenByName(tok);
        }
        
        playersCurrentGame.playerPlaceCounter(playerWhoSent, selectedRoute, t);

        ActionManager ackManager = ActionManager.getInstance();

        // consume token on client side
        ackManager.sendToSender(new ConfirmPlaceCounterSingleACK(this.tok), this.senderName);
        
        ConfirmPlaceCounterACK actionToSend = new ConfirmPlaceCounterACK(senderName, selectedRoute.getSourceTownString(), selectedRoute.getDestTownString(),
                this.tok);
                
        // let everyone else know of the new state
        ackManager.sentToAllPlayersInGame(actionToSend, playersCurrentGame);

    }

}
