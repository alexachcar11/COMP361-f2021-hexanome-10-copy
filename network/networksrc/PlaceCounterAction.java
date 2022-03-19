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
        // TODO: not complete, requires more validity checks
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
        ConfirmPlaceCounterACK actionToSend = new ConfirmPlaceCounterACK(senderName, this.srcTown, this.destTown,
                this.tok);
        ackManager.sentToAllPlayersInGame(actionToSend, playersCurrentGame);

    }

}
