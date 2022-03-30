package networksrc;

import serversrc.TownGoldOption;

import org.json.simple.parser.ParseException;
import serversrc.GameLobby;
import serversrc.Mode;
import serversrc.ServerGame;
import serversrc.ServerMain;
import serversrc.ServerUser;

public class CreateNewGameAction implements Action {

    private String senderName;
    private String displayName;
    private int numberOfPlayers;
    private int gameRoundsLimit;
    private boolean destinationTownEnabled;
    private boolean witchEnabled;
    private String stringMode;
    private String stringTownGoldOption;

    public CreateNewGameAction(String senderName, String displayName, int numberOfPlayers, int gameRoundsLimit,
            boolean destinationTownEnabled, boolean witchEnabled, String mode, String townGoldOption) {
        this.senderName = senderName;
        this.displayName = displayName;
        this.numberOfPlayers = numberOfPlayers;
        this.gameRoundsLimit = gameRoundsLimit;
        this.destinationTownEnabled = destinationTownEnabled;
        this.witchEnabled = witchEnabled;
        this.stringMode = mode;
        this.stringTownGoldOption = townGoldOption;
    }

    /**
     * Returns false if some game settings are inconsistent with the gamemode.
     */
    @Override
    public boolean isValid() {
        // if elfenland
        if (stringMode.equals("elfenland")) {
            if (gameRoundsLimit != 3 && gameRoundsLimit != 4) {
                System.err.println("Elfenland must have 3 or 4 rounds. It currently has " + gameRoundsLimit);
                return false;
            }
            if (witchEnabled) {
                System.err.println("Elfenland cannot have a witch.");
                return false;
            }
            if (!stringTownGoldOption.equals("no")) {
                System.err.println("Elfenland cannot have a town gold option");
                return false;
            }
        }

        if (stringMode.equals("elfengold")) {
            if (gameRoundsLimit != 6) {
                System.err.println("Elfengold must have 6 rounds. It currently has " + gameRoundsLimit);
                return false;
            }
        }

        return true;
    }

    /**
     * Registers a game service and session on LS.
     * Upon failure: sends a failure message to the sender
     * Upon success: creates a ServerGame and GameLobby that are associated then
     * sends an ACK to senderName.
     */
    @Override
    public void execute() {

        try {
            // parse mode
            Mode mode = null;
            if (stringMode.equals("elfenland")) {
                mode = Mode.ELFENLAND;
            } else if (stringMode.equals("elfengold")) {
                mode = Mode.ELFENGOLD;
            }

            // parse town gold option
            TownGoldOption townGoldOption = null;
            if (stringTownGoldOption.equals("no")) {
                townGoldOption = TownGoldOption.NO;
            } else if (stringTownGoldOption.equals("yes-default")) {
                townGoldOption = (TownGoldOption.YESDEFAULT);
            } else if (stringTownGoldOption.equals("yes-random")) {
                townGoldOption = TownGoldOption.YESRANDOM;
            }

            // get the creator's ServerUser
            ServerUser creator = ServerUser.getServerUser(senderName);

            // create game service + session on LS
            String gameID = ServerMain.REGISTRATOR.createGame(creator, displayName, numberOfPlayers, gameRoundsLimit,
                    mode, witchEnabled, destinationTownEnabled, townGoldOption);
            if (gameID == null) {
                // LS gameservice or session failed
                // send ack to the sender only
                ActionManager ackManager = ActionManager.getInstance();
                CreateNewGameACK actionToSend = new CreateNewGameACK(); // this constructor represents a failure
                ackManager.sendToSender(actionToSend, senderName);
            } else {
                // success!
                // add server side logic
                ServerGame serverGame = new ServerGame(numberOfPlayers, gameRoundsLimit, destinationTownEnabled,
                        witchEnabled, mode, townGoldOption, gameID);
                GameLobby gameLobby = new GameLobby(gameID, serverGame, displayName, creator);
                ServerUser sUser = ServerUser.getServerUser(senderName);
                gameLobby.addUser(sUser);
                // send ack to the sender only
                ActionManager ackManager = ActionManager.getInstance();
                CreateNewGameACK actionToSend = new CreateNewGameACK(displayName, gameID, numberOfPlayers,
                        gameRoundsLimit, destinationTownEnabled, witchEnabled, stringMode, stringTownGoldOption);
                ackManager.sendToSender(actionToSend, senderName);
            }

        } catch (ParseException e) {
            // TODO : fill this?
            e.printStackTrace();
        }

    }

}
