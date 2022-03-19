package networksrc;

import java.util.ArrayList;

import serversrc.GameLobby;
import serversrc.Mode;
import serversrc.ServerGame;
import serversrc.ServerUser;
import serversrc.TownGoldOption;

public class GetGameInfoAction implements Action {

    private String senderName;
    private String gameID;

    public GetGameInfoAction(String senderName, String gameID) {
        this.senderName = senderName;
        this.gameID = gameID;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void execute() {
        GameLobby gameLobby = GameLobby.getGameLobby(gameID);
        ServerGame serverGame = gameLobby.getServerGame();

        // get game info
        int numberOfPlayers = serverGame.getNumberOfPlayers();
        int gameRoundsLimit = serverGame.getGameRoundsLimit();
        boolean destinationTownEnabled = serverGame.isDestinationTownEnabled();
        boolean witchEnabled = serverGame.witchEnabled;
        Mode mode = serverGame.getMode();
        TownGoldOption townGoldOption = serverGame.getTownGoldOption();

        // convert custom objects into strings

        String modeString = null;
        if (mode.equals(Mode.ELFENLAND)) {
            modeString = "elfenland";
        } else if (mode.equals(Mode.ELFENGOLD)) {
            modeString = "elfengold";
        }

        String townGoldOptionString = null;
        if (townGoldOption.equals(TownGoldOption.NO)) {
            townGoldOptionString = "no";
        } else if (townGoldOption.equals(TownGoldOption.YESDEFAULT)) {
            townGoldOptionString = "yes-default";
        } else if (townGoldOption.equals(TownGoldOption.YESRANDOM)) {
            townGoldOptionString = "yes-random";
        }

        // send ACK to sender
        ActionManager ackManager = ActionManager.getInstance();
        GetGameInfoACK actionToSend = new GetGameInfoACK(numberOfPlayers, gameRoundsLimit, destinationTownEnabled,
                witchEnabled, modeString, townGoldOptionString);
        ackManager.sendToSender(actionToSend, senderName);
    }

}
