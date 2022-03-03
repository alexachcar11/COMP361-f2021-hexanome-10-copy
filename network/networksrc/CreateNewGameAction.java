package networksrc;

import serversrc.TownGoldOption;
import serversrc.GameLobby;
import serversrc.Mode;
import serversrc.ServerGame;

public class CreateNewGameAction implements Action{

    private String senderName;
    private String gameID;
    private int numberOfPlayers;
    private int gameRoundsLimit;
    private boolean destinationTownEnabled;
    private boolean witchEnabled;
    private Mode mode;
    private TownGoldOption townGoldOption;

    public CreateNewGameAction(String senderName, String gameID, int numberOfPlayers, int gameRoundsLimit, boolean destinationTownEnabled, boolean witchEnabled, String mode, String townGoldOption) {
        this.senderName = senderName;
        this.gameID = gameID;
        this.numberOfPlayers = numberOfPlayers;
        this.gameRoundsLimit = gameRoundsLimit;
        this. destinationTownEnabled = destinationTownEnabled;
        this.witchEnabled = witchEnabled;
        if (mode.equals("elfenland")) {
            this.mode = Mode.ELFENLAND;
        } else if (mode.equals("elfengold")) {
            this.mode = Mode.ELFENGOLD;
        }
        if (townGoldOption.equals("no")) {
            this.townGoldOption = TownGoldOption.NO;
        } else if (townGoldOption.equals("yes-default")) {
            this.townGoldOption.equals(TownGoldOption.YESDEFAULT);
        } else if (townGoldOption.equals("yes-random")) {
            this.townGoldOption = TownGoldOption.YESRANDOM;
        }
    }

    /**
     * Returns false if some game settings are inconsistent with the gamemode.
     */
    @Override
    public boolean isValid() {
        // if elfenland
        if (mode.equals(Mode.ELFENLAND)) {
            if (gameRoundsLimit != 3 && gameRoundsLimit != 4) {
                System.err.println("Elfenland must have 3 or 4 rounds. It currently has " + gameRoundsLimit);
                return false;
            }
            if (witchEnabled) {
                System.err.println("Elfenland cannot have a witch.");
                return false;
            }
            if (!townGoldOption.equals(TownGoldOption.NO)) {
                System.err.println("Elfenland cannot have a town gold option");
                return false;
            }
        }

        if (mode.equals(Mode.ELFENGOLD)) {
            if (gameRoundsLimit != 6) {
                System.err.println("Elfengold must have 6 rounds. It currently has " + gameRoundsLimit);
                return false;
            }
        }

        return true;
    }

    /**
     * Creates a ServerGame and GameLobby that are associated. 
     * Then sends an ACK to senderName.
     */
    @Override
    public void execute() {
        ServerGame serverGame = new ServerGame(numberOfPlayers, gameRoundsLimit, destinationTownEnabled, witchEnabled, mode, townGoldOption);
        GameLobby gameLobby = new GameLobby(gameID, serverGame);
        // send ack to the sender only
        ACKManager ackManager = ACKManager.getInstance();
        CreateNewGameACK actionToSend = new CreateNewGameACK();
        ackManager.sendToSender(actionToSend, senderName);
    }
    
}
