/*
Instances of LobbyServiceGame represent one available game service on the lobby service.
 */

public class LobbyServiceGame implements Joinable{

    // FIELDS
    private String name;
    private String displayName;
    private String location;
    private final int numberOfUsers;
    private LobbyServiceGameSession activeSession;

    /**
     * CONSTRUCTOR : Creates a LobbyServiceGame object. Represents a single available game on the Lobby Service.
     * @param displayName displayName provided by gameservices/{gameservice}json
     * @param location location provided by gameservices/{gameservice} json
     * @param numberOfUsers maxSessionPlayers provided by gameservices/{gameservice} json
     */
    public LobbyServiceGame(String name, String displayName, String location, int numberOfUsers) {
        this.name = name;
        this.displayName = displayName;
        this.location = location;
        this.numberOfUsers = numberOfUsers;
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

    /**
     * The Registrator will make this user join this game service by creating a session.
     */
    @Override
    public void join() throws Exception {
        LobbyServiceGameSession newSessionCreated = Registrator.instance().createGameSession(this, ClientMain.currentUser, "");
        this.activeSession = newSessionCreated;
    }
}
