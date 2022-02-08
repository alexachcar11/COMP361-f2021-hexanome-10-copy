import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONArray;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import java.util.Map;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import unirest.shaded.com.google.gson.Gson;

/*
* Singleton
*/

public class Registrator {

    private JSONObject currentTokenJSON;
    private static final Registrator INSTANCE = new Registrator();
    private static final String encoded = Base64.getEncoder()
            .encodeToString(("bgp-client-name:bgp-client-pw").getBytes(StandardCharsets.UTF_8)); // Java 8
    private static final JSONParser parser = new JSONParser();

    private Registrator() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                try {
                    currentTokenJSON = createToken();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

        }, 0, 1790 * 1000);
        timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                try {
                    currentTokenJSON = refreshToken();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

        }, 590 * 1000, 590 * 1000);
    }

    public static Registrator instance() {
        return INSTANCE;
    }

    /**
     * Creates a new access token for an admin.
     * 
     * @throws ParseException
     */
    private JSONObject createToken() throws ParseException {

        HttpResponse<String> jsonResponse = Unirest
                .post("http://127.0.0.1:4242/oauth/token?grant_type=password&username=maex&password=abc123_ABC123")
                .header("Authorization", "Basic " + encoded)
                .asString();

        if (jsonResponse.getStatus() != 200) {
            System.err.println("Error" + jsonResponse.getStatus() + ": could not create access token.");
        }

        JSONObject token = (JSONObject) parser.parse(jsonResponse.getBody());

        System.out.println(token.toString());

        return token;
    }

    /**
     * Refreshes the access token and set currentAccessToken to this new token
     * 
     * @throws ParseException
     * @return JSONObject containing the body of a post request creating an access
     *         token
     */
    public JSONObject refreshToken() throws ParseException {

        HttpResponse<String> jsonResponse = Unirest
                .post("http://127.0.0.1:4242/oauth/token?grant_type=refresh_token&refresh_token="
                        + this.currentTokenJSON.get("refresh_token"))
                .header("Authorization", "Basic " + encoded)
                .asString();

        if (jsonResponse.getStatus() != 200) {
            System.err.println("Error" + jsonResponse.getStatus() + ": cannot refresh token.");
        }
        JSONObject token = (JSONObject) parser.parse(jsonResponse.getBody());
        return token;
    }

    /**
     * Revoke token
     */
    public static void revokeToken() {

    }

    /**
     * Takes currentTokenJSON and returns its string value
     * 
     * @return current token in String format
     */
    public String getToken() {
        return (String) this.currentTokenJSON.get("access_token");
    }

    public void createNewUser(String userName, String passWord) {
        /*
         * add a new user to the API
         */
        Map<String, Object> fields = new HashMap<>();
        fields.put("name", userName);
        fields.put("password", passWord);
        fields.put("preferredColour", "01FFFF");
        fields.put("role", "ROLE_PLAYER");

        String encoded = Base64.getEncoder()
                .encodeToString(("bgp-client-name:bgp-client-pw").getBytes(StandardCharsets.UTF_8)); // Java 8

        HttpResponse<String> jsonResponse = Unirest
                .put("http://127.0.0.1:4242/api/users/" + userName + "?access_token="
                        + this.getToken())
                .header("Content-Type", "application/json")
                .header("Authorization", "Basic " + encoded)
                .body(new Gson().toJson(fields)).asString();
        if (jsonResponse.getStatus() != 200) {
            System.out.println(jsonResponse.getStatus());
            throw new IllegalArgumentException("Cannot create user: " + userName);
        }
    }

    // get user from API by username
    public JSONObject getUser(String userName) throws ParseException {

        HttpResponse<String> jsonResponse = Unirest
                .get("http://127.0.0.1:4242/api/users/" + userName + "?access_token="
                        + this.getToken())
                .header("Authorization", "Basic " + encoded)
                .asString();
        if (jsonResponse.getStatus() != 200) {
            throw new IllegalArgumentException("Unable to retrieve user: " + userName);
        }

        return (JSONObject) parser.parse(jsonResponse.getBody().toString());
    }

    /*
     * This is the unirest JSONArray, may need to change to org.simple.json
     */
    public JSONArray getAllUsers() throws ParseException {

        HttpResponse<String> jsonResponse = Unirest
                .get("http://127.0.0.1:4242/api/users?access_token=" + this.getToken())
                .header("Authorization", "Basic " + encoded).asString();

        if (jsonResponse.getStatus() != 200) {
            System.err.println("Error: unable to retrieve users.");
        }

        JSONArray jsonArray = (JSONArray) parser.parse(jsonResponse.getBody());

        return jsonArray;
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
     * @param destinationTownEnabled true if players will have a destination town,
     *                               false otherwise
     */
    public void createNewGame(String displayName, int numberOfPlayers, int numberOfRounds, Mode mode,
            boolean witchEnabled, boolean destinationTownEnabled) throws ParseException {

        HttpResponse<String> jsonToken = Unirest
                .post("http://127.0.0.1:4242/oauth/token?grant_type=password&username=service&password=abc123_ABC123")
                .header("Authorization", "Basic " + encoded)
                .asString();

        JSONObject token = (JSONObject) parser.parse(jsonToken.getBody());

        String name = displayName.replace(" ", "");

        Map<String, Object> fields = new HashMap<>();
        fields.put("location", "http://127.0.0.1:4243" + name);
        fields.put("maxSessionPlayers", numberOfPlayers);
        fields.put("minSessionPlayers", numberOfPlayers);
        fields.put("name", name);
        fields.put("displayName", displayName);
        fields.put("webSupport", "false");

        System.out.println(INSTANCE.getToken());

        // lobby service location url
        String lobbyServiceURL = "http://127.0.0.1:4242/api/gameservices/" + name + "?access_token="
                + token.get("access_token");
        System.out.println(lobbyServiceURL);

        // build request
        HttpResponse<String> jsonResponse = Unirest
                .put(lobbyServiceURL)
                .header("Authorization", "Basic " + encoded) // when bearer:
                                                             // invalid access
                                                             // token. when
                                                             // basic: access
                // is denied
                .header("Content-Type", "application/json")
                .body(new Gson().toJson(fields)).asString();

        // verify response
        if (jsonResponse.getStatus() != 200) {
            System.err.println("Error" + jsonResponse.getStatus() + ": could not register game service");
            // send gameCreationConfirmed(Game null) to the User
            // gameCreationConfirmed(null);
        } else {
            // create a new Game object
            ServerGame newGame = new ServerGame(numberOfPlayers, numberOfRounds, destinationTownEnabled, witchEnabled,
                    mode);

            // send gameCreationConfirmed(Game newGameObject) to the User
            // gameCreationConfirmed(newGame);
        }
    }

    // public JSONObject getOauthRole() throws ParseException {
    // HttpResponse<String> jsonResponse = Unirest
    // .get("http://127.0.0.1:4242/oauth/username?access_token=" + this.getToken())
    // .asString();
    // if (jsonResponse.getStatus() != 200) {
    // System.err.println("Error" + jsonResponse.getStatus() + ": could not get
    // token role.");
    // throw new RuntimeException();
    // }
    // JSONParser parser = new JSONParser();
    // JSONObject role = (JSONObject) parser.parse(jsonResponse.getBody());
    // return role;
    // }
}
