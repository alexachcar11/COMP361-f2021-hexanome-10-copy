/*
Instances of LobbyServiceGame represent one available game service on the lobby service.
 */

import org.minueto.MinuetoColor;
import org.minueto.image.MinuetoFont;
import org.minueto.image.MinuetoRectangle;
import org.minueto.image.MinuetoText;

import java.util.ArrayList;

public class LobbyServiceGame implements Joinable{

    // attributes
    private String name;
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
    public LobbyServiceGame(String name, String displayName, String location, int maxNumberOfPlayers) {
        this.name = name;
        this.displayName = displayName;
        this.location = location;
        this.maxNumberOfPlayers = maxNumberOfPlayers;
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
     * GETTER : Get the max number of players that can play this game.
     * @return maxNumberOfPlayers
     */
    public int getMaxNumberOfPlayers() {
        return maxNumberOfPlayers;
    }

    /**
     * The Registrator will make this user join this game service
     */
    @Override
    public void join() {
        try {
            LobbyServiceGameSession newSessionCreated = Registrator.instance().createGameSession(this, ClientMain.currentUser, "");
            this.activeSession = newSessionCreated;
            Registrator.instance().joinGame(newSessionCreated);
        } catch (Exception e){
            System.err.println("Unable to join game");
        }


    }
}
