/*
Instances of LobbyServiceGame represent one available game service on the lobby service.
 */

import org.minueto.MinuetoColor;
import org.minueto.image.MinuetoFont;
import org.minueto.image.MinuetoRectangle;
import org.minueto.image.MinuetoText;

import java.util.ArrayList;

public class LobbyServiceGame {

    // attributes
    private String displayName;
    private String location;
    private final int maxNumberOfPlayers;
    private LobbyServiceGameSession activeSession;

    /**
     * CONSTRUCTOR : Creates a LobbyServiceGame object. Represents a single available game on the Lobby Service.
     * @param displayName displayName provided by gameservices/{gameservice}json
     * @param location location provided by gameservices/{gameservice} json
     * @param maxNumberOfPlayers maxSessionPlayers provided by gameservices/{gameservice} json
     */
    public LobbyServiceGame(String displayName, String location, int maxNumberOfPlayers) {
        this.displayName = displayName;
        this.location = location;
        this.maxNumberOfPlayers = maxNumberOfPlayers;
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
     * GETTER : Get the max number of players that can play this game.
     * @return maxNumberOfPlayers
     */
    public int getMaxNumberOfPlayers() {
        return maxNumberOfPlayers;
    }


    /**
     * Create a new session for this game service.
     * @param user user that initiates this operation
     * @param saveGameID id of the previously saved game. If there is none, put "".
     * @return the newly created LobbyServiceGameSession
     * @post The user that initiates this operation becomes the creator of the session and is one of the players.
     */
    public LobbyServiceGameSession createSessionFromGameService(User user, String saveGameID) {
        LobbyServiceGameSession newSession = new LobbyServiceGameSession(false, "", user.getName(), this);
        this.activeSession = newSession;
        return newSession;
    }



}
