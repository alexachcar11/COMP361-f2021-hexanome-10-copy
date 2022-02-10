/*
Represents one user from the moment the game is launched (they may not be a player yet)
 */

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class User {

    // FIELDS
    private String name;
    private JSONObject currentTokenJSON;
    private static final Registrator REGISTRATOR = Registrator.instance();

    // CONSTRUCTOR
    public User(String name, JSONObject token) {
        this.name = name;
        this.currentTokenJSON = token;
    }

    public String getName() {
        return name;
    }

    public String getToken() {
        return (String) this.currentTokenJSON.get("access_token");
    }

    // OPERATIONS


    /*
     * Operation: User::createNewGame(numberOfPlayers: int, numGameRounds: int,
     * mode: Mode, witchEnabled: boolean, destinationTownEnabled: boolean)
     * Scope: Game; User;
     * New: newGame: Game;
     * Messages: User:: {gameCreationFailed_e; gameCreationConfirmed}
     * Post: Sends a game creation confirmed message to the user upon success. In
     * case the game is not successfully created, the operation outputs an
     * “gameCreationFailed_e” message to the user.
     */
    public void createNewGame(String displayName, int numberOfPlayers, int numberOfRounds, Mode mode,
            boolean witchEnabled, boolean destinationTownEnabled) throws IOException, ParseException {
        // TODO: change this into a message once the network is working
        REGISTRATOR.createNewGame(displayName, numberOfPlayers, numberOfRounds, mode, witchEnabled,
                destinationTownEnabled);
    }

    /*
     * Operation: User::loadGame(savedGame: Game)
     * Scope: User; Player;
     * New: newSession: Session;
     * Messages: Player::{gameSessionCreationConfirmation;
     * gameSessionCreationFailed_e}
     * Post: Upon success, sends a confirmation message to the player that their
     * gameState has been saved. Otherwise, sends a “gameSessionCreationFailed_e”
     * message.
     */

    /*
     * TODO: add players to the game here
     * 
     * LILIA
     * 
     * // create players
     * List<Player> players = new ArrayList<>();
     * // Player p1 = new Player(null, Color.YELLOW);
     * // Player p2 = new Player(null, Color.BLACK);
     * // players.add(p1);
     * // players.add(p2);
     * 
     * Operation: User::joinGameSession(gameSession: Session)
     * Scope: User; Player; Session;
     * Messages: Player::{joinConfirmation, joinFailed_e}
     * Post: Upon success, sends the player a message to confirm they have joined a
     * game session successfully and moves the player to the game lobby. Otherwise,
     * sends a “joinFailed_e” message.
     */

    public void joinGameSession(LobbyServiceGameSession session) {
        // send a message to Server that this User wants to join the session
    }

}
