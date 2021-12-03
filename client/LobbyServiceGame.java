/*
Instances of LobbyServiceGame represent one available game on the lobby service.
To construct this class, use the info given by the gameservices/{gameservice} json.
 */

import org.minueto.MinuetoColor;
import org.minueto.image.MinuetoFont;
import org.minueto.image.MinuetoRectangle;
import org.minueto.image.MinuetoText;

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

    /**
     * Display information about the game
     */
    public void buildDisplay() {
        // TODO: fix the sizing and layout
        MinuetoRectangle rectangle = new MinuetoRectangle(50, 1000, MinuetoColor.WHITE, true);
        MinuetoFont fontArial14 = new MinuetoFont("Arial",14,false, false);
        MinuetoText name = new MinuetoText(displayName,fontArial14,MinuetoColor.BLACK);

    }

}
