/*
Instances of LobbyServiceGame represent one available game on the lobby service.
 */

import org.minueto.MinuetoColor;
import org.minueto.image.MinuetoFont;
import org.minueto.image.MinuetoRectangle;
import org.minueto.image.MinuetoText;

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
     *  Create all minueto objects that are needed to display information about the game
     */
    public void buildDisplay() {


        // background
        MinuetoRectangle rectangle = new MinuetoRectangle(50, 1000, MinuetoColor.WHITE, true);

        // display name
        MinuetoFont fontArial14 = new MinuetoFont("Arial",14,false, false);
        MinuetoText name = new MinuetoText(displayName,fontArial14,MinuetoColor.BLACK);

        // I think when a LobbyServiceGame has an active session, then we should use activeSession.buildDisplay() instead.
        // They are different because Session has a number of current players shown but Game does not


    }



}
