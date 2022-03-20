package serversrc;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import networksrc.CreateNewGameAction;
import networksrc.GetGameInfoACK;
import networksrc.GetGameInfoAction;
import networksrc.LaunchGameAction;
import unirest.shaded.com.google.gson.Gson;

public class Registrator {

    private static final Registrator INSTANCE = new Registrator();  // SINGLETON

    private JSONObject currentToken;    // registrator's token

    private static final String encoded = Base64.getEncoder()
            .encodeToString(("bgp-client-name:bgp-client-pw").getBytes(StandardCharsets.UTF_8)); // Java 8
    private static final JSONParser parser = new JSONParser();
    private static final Gson GSON = new Gson();

    /**
     * CONSTRUCTOR
     * Creates a new token and refreshes it at intervals of time
     * THIS IS FOR AN ADMIN USER
     */
    private Registrator() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                try {
                    currentToken = createToken("maex", "abc123_ABC123");
                } catch (ParseException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }

        }, 0, 1790 * 1000);
        timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                try {
                    currentToken = refreshToken();
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
     * Creates an access token for an admin
     * @param username username of the admin
     * @param password password of the admin
     * @return new token for the admin (String format)
     * @throws ParseException
     * @throws IllegalAccessException
     */
    public JSONObject createToken(String username, String password) throws ParseException, IllegalAccessException {

        HttpResponse<String> jsonResponse = Unirest
                .post("http://127.0.0.1:4242/oauth/token?grant_type=password&username="
                        + username
                        + "&password=" + password)
                .header("Authorization", "Basic " + encoded)
                .asString();

        if (jsonResponse.getStatus() != 200) {
            System.err.println(jsonResponse.getBody());
            throw new IllegalAccessException("Error" + jsonResponse.getStatus() + ": could not create access token.");
        }

        JSONObject token = (JSONObject) parser.parse(jsonResponse.getBody());
        return token;
    }

    /**
     * Refreshes the access token and set currentAccessToken to this new token
     * 
     * @throws ParseException
     * @return Refreshed token in String format
     */
    public JSONObject refreshToken() throws ParseException {
        String refreshToken = (String) currentToken.get("refresh_token");
        refreshToken = refreshToken.replace("+", "%2B");

        HttpResponse<String> jsonResponse = Unirest
                .post("http://127.0.0.1:4242/oauth/token?grant_type=refresh_token&refresh_token="
                        + refreshToken)
                .header("Authorization", "Basic " + encoded)
                .asString();

        if (jsonResponse.getStatus() != 200) {
            System.err.println(jsonResponse.getBody());
            System.err.println("Error" + jsonResponse.getStatus() + ": cannot refresh token.");
        }

        JSONObject token = (JSONObject) parser.parse(jsonResponse.getBody());
        return token;
    }

    /**
     * Revoke token
     */
    public static void revokeToken() {
        // TODO
    }

    /**
     * Retrieve registrator's currenToken
     * @return current token in String format
     */
    public String getToken() {
        String token = (String) currentToken.get("access_token");
        token = token.replace("+", "%2B");
        return token;
    }

    /**
     * Retrieves a user by their username on LS
     * @param userName
     * @return JSONObject of the user info: name, password, preferredColour, role
     * @throws ParseException
     */
    public JSONObject getUser(String userName) throws ParseException {

        userName = userName.replace("+", "%2B");

        HttpResponse<String> jsonResponse = Unirest
                .get("http://127.0.0.1:4242/api/users/" + userName + "?access_token="
                        + this.getToken().replace("+", "%2B"))
                .header("Authorization", "Basic " + encoded)
                .asString();
        if (jsonResponse.getStatus() != 200) {
            System.err.println(jsonResponse.getBody());
            throw new IllegalArgumentException("Unable to retrieve user: " + userName);
        }

        return (JSONObject) parser.parse(jsonResponse.getBody());
    }

    /**
     * Retrieves the list of existing users on the LS
     * @return JSONArray of JSONObjects: name, password, preferredColour, role
     * @throws ParseException
     */
    public JSONArray getAllUsers() throws ParseException {

        HttpResponse<String> jsonResponse = Unirest
                .get("http://127.0.0.1:4242/api/users?access_token=" + this.getToken().replace("+", "%2B"))
                .header("Authorization", "Basic " + encoded).asString();

        if (jsonResponse.getStatus() != 200) {
            System.err.println(jsonResponse.getBody());
            throw new RuntimeException("Error" + jsonResponse.getStatus() + ": unable to retrieve users.");
        }

        return (JSONArray) parser.parse(jsonResponse.getBody());
    }

    /**
     * Returns true if the username exists in LS, false otherwise.
     * @param username username to search
     * @return true when the user exists already
     */
    public boolean userExists(String username) {
        boolean userFound = false;
        try {
            for (Object user : this.getAllUsers()) {
                JSONObject userJson = (JSONObject) user;
                if (userJson.get("name").equals(username)) {
                    userFound = true;
                    break;
                }
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return userFound;
    }

    /**
     * Create a new user on the LS
     * @param userName username of the user
     * @param passWord password of the user
     * @return User instance that is created upon API request success
     */
    public void createNewUser(String userName, String passWord) {
        Map<String, Object> fields = new HashMap<>();
        fields.put("name", userName);
        fields.put("password", passWord);
        fields.put("preferredColour", "01FFFF");
        fields.put("role", "ROLE_PLAYER");

        String encoded = Base64.getEncoder()
                .encodeToString(("bgp-client-name:bgp-client-pw").getBytes(StandardCharsets.UTF_8)); // Java 8

        userName = userName.replace("+", "%2B");
        passWord = passWord.replace("+", "%2B");

        HttpResponse<String> jsonResponse = Unirest
                .put("http://127.0.0.1:4242/api/users/" + userName + "?access_token="
                        + this.getToken().replace("+", "%2B"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Basic " + encoded)
                .body(GSON.toJson(fields)).asString();

        if (jsonResponse.getStatus() != 200) {
            System.out.println(jsonResponse.getStatus() + jsonResponse.getBody());
            throw new IllegalArgumentException("Cannot create user: " + userName);
        } else {
            
        }
    }

    public String createGame(ServerUser creator, String displayName, int numberOfPlayers, int numberOfRounds, Mode mode,
                                       boolean witchEnabled, boolean destinationTownEnabled, TownGoldOption townGoldOption) throws ParseException {

        HttpResponse<String> jsonToken = Unirest
                .post("http://127.0.0.1:4242/oauth/token?grant_type=password&username=service&password=abc123_ABC123")
                .header("Authorization", "Basic " + encoded)
                .asString();

        JSONObject token = (JSONObject) parser.parse(jsonToken.getBody());

        String name = displayName.replace(" ", "");
        String location = "http://127.0.0.1:4243/" + name;

        Map<String, Object> fields = new HashMap<>();
        fields.put("location", location);
        fields.put("maxSessionPlayers", numberOfPlayers);
        fields.put("minSessionPlayers", numberOfPlayers);
        fields.put("name", name);
        fields.put("displayName", displayName);
        fields.put("webSupport", "false");

        String tokenString = (String) token.get("access_token");
        tokenString = tokenString.replace("+", "%2B");

        // lobby service location url
        String lobbyServiceURL = "http://127.0.0.1:4242/api/gameservices/" + name + "?access_token="
                + tokenString;

        // build request
        HttpResponse<String> jsonResponse = Unirest
                .put(lobbyServiceURL)
                .header("Authorization", "Basic " + encoded)
                .header("Content-Type", "application/json")
                .body(GSON.toJson(fields)).asString();

        String gameID = null;

        // verify response
        if (jsonResponse.getStatus() == 400) {
            System.err.println(jsonResponse.getStatus() + ": " + jsonResponse.getBody());
        } else if (jsonResponse.getStatus() != 200) {
            System.err.println(jsonResponse.getStatus() + ": " + jsonResponse.getBody());
        } else {
            // success -> create a game session
            gameID = this.createGameSession(name, creator, "");
        }
        return gameID;
    }

    public String createGameSession(String name, ServerUser creator, String saveGameID) {
        // API request
        Map<String, Object> fields = new HashMap<>();
        fields.put("creator", creator.getName());
        fields.put("game", name);
        fields.put("savegame", saveGameID);

        // user token
        String token = creator.getToken().replace("+", "%2B");
        System.out.println(token);

        // build request
        HttpResponse<String> jsonResponse = Unirest
                .post("http://127.0.0.1:4242/api/sessions/?access_token="
                        + token)
                .header("Content-Type", "application/json")
                .body(new Gson().toJson(fields)).asString();


        String id = null;

        // verify response
        if (jsonResponse.getStatus() != 200) {
            System.err.println("Error" + jsonResponse.getStatus() + ": " + jsonResponse.getBody());
        } else {
            // success !
            // get the session ID
            id = jsonResponse.getBody();
        }
        return id;
    }

    
}
