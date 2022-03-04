package networksrc;

import java.util.ArrayList;
import serversrc.*;


public class MoveBootAction implements Action {

    private String senderName;
    private String srcTown;
    private String dstTown;

    public MoveBootAction(String senderName, String srcTown, String dstTown) {
        this.senderName = senderName;
        this.srcTown = srcTown;
        this.dstTown = dstTown;
    }

    @Override
    public boolean isValid() {
        // TODO: check if parameters are not null

        Player playerWhoSent = Player.getPlayerByName(senderName);
        ServerGame playersCurrentGame = playerWhoSent.getCurrentGame();

        // sanity check : the player is actually in that game
        ArrayList<Player> allPlayers = playersCurrentGame.getAllPlayers();
        if (!allPlayers.contains(playerWhoSent)) {
            return false;
        }

        // add other validation here
        // check for moveBoot phase
        if (playersCurrentGame.getCurrentPhase() != 5){
            // do nothing ?
            System.out.println("ERROR: Not moveBoot phase!");
            return false;
        }
        // check if it's not player's turn
        if (!playerWhoSent.getIsTurn()){
            // do nothing ?
            System.out.println("ERROR: Not " + playerWhoSent.getName() + "'s turn!");
            return false;
        }
        Town sTown = playersCurrentGame.getTownByName(srcTown);
        Town dTown = playersCurrentGame.getTownByName(dstTown);
        Route route = playersCurrentGame.getTownGraph().getRoute(sTown, dTown);
        // check if route is not adjacent to player
        if (!(route.getSource() == playerWhoSent.getTown() || route.getDest() == playerWhoSent.getTown())){
            // do nothing ?
            System.out.println("ERROR: Invalid route (not adjacent to player's location)!");
            return false;
        }
        // check if player has the cards required to travel that route
        // if (!playerWhoSent.hasCards(route.getRequiredCards())){
        //     System.out.println("ERROR: Player doesn't have all cards required!");
        //     return false;
        // }

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
        Route route = playersCurrentGame.getTownGraph().getRoute(sTown, dTown);

        System.out.println(playerWhoSent + " is in game " + playersCurrentGame.getGameID());

        // here you can do stuff with playerWhoSent and playersCurrentGame
        playersCurrentGame.playerMovedBoot(playerWhoSent, route);

        // send an ACK to all clients in the game
        ACKManager ackManager = ACKManager.getInstance();
        MoveBootACK actionToSend = new MoveBootACK(dstTown, playerWhoSent.getBootColor().name());
        ackManager.sentToAllPlayersInGame(actionToSend, playersCurrentGame);
    }
}
