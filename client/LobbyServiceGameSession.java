import org.minueto.MinuetoColor;
import org.minueto.image.MinuetoFont;
import org.minueto.image.MinuetoRectangle;
import org.minueto.image.MinuetoText;

import java.util.ArrayList;

// represents one active game session
public class LobbyServiceGameSession {

    // fields
    boolean launched;
    ArrayList<String> players; //TODO: this will be ArrayList<Player> later
    int numberOfPlayersCurrently;
    String saveGameID;


    LobbyServiceGameSession(boolean launched, ArrayList<String> players, String saveGameID) {
        this.launched = launched;
        this.players = players;
        this.saveGameID = saveGameID;
        this.numberOfPlayersCurrently = players.size();
    }

    /**
     * Create all minueto objects that are needed to display information about the game session
     */
    public void buildDisplay() {



    }

}