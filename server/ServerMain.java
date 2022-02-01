
// API requests and parsing
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import kong.unirest.Unirest;
import kong.unirest.HttpResponse;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
// other
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

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
     * Operation: Elfen::createNewGame(numberOfPlayers: int, numGameRounds: int,
     * mode: Mode, witchEnabled: boolean, destinationTownEnabled: boolean)
     * Scope: Game;
     * New: newGame: Game;
     * Messages: User:: {gameCreationFailed_e; newGameState}
     * Post: Sends a new game state to the user upon success. in case the game is
     * not successfully created, the operation outputs an “gameCreationFailed_e”
     * message to the user.
     * 
     * @param displayName            name of the game
     * @param numberOfPlayers        exact number of players required to play this
     *                               game. it must be between 2 and 6
     * @param numberOfRounds         number of rounds this game will have
     * @param mode                   elfenland or elfengold
     * @param witchEnabled           true if the witch can be used, false otherwise
     * @param destinationTownEnabled true if players will have a destionation town,
     *                               false otherwise
     */
    public static void createNewGame(String displayName, int numberOfPlayers, int numberOfRounds, Mode mode,
            boolean witchEnabled, boolean destinationTownEnabled) throws IOException {

        String encoded = Base64.getEncoder()
                .encodeToString(("bgp-client-name:bgp-client-pw").getBytes(StandardCharsets.UTF_8)); // Java 8

        String name = displayName.replace(" ", "");

        Map<String, Object> fields = new HashMap<>();
        fields.put("location", "http://127.0.0.1:4243" + name);
        fields.put("maxSessionPlayers", numberOfPlayers);
        fields.put("minSessionPlayers", numberOfPlayers);
        fields.put("name", displayName);

        HttpResponse<String> jsonResponse = Unirest
                .put("http://127.0.0.1:4242/api/gameservices/" + name + "?access_token="
                        + ClientMain.token.get("access_token"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Basic " + encoded)
                .fields(fields).asString();
        if (jsonResponse.getStatus() != 200) {
            System.err.println("Error: could not register game service");
        }
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
        ArrayList<LobbyServiceGame> availableGames = getAvailableGames();
        ArrayList<LobbyServiceGameSession> availableSessions = getAvailableSessions();
        // TODO: send availableGames to the user (Arraylist is serializable)
    }

    /**
     * Helper function for availableGames(). It gets and returns all available game
     * session on the Lobby Service
     * 
     * @return ArrayList<LobbyServiceGameSession> representing all available
     *         sessions on the Lobby Service
     * @throws IOException
     * @throws ParseException
     */
    public static ArrayList<LobbyServiceGameSession> getAvailableSessions() throws IOException, ParseException {
        URL url = new URL("http://127.0.0.1:4242/api/sessions");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        int status = con.getResponseCode();
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        con.disconnect();

        // TESTING CODE START
        System.out.println("Response status: " + status);
        System.out.println(content.toString());
        // TESTING CODE END

        // convert GET output to JSON
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(String.valueOf(content));
        JSONObject jsonObject = (JSONObject) obj;

        ArrayList<LobbyServiceGameSession> availableSessions = new ArrayList<>();

        try {
            // i dont know if this works because I have no way to test it yet (can't create
            // a game)
            JSONObject sessions = (JSONObject) jsonObject.get("sessions");
            sessions.keySet().forEach(sessionID -> {
                JSONObject sessionJSON = (JSONObject) sessions.get(sessionID);

                String creator = (String) sessionJSON.get("creator");
                boolean launched = (boolean) sessionJSON.get("launched");
                String saveGameID = (String) sessionJSON.get("savegameid");

                // Object gameParameters = sessionJSON.get("gameParameters");

                String playerListInStringForm = (String) sessionJSON.get("players");
                playerListInStringForm = playerListInStringForm.replace("[", "");
                playerListInStringForm = playerListInStringForm.replace("]", "");
                String[] playerListInArrayForm = playerListInStringForm.split(",");
                ArrayList<String> playerNames = (ArrayList<String>) Arrays.asList(playerListInArrayForm);

                availableSessions.add(new LobbyServiceGameSession(launched, playerNames, saveGameID));

            });
        } catch (NullPointerException e) {
            // there are no available sessions
            System.out.println("there are no sessions");
        }
        return availableSessions;
    }

    /**
     * Helper function for availableGames(). Returns an arraylist of
     * LobbyServiceGame objects available on the LobbyService
     * 
     * @return arrayList of LobbyServiceGame objects
     * @throws IOException
     * @throws ParseException
     */
    public static ArrayList<LobbyServiceGame> getAvailableGames() throws IOException, ParseException {
        URL url = new URL("http://127.0.0.1:4242/api/gameservices");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        int status = con.getResponseCode();
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        con.disconnect();

        // TESTING CODE START
        System.out.println("Response status: " + status);
        System.out.println(content.toString());
        // TESTING CODE END

        // convert GET output to JSON
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(String.valueOf(content));
        JSONArray jsonArray = (JSONArray) obj;

        // make a list of available games
        ArrayList<LobbyServiceGame> availableGames = new ArrayList<>(jsonArray.size());

        // add unloaded games (i.e. LobbyServiceGame)
        for (Object lsGameJson : jsonArray) {
            JSONObject lsGame = (JSONObject) lsGameJson;

            // get game name
            String name = (String) lsGame.get("name");

            // get game-service json
            JSONObject gameJson = getLSGameInfo(name);

            // get game-service info from the json
            String displayName = (String) gameJson.get("displayName");
            String location = (String) gameJson.get("location");
            int numberOfPlayers = (int) gameJson.get("numberOfPlayers");

            // create LobbyServiceGame and add it to availableGames list
            availableGames.add(new LobbyServiceGame(displayName, location, numberOfPlayers));
        }

        return availableGames;
    }

    /**
     * Helper function for getAvailableGames(). Returns details on a previously
     * registered Game-Service.
     * 
     * @return JSONObject representing the game-service
     * @throws IOException
     */
    public static JSONObject getLSGameInfo(String name) throws IOException, ParseException {
        URL url = new URL("curl -X GET http://127.0.0.1:4242/api/gameservices/" + name);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        int status = con.getResponseCode();
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        con.disconnect();

        // TESTING CODE START
        System.out.println("Response status: " + status);
        System.out.println(content.toString());
        // TESTING CODE END

        // convert GET output to JSON
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(String.valueOf(content));

        return (JSONObject) obj;
    }

}
