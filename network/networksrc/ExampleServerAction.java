package networksrc;

import java.util.ArrayList;

import serversrc.Player;
import serversrc.ServerGame;

public class ExampleServerAction implements Action {

    private String senderName;

    public ExampleServerAction(String senderName) {
        this.senderName = senderName;
    }

    @Override
    public boolean isValid() {
        Player playerWhoSent = Player.getPlayerByName(senderName);
        ServerGame playersCurrentGame = playerWhoSent.getCurrentGame();

        // sanity check : the player is actually in that game
        ArrayList<Player> allPlayers = playersCurrentGame.getAllPlayers();
        if (!allPlayers.contains(playerWhoSent)) {
            return false;
        }

        // add other validation here

        return true;

    }

    @Override
    public void execute() {
        // server has received the message
        System.out.println("Executing the ExampleServerAction on the server");

        Player playerWhoSent = Player.getPlayerByName(senderName);
        ServerGame playersCurrentGame = playerWhoSent.getCurrentGame();

        // here you can do stuff with playerWhoSent and playersCurrentGame

        // send an ACK to all clients in the game
        ActionManager ackManager = ActionManager.getInstance();
        ExampleActionACK actionToSend = new ExampleActionACK(senderName);
        ackManager.sentToAllPlayersInGame(actionToSend, playersCurrentGame);

    }

}
