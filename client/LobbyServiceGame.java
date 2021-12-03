/*
Instances of LobbyServiceGame represent one available game on the lobby service.
To construct this class, use the info given by the gameservices/{gameservice} json.
 */

public class LobbyServiceGame {

    // attributes
    private String displayName;
    private String location;
    private int numberOfPlayers;

    /**
     * CONSTRUCTOR : Creates a LobbyServiceGame object. Represents a single available game on the Lobby Service.
     * @param displayName displayName provided by gameservices/{gameservice}json
     * @param location location provided by gameservices/{gameservice} json
     * @param numberOfPlayers maxSessionPlayers provided by gameservices/{gameservice} json
     */
    public LobbyServiceGame(String displayName, String location, int numberOfPlayers) {
        this.displayName = displayName;
        this.location = location;
        this.numberOfPlayers = numberOfPlayers;
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
     * GETTER : Get the numberOfPlayers that can play this game.
     * @return numberOfPlayers
     */
    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }
}
