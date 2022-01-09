import java.util.ArrayList;

// represents one active game session
public class LobbyServiceGameSession {

    // fields
    boolean launched;
    ArrayList<Player> players;
    String saveGameID;


    LobbyServiceGameSession(boolean launched, ArrayList<Player> players, String saveGameID) {
        this.launched = launched;
        this.players = players;
        this.saveGameID = saveGameID;
    }



}