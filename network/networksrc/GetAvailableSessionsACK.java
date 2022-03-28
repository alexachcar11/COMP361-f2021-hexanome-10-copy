package networksrc;

import java.util.ArrayList;

import clientsrc.Game;
import clientsrc.LobbyServiceGameSession;
import clientsrc.User;
import clientsrc.Mode;
import clientsrc.TownGoldOption;

public class GetAvailableSessionsACK implements Action {

    private ArrayList<String> names;
    private ArrayList<Integer> numberPlayersCurrently;
    private ArrayList<Integer> numberPlayers;
    private ArrayList<Integer> numberRounds;
    private ArrayList<Boolean> destinationTownEnabled;
    private ArrayList<Boolean> witchEnabled;
    private ArrayList<String> mode;
    private ArrayList<String> townGoldOption;
    private ArrayList<String> creatorNames;
    private ArrayList<String> gameIDs;

    public GetAvailableSessionsACK(ArrayList<String> names, ArrayList<Integer> numberPlayersCurrently, ArrayList<Integer> numberPlayers, ArrayList<Integer> numberRounds,
            ArrayList<Boolean> destinationTownEnabled, ArrayList<Boolean> witchEnabled, ArrayList<String> mode, ArrayList<String> townGoldOption,
            ArrayList<String> creatorNames, ArrayList<String> gameIDs) {
        this.names = names;
        this.numberPlayersCurrently = numberPlayersCurrently;
        this.numberPlayers = numberPlayers;
        this.numberRounds = numberRounds;
        this.destinationTownEnabled = destinationTownEnabled;
        this.witchEnabled = witchEnabled;
        this.mode = mode;
        this.townGoldOption = townGoldOption;
        this.creatorNames = creatorNames;
        this.gameIDs = gameIDs;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void execute() {
        for (int i=0; i<names.size(); i++) {
            LobbyServiceGameSession ls = LobbyServiceGameSession.getSessionByName(names.get(i));
            if (ls == null) {
                // game does not exist on the client yet

                // retrieve info
                String name = names.get(i);
                int currentNbPlayers = numberPlayersCurrently.get(i);
                int nbPlayers = numberPlayers.get(i);
                int nbRounds = numberRounds.get(i);
                Boolean destination = destinationTownEnabled.get(i);
                Boolean witch = witchEnabled.get(i);
                Mode modeInfo = null;
                if (mode.get(i).equals("elfenland")) {
                    modeInfo = Mode.ELFENLAND;
                } else if (mode.get(i).equals("elfengold")) {
                    modeInfo = Mode.ELFENGOLD;
                }
                TownGoldOption townInfo = null;
                if (townGoldOption.get(i).equals("NO")) {
                    townInfo = TownGoldOption.NO;
                } else if (townGoldOption.get(i).equals("YES-DEFAULT")) {
                    townInfo = TownGoldOption.YESDEFAULT;
                } else if (townGoldOption.get(i).equals("YES-RANDOM")) {
                    townInfo = TownGoldOption.YESRANDOM;
                }
                String creatorName = creatorNames.get(i);
                String gameID = gameIDs.get(i);

                // create a new Game object
                Game newGame = new Game(nbPlayers, nbRounds, destination, witch, modeInfo, townInfo);

                // make a User for the creator
                User creator = new User(creatorName);
            
                // create new LobbyServiceGameSession
                LobbyServiceGameSession newSession = new LobbyServiceGameSession("", newGame, creator, gameID, name);
                newSession.addUser(creator);
                newSession.setCurrentNumberOfPlayers(currentNbPlayers);
            } else {
                // game already exists on the client
                int currentNbPlayers = numberPlayersCurrently.get(i);
                ls.setCurrentNumberOfPlayers(currentNbPlayers);
            }
        }
    }
    
}
