package networksrc;

import clientsrc.ClientMain;
import clientsrc.Game;
import clientsrc.LobbyServiceGame;
import clientsrc.LobbyServiceGameSession;
import clientsrc.Mode;
import clientsrc.TownGoldOption;
import clientsrc.User;

public class CreateNewGameACK implements Action {

    private String displayName;
    private String gameID;
    private int numberPlayers;
    private int numberRounds;
    private boolean destinationTownEnabled;
    private boolean witchEnabled;
    private String stringMode;
    private String stringTownGoldOption;

    public CreateNewGameACK() {
        // this constructor indicates a CreateNewGame failure
        this.gameID = null;
    }

    public CreateNewGameACK(String displayName, String gameID, int numberPlayers, int numberRounds,
            boolean destinationTownEnabled, boolean witchEnabled, String stringMode, String stringTownGoldOption) {
        this.displayName = displayName;
        this.gameID = gameID;
        this.numberPlayers = numberPlayers;
        this.numberRounds = numberRounds;
        this.destinationTownEnabled = destinationTownEnabled;
        this.witchEnabled = witchEnabled;
        this.stringMode = stringMode;
        this.stringTownGoldOption = stringTownGoldOption;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void execute() {

        if (gameID == null) {
            // server failed to create a new game
            System.out.println("CreateNewGameACK received: failed to create a new game");

            // show error message because the game already exists ??
            ClientMain.displayNameTaken();

        } else {
            // server succesfully created a new game
            System.out.println("CreateNewGameACK received: successfully created a new game");

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

            // create a new Game object
            Game newGame = new Game(numberPlayers, numberRounds, destinationTownEnabled, witchEnabled, mode,
                    townGoldOption);

            // set as currentgame
            ClientMain.currentGame = newGame;

            // get creator
            User currentUser = ClientMain.currentUser;

            // create new LobbyServiceGameSession
            LobbyServiceGameSession newSession = new LobbyServiceGameSession("", newGame, currentUser, gameID,
                    displayName);

            // set as current session
            ClientMain.currentSession = newSession;

            // add user to the session
            ClientMain.currentSession.addUser(ClientMain.currentUser);

            // get available boot colors
            ClientMain.ACTION_MANAGER
                    .sendAction(new GetAvailableColorsAction(currentUser.getName(), newSession.getSessionID()));
        }

    }

}
