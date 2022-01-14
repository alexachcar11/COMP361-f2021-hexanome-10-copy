// API requests and parsing
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

// other
import java.util.ArrayList;
import java.util.Arrays;

public class ServerMain {
    /*
    Operation: Elfen::login(username: String, password: String)
    Scope: User;
    New: newUser: User
    Messages: User::{availableGames, invalidLogin_e}
    Post: If the login is successful, sends the user all available games. Otherwise, sends the user a “invalidLogin_e” message to inform them that the login has failed.
     */

    /*
    Operation: Elfen::createNewGame(numberOfPlayers: int, numGameRounds: int, mode: Mode, witchEnabled: boolean, destinationTownEnabled: boolean)
    Scope: Game;
    New: newGame: Game;
    Messages: User:: {gameCreationFailed_e; newGameState}
    Post: Sends a new game state to the user upon success. in case the game is not successfully created, the operation outputs an “gameCreationFailed_e” message to the user.
     */

    /*
    Operation: Elfen::gameCreationConfirmed(game: Game)
    Scope: Game; User;
    New: newGame: Game;
    Messages: User:: {gameCreationFailed_e; gameCreationConfirmed}
    Post: Sends a game creation confirmed message to the user upon success. In case the game is not successfully created, the operation outputs an “gameCreationFailed_e” message to the user.
     */


    /**
     * Operation: Elfen::availableGames(availableGames: ArrayList{Game})
     * Scope: User; Game;
     * Messages: User::{availableGames, invalidLogin_e}
     * Post: If the login is successful, sends the user all available games. Otherwise, sends the user a “invalidLogin_e” message to inform them that the login has failed.
     * @param availableGames ArrayList of LobbyServiceGameSession objects to send to the User
     */
    public static void availableGames(ArrayList<LobbyServiceGameSession> availableGames) throws IOException, ParseException {
        // TODO for owen : If the login is successful, sends the user all available games. Otherwise, sends the user a “invalidLogin_e” message to inform them that the login has failed.
        // if login unsuccessful: send invalidLogin_e to User
        // elif login successful: send availableGames to the User
        ArrayList<LobbyServiceGameSession> availableSessionsList = getAvailableSessions();
        ArrayList<LobbyServiceGame> availableGamesList = getAvailableGames();

        // TODO: send these lists to the user (Arraylist is serializable)
    }

    /**
     * Helper function for availableGames(). It gets and returns all available game session on the Lobby Service
     * @return ArrayList<LobbyServiceGameSession> representing all available sessions on the Lobby Service
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
     * Helper function for availableGames(). Returns an arraylist of LobbyServiceGame objects available on the LobbyService
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
     * Helper function for getAvailableGames(). Returns details on a previously registered Game-Service.
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
