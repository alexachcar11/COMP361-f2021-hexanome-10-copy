package networksrc;

import java.util.ArrayList;

import serversrc.GameLobby;
import serversrc.Mode;
import serversrc.ServerGame;
import serversrc.ServerUser;
import serversrc.TownGoldOption;

public class GetAvailableSessionsAction implements Action {

    private String senderName;

    public GetAvailableSessionsAction(String senderName) {
        this.senderName = senderName;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void execute() {
        // retrieve all available game lobbies
        ArrayList<GameLobby> availableLobbies = GameLobby.getAvailableLobbies();

        ArrayList<String> names = new ArrayList<>();
        ArrayList<Integer> numberPlayersCurrently = new ArrayList<>();
        ArrayList<Integer> numberPlayers = new ArrayList<>();
        ArrayList<Integer> numberRounds = new ArrayList<>();
        ArrayList<Boolean> destinationTownEnabled = new ArrayList<>();
        ArrayList<Boolean> witchEnabled = new ArrayList<>();
        ArrayList<String> mode = new ArrayList<>();
        ArrayList<String> townGoldOption = new ArrayList<>();
        ArrayList<String> creatorNames = new ArrayList<>();
        ArrayList<String> gameIDs = new ArrayList<>();

        for (GameLobby gl : availableLobbies) {
            // get relevant info
            ServerGame serverGame = gl.getServerGame();
            String name = gl.getName();
            int currentPlayers = gl.getNumberOfUsersCurrently();
            int totalPlayers = serverGame.getNumberOfPlayers();
            int nbRounds = serverGame.getGameRoundsLimit();
            Boolean destination = serverGame.isDestinationTownEnabled();
            Boolean witch = serverGame.isWitchEnabled();
            Mode modeSelected = serverGame.getMode();
            String modeString = null;
            if (modeSelected.equals(Mode.ELFENLAND)) {
                modeString = "elfenland";
            } else if (modeSelected.equals(Mode.ELFENGOLD)) {
                modeString = "elfengold";
            }
            TownGoldOption town = serverGame.getTownGoldOption();
            String townGold = null;
            if (town.equals(TownGoldOption.NO)) {
                townGold = "NO";
            } else if (town.equals(TownGoldOption.YESDEFAULT)) {
                townGold = "YES-DEFAULT";
            } else if (town.equals(TownGoldOption.YESRANDOM)) {
                townGold = "YES-RANDOM";
            }
            ServerUser creatorUser = gl.getCreator();
            String creatorName = creatorUser.getName();
            String gameID = gl.getGameID();

            // store info
            names.add(name);
            numberPlayersCurrently.add(currentPlayers);
            numberPlayers.add(totalPlayers);
            numberRounds.add(nbRounds);
            destinationTownEnabled.add(destination);
            witchEnabled.add(witch);
            mode.add(modeString);
            townGoldOption.add(townGold);
            creatorNames.add(creatorName);
            gameIDs.add(gameID);
        }

        // send to sender
        GetAvailableSessionsACK action = new GetAvailableSessionsACK(names, numberPlayersCurrently, numberPlayers,
                numberRounds, destinationTownEnabled, witchEnabled, mode, townGoldOption, creatorNames, gameIDs);
        ActionManager.getInstance().sendToSender(action, senderName);
    }

}
