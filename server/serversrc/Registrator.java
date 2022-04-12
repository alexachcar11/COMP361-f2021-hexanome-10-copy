package serversrc;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONArray;
import java.nio.charset.StandardCharsets;
import java.util.*;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import unirest.shaded.com.google.gson.Gson;

public class Registrator {

    private static final Registrator INSTANCE = new Registrator(); // SINGLETON

    private JSONObject currentToken; // registrator's token

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

    /**
     * Returns the singleton instance of Registrator
     * 
     * @return
     */
    public static Registrator instance() {
        return INSTANCE;
    }

    /**
     * Creates an access token for an admin
     * 
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
     * 
     * @return current token in String format
     */
    public String getToken() {
        String token = (String) currentToken.get("access_token");
        token = token.replace("+", "%2B");
        return token;
    }

    /**
     * Retrieves a user by their username on LS
     * 
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

        if (jsonResponse.getBody().contains("Access token expired")) {
            currentToken = this.refreshToken();
        } else if (jsonResponse.getStatus() != 200) {
            System.err.println(jsonResponse.getBody());
            throw new IllegalArgumentException("Unable to retrieve user: " + userName);
        }

        return (JSONObject) parser.parse(jsonResponse.getBody());
    }

    /**
     * Retrieves the list of existing users on the LS
     * 
     * @return JSONArray of JSONObjects: name, password, preferredColour, role
     * @throws ParseException
     */
    public JSONArray getAllUsers() throws ParseException {

        HttpResponse<String> jsonResponse = Unirest
                .get("http://127.0.0.1:4242/api/users?access_token=" + this.getToken().replace("+", "%2B"))
                .header("Authorization", "Basic " + encoded).asString();

        if (jsonResponse.getBody().contains("Access token expired")) {
            currentToken = this.refreshToken();
        } else if (jsonResponse.getStatus() != 200) {
            System.err.println(jsonResponse.getBody());
            throw new RuntimeException("Error" + jsonResponse.getStatus() + ": unable to retrieve users.");
        }

        return (JSONArray) parser.parse(jsonResponse.getBody());
    }

    /**
     * Returns true if the username exists in LS, false otherwise.
     * 
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
     * 
     * @param userName username of the user
     * @param passWord password of the user
     * @return User instance that is created upon API request success
     * @throws ParseException
     */
    public void createNewUser(String userName, String passWord) throws ParseException {
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

    /**
     * Sends a request to LS to create a new game service.
     * 
     * @param creator                ServerUser that send the request
     * @param displayName            display name of the game (i.e name chosen by
     *                               the creator)
     * @param numberOfPlayers        total number of players
     * @param numberOfRounds         number of rounds to play
     * @param mode                   elfenland or elfengold
     * @param witchEnabled           are we playing with the witch variant
     * @param destinationTownEnabled are we playing with destination towns
     * @param townGoldOption         are we playing with town gold
     * @return gameID of the session created
     * @throws ParseException
     */
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
        if (jsonResponse.getBody().contains("Access token expired")) {
            this.refreshUserToken(creator);
            gameID = this.createGameSession(name, creator, "");
        } else if (jsonResponse.getStatus() == 400) {
            System.err.println(jsonResponse.getStatus() + ": " + jsonResponse.getBody());
        } else if (jsonResponse.getStatus() != 200) {
            System.err.println(jsonResponse.getStatus() + ": " + jsonResponse.getBody());
        } else {
            // success -> create a game session
            gameID = this.createGameSession(name, creator, "");
        }
        return gameID;
    }

    /**
     * Sends a LS request to create a new game session.
     * 
     * @param name       name of the game service(NOT display name)
     * @param creator    creator's username
     * @param saveGameID save game ID or "" if there is none
     * @return gameID
     * @throws ParseException
     */
    public String createGameSession(String name, ServerUser creator, String saveGameID) throws ParseException {
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
        if (jsonResponse.getBody().contains("Access token expired")) {
            this.refreshUserToken(creator);
            id = jsonResponse.getBody();
        } else if (jsonResponse.getStatus() != 200) {
            System.err.println("Error" + jsonResponse.getStatus() + ": " + jsonResponse.getBody());
        } else {
            // success !
            // get the session ID
            id = jsonResponse.getBody();
        }
        return id;
    }

    /**
     * Sends a request to LS for a user to join a session
     * 
     * @param sessionID   session ID of the session to join
     * @param userJoining ServerUser that wants to join
     * @throws Exception when the request is rejected
     */
    public void joinGame(String sessionID, ServerUser userJoining) throws Exception {
        // user token
        String token = userJoining.getToken().replace("+", "%2B");
        System.out.println(token);

        // build request
        HttpResponse<String> jsonResponse = Unirest
                .put("http://127.0.0.1:4242/api/sessions/" + sessionID + "/players/"
                        + userJoining.getName() + "?access_token="
                        + token)
                .asString();

        System.out.println(jsonResponse.getBody());

        // verify response
        if (jsonResponse.getStatus() != 200) {
            if (jsonResponse.getBody().contains("Access token expired")) {
                this.refreshUserToken(userJoining);
                this.joinGame(sessionID, userJoining);
            } else {
                System.err.println("Error" + jsonResponse.getStatus() + ": " + jsonResponse.getBody());
                throw new Exception("Error" + jsonResponse.getStatus() + ": could not join game");
            }
        }
    }

    public void refreshUserToken(ServerUser user) throws ParseException {
        JSONObject token = user.getTokenObject();
        String refreshToken = ((String) token.get("refresh_token")).replace("+", "%2B");
        HttpResponse<String> jsonResponse = Unirest
                .post("http://127.0.0.1:4242/oauth/token?grant_type=refresh_token&refresh_token="
                        + refreshToken)
                .header("Authorization", "Basic " + encoded)
                .asString();
        if (jsonResponse.getStatus() != 200) {
            throw new IllegalArgumentException("Error" + jsonResponse.getStatus() + ": could not refresh user token.");
        } else {
            // success
            JSONObject newToken = (JSONObject) parser.parse(jsonResponse.getBody());
            user.setToken(newToken);
        }
    }

    // TODO
    public void leaveGame(String sessionID, ServerUser userLeaving) throws ParseException {
        // user token
        String token = userLeaving.getToken().replace("+", "%2B");
        System.out.println(token);

        // build request
        HttpResponse<String> jsonResponse = Unirest
                .delete("http://127.0.0.1:4242/api/sessions/" + sessionID + "/players/"
                        + userLeaving.getName() + "?access_token="
                        + token)
                .asString();

        System.out.println(jsonResponse.getBody());

        // verify response
        if (jsonResponse.getBody().contains("Access token expired")) {
            this.refreshUserToken(userLeaving);
            // sessionToLeave.removeUser(userLeaving);
            // TODO: notify all users that a player has left
        } else if (jsonResponse.getStatus() != 200) {
            System.err.println("Error" + jsonResponse.getStatus() + ": could not leave game");
        } else {
            // sessionToLeave.removeUser(userLeaving);
            // TODO: notify all users that a player has left
        }
    }

    // TODO
    public void deleteSession(String sessionID, ServerUser userAskingToDelete) throws ParseException {
        // user token
        String token = userAskingToDelete.getToken().replace("+", "%2B");
        System.out.println(token);

        // build request
        HttpResponse<String> jsonResponse = Unirest
                .delete("http://127.0.0.1:4242/api/sessions/" + sessionID
                        + "?access_token="
                        + token)
                .asString();

        System.out.println(jsonResponse.getBody());

        // verify response
        if (jsonResponse.getBody().contains("Access token expired")) {
            this.refreshUserToken(userAskingToDelete);
        } else if (jsonResponse.getStatus() != 200) {
            System.err.println("Error" + jsonResponse.getStatus() + ": could not delete game session");
        } else {
            System.out.println("deleted successfully");
        }
    }

    public void registerSaveGame(String gameName, List<String> playerNames, String saveGameID) {
        HttpResponse<String> jsonResponse = Unirest
                .put("http://elfenland.simui.com/api/gameservices/{gameservice}/savegames").asString();
    }

    /**
     * Send an LS request to launch a session
     * 
     * @param sessionID          session ID to launch
     * @param userAskingToLaunch user that send the request
     * @throws Exception if the request was rejected
     */
    public void launchSession(String sessionID, ServerUser userAskingToLaunch) throws Exception {

        /*
         * // user token
         * String token = userAskingToLaunch.getToken().replace("+", "%2B");
         * System.out.println(token);
         * 
         * // build request
         * HttpResponse<String> jsonResponse = Unirest
         * .post("http://127.0.0.1:4242/api/sessions/" + sessionID
         * + "?access_token="
         * + token)
         * .asString();
         * 
         * System.out.println(jsonResponse.getBody());
         * 
         * // verify response
         * if (jsonResponse.getBody().contains("Access token expired")) {
         * this.refreshUserToken(userAskingToLaunch);
         * } else if (jsonResponse.getStatus() != 200) {
         * throw new Exception("Error" + jsonResponse.getStatus() +
         * jsonResponse.getBody());
         * }
         */
    }
}
