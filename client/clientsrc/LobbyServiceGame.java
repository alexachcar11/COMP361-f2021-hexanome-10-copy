/*
Instances of LobbyServiceGame represent one available game service on the lobby service.
 */

package clientsrc;

import java.util.ArrayList;

import networksrc.PlayerHasJoinedAction;

// import serversrc.Mode;
// import serversrc.TownGoldOption;

public class LobbyServiceGame implements Joinable{

    // FIELDS
    // lobby service relate
    private String name;
    private String displayName;
    private String location;
    private final int numberOfUsers;
    private LobbyServiceGameSession activeSession;
    private static ArrayList<LobbyServiceGame> allGameServices = new ArrayList<>();
    private Game game;

    /**
     * FULL CONSTRUCTOR : Creates a LobbyServiceGame object. Represents a single available game on the Lobby Service that you create.
     * @param displayName displayName provided by gameservices/{gameservice}json
     * @param location location provided by gameservices/{gameservice} json
     * @param numberOfUsers maxSessionPlayers provided by gameservices/{gameservice} json
     */
    public LobbyServiceGame(String name, String displayName, String location, int numberOfUsers, Game game) {
        this.name = name;
        this.displayName = displayName;
        this.location = location;
        this.numberOfUsers = numberOfUsers;
        this.activeSession = null;
        allGameServices.add(this);
        this.game = game;
    }

    // SIMPLE CONSTRUCTOR FOR WHEN YOU RETRIEVE AN EXISTING GAME
    public LobbyServiceGame(String name, String displayName, String location, int numberOfUsers) {
        this.name = name;
        this.displayName = displayName;
        this.location = location;
        this.numberOfUsers = numberOfUsers;
        allGameServices.add(this);
    }

    /**
     * Given a name, searches through all LobbyServiceGame. If a game has the same name, return it. Else return null.
     * @param name game name on the LS
     * @return LobbyServiceGame or null
     */
    public static LobbyServiceGame getLobbyServiceGame(String name) {
        for (LobbyServiceGame g : allGameServices) {
            if (g.name.equals(name)) {
                // already exists
                return g;
            }
        }
        // we reach this line when the game does not exist
        return null;
    }

    /**
     * GETTER: Get the name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * GETTER : Get the displayName
     * @return displayName
     */
    public String getDisplayName() {
        return displayName;
    }

    /**
     * GETTER : Get the location url of the game as a String.
     * @return location
     */
    public String getLocation() {
        return location;
    }

    /**
     * GETTER : Get the number of users that can play this game.
     * @return numberOfUsers
     */
    public int getNumberOfUsers() {
        return numberOfUsers;
    }

    public LobbyServiceGameSession getActiveSession() {
        return  activeSession;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        if (this.game == null) {
            this.game = game;
        } else {
            throw new IllegalStateException("The LobbyServiceGame's Game is already set.");
        }
    }

    /**
     * Returns the current available session
     * @param session LobbyServiceGameSession or null
     * @throws Exception when an activeSession already exists for this game service
     */
    public void setActiveSession(LobbyServiceGameSession session) throws Exception {
        if (this.activeSession == null) {
            this.activeSession = session;
        } else {
            throw new Exception("Could not setActiveSession because activeSession is not null.");
        }
    }

    /**
     * The Registrator will make this user join this game service by creating a session.
     */
    @Override
    public LobbyServiceGameSession join() throws Exception {
        LobbyServiceGameSession newSessionCreated = Registrator.instance().createGameSession(this, ClientMain.currentUser, "");
        this.activeSession = newSessionCreated;
        ClientMain.currentSession = newSessionCreated;
        ClientMain.ACTION_MANAGER.sendActionAndGetReply(new PlayerHasJoinedAction(ClientMain.currentUser.getName(), newSessionCreated.getSessionID()));
        return newSessionCreated;
    }
}
