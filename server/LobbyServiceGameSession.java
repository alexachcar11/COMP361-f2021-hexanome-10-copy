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

    /*
    Operation: Session::startGame(gameSession: Session)
    Scope: Player; Session;
    Messages: Player::{gameStartConfirmation; gameStartFailed_e}
    Post: Upon success, sends the player a message to confirm that the game has started successfully and moves all players to the game screen. Otherwise, sends a “gameStartFailed_e” message.
    */

    /*
    Operation: Session::quitGameSession()
    Scope: Player; Session;
    Messages: Player::{quitConfirmation; quitFailed_e}
    Post: Upon success, sends the player a message to confirm they have quit successfully and returns the player back to the lobby. Otherwise, sends a “quitFailed_e” message.
    */

    /*
    Operation: Session::saveGameSession()
    Scope: Player; Session; Game;
    Messages: Player::{saveConfirmation}
    Post: Sends a message to the player to notify the player that the session is saved.
     */

    /*
    Operation: Session::playerJoined(gameSession: Session, player: Player)
    Scope: User; Player; Session;
    Messages: Player::{playerJoined}
    Post: Upon success, sends the player a message to confirm another player has joined a game session successfully.
     */
    public void playerJoined(LobbyServiceGameSession sessionJoined, Player playerThatJoined) {

    }

    /*
    Operation: Session::joinConfirmation(gameSession: Session)
    Scope: User; Player; Session;
    Messages: Player::{joinConfirmation, joinFailed_e}
    Post: Upon success, sends the player a message to confirm they have joined a game session successfully and moves the player to the game lobby. Otherwise, sends a “joinFailed_e” message.
    */
    public void joinConfirmation(LobbyServiceGameSession session) {
        // send a message to the User that the session was joined succesfully or not

    }

    public void joinGame(LobbyServiceGameSession session) {
        // helper function that will create a Player and make them join a session
    }

    /*
    Operation: Session::gameStartConfirmation(gameSession: Session, gameBoard: Board)
    Scope: Player; Session;
    Messages: Player::{gameStartConfirmation; gameStartFailed_e}
    Post: Upon success, sends the player a message to confirm that the game has started successfully and moves all players to the game screen. Otherwise, sends a “gameStartFailed_e” message.

    Operation: Session::gameSessionCreationConfirmation(gameSession: Session)
    Scope: User;  Player;
    New: newSession: Session;
    Messages: Player::{gameSessionCreationConfirmation; gameSessionCreationFailed_e}
    Post: Upon success, sends a confirmation message to the player that their gameState has been saved. Otherwise, sends a “gameSessionCreationFailed_e” message.

    Operation: Session::playerQuit(gameSession: Session, player: Player)
    Scope: Player; Session;
    Messages: Player::{playerQuit}
    Post: Upon success, sends the player a message to confirm another player has quit successfully.

    Operation: Session::quitConfirmation(gameSession: Session)
    Scope: Player; Session;
    Messages: Player::{quitConfirmation; quitFailed_e}
    Post: Upon success, sends the player a message to confirm they have quit successfully and returns the player back to the lobby. Otherwise, sends a “quitFailed_e” message.

    Operation: Session::saveConfirmation(gameSession: Session)
    Scope: Session; Game; Player;
    Messages: Player::{saveConfirmation}
    Post: Sends a message to the player to notify the player that the session is saved.

     */


}