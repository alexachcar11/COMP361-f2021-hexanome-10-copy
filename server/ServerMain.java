
// API requests and parsing
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import unirest.shaded.com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

// other
import java.util.*;

public class ServerMain {

    public static void main(String[] args) {

    }

    /**
     * Operation: Elfen::login(username: String, password: String)
     * Scope: User;
     * New: newUser: User
     * Messages: User::{availableGames, invalidLogin_e}
     * Post: If the login is successful, sends the user all available games.
     * Otherwise, sends the user a “invalidLogin_e” message to inform them that the
     * login has failed.
     * 
     * @param username username input by the User
     * @param password password input by the User
     * @throws IOException
     * @throws ParseException
     */
    // TODO for owen : check for valid login here
    public static void login(String username, String password) throws IOException, ParseException {
        // if successful: use the availableGames operation to send games to the User (+
        // create a new User object?)
        availableGames();
        // if unsuccessful: send invalidLogin_e to the User
    }

    /**
     * Operation: Elfen::gameCreationConfirmed(game: Game)
     * Scope: Game; User;
     * Messages: User:: {gameCreationFailed_e; newGameState}
     * Post: Sends a game creation confirmed message to the user upon success. In
     * case the game is not successfully created, the operation outputs an
     * “gameCreationFailed_e” message to the user.
     * 
     * @param game Game object that was created
     */
    public static void gameCreationConfirmed(ServerGame game) {
        if (game == null) {
            Exception exception = new Exception("game creation failed");
            // send exception to User (Exception is Serializable)
        } else {
            // send newGameState to the User
        }
    }

    /**
     * Note: Lilia changed this operation because it didn't make sense
     * Operation: Elfen::availableGames(availableGames: ArrayList{Game})
     * Scope: User; Game;
     * Messages: User::{availableGames, invalidLogin_e}
     * Post: Sends the user all available games
     */
    public static void availableGames() throws IOException, ParseException {
        ArrayList<LobbyServiceGame> availableGames = Registrator.instance().getAvailableGames();
        ArrayList<LobbyServiceGameSession> availableSessions = Registrator.instance().getAvailableSessions();
        // TODO: send availableGames to the user (Arraylist is serializable)
    }

}
