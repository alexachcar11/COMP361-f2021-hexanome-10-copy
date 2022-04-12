package networksrc;

import java.util.ArrayList;
import serversrc.*;

public class MoveBootAction implements Action {

    private String senderName;
    private String srcTown;
    private String dstTown;
    private boolean isWater;

    public MoveBootAction(String senderName, String srcTown, String dstTown, boolean pIsWater) {
        this.senderName = senderName;
        this.srcTown = srcTown;
        this.dstTown = dstTown;
        this.isWater = pIsWater;
    }

    @Override
    public boolean isValid() {
        if(this.senderName == null || this.srcTown == null || this.dstTown == null){
            return false;
        }
        Player playerWhoSent = Player.getPlayerByName(senderName);
        // check if it's not player's turn
        if (!playerWhoSent.getIsTurn()) {
            System.out.println("ERROR: Not " + playerWhoSent.getName() + "'s turn!");
            return false;
        }
        ServerGame playersCurrentGame = playerWhoSent.getCurrentGame();

        // sanity check : the player is actually in that game
        ArrayList<Player> allPlayers = playersCurrentGame.getAllPlayers();
        if (!allPlayers.contains(playerWhoSent)) {
            return false;
        }

        // add other validation here
        // check for moveBoot phase
        if (playersCurrentGame.getCurrentPhase() != 5) {
            // do nothing ?
            System.out.println("ERROR: Not moveBoot phase!");
            return false;
        }
        
        Town sTown = playersCurrentGame.getTownByName(srcTown);
        Town dTown = playersCurrentGame.getTownByName(dstTown);
        Route route = playersCurrentGame.getTownGraph().getRoute(sTown, dTown, this.isWater);
        // check if route is not adjacent to player
        if (!(route.getSource().equal(playerWhoSent.getTown()) || route.getDest().equal(playerWhoSent.getTown()))) {
            // do nothing ?
            System.out.println("ERROR: Invalid route (not adjacent to player's location)!");
            return false;
        }
        // check if player has the cards required to travel that route
        if (!playerWhoSent.hasCards(route.getRequiredCards(playerWhoSent.getTown()))) {
            System.out.println("ERROR: Player doesn't have all cards required!");
            return false;
        }
        if (!route.hasCounter()){
            return false;
        }
        return true;
    }

    @Override
    public void execute() {
        // server has received the message
        System.out.println("Executing the MoveBootAction on the server");

        Player playerWhoSent = Player.getPlayerByName(senderName);
        ServerGame playersCurrentGame = playerWhoSent.getCurrentGame();
        Town sTown = playersCurrentGame.getTownByName(srcTown);
        Town dTown = playersCurrentGame.getTownByName(dstTown);
        Route route = playersCurrentGame.getTownGraph().getRoute(sTown, dTown, this.isWater);

        // increase the amount of gold that the player has based on how much gold the town is worth 
        playerWhoSent.incrementGold(dTown.getGoldValue());

        System.out.println(playerWhoSent + " is in game " + playersCurrentGame.getGameID());

        playersCurrentGame.playerMovedBoot(playerWhoSent, route);

        // send an ACK to all clients in the game
        ActionManager ackManager = ActionManager.getInstance();
        // set the right dst town
        String goToTown;
        if (playerWhoSent.getTown().equal(dTown)){
            goToTown = srcTown;
        }
        else {
            goToTown = dstTown;
        }
        MoveBootACK actionToSend = new MoveBootACK(goToTown, senderName);
        ackManager.sentToAllPlayersInGame(actionToSend, playersCurrentGame);
    }
}
