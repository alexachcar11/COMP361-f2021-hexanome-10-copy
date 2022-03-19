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

    private String currentToken;    // registrator's token
    private String refreshToken;    // registrator's refresh token

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
    public String createToken(String username, String password) throws ParseException, IllegalAccessException {

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
        String newToken = (String) token.get("access_token");
        String newRefreshToken = (String) token.get("refresh_token");
        currentToken = newToken.replace("+", "%2B");
        refreshToken = newRefreshToken.replace("+", "%2B");
        return currentToken;
    }

    /**
     * Refreshes the access token and set currentAccessToken to this new token
     * 
     * @throws ParseException
     * @return Refreshed token in String format
     */
    public String refreshToken() throws ParseException {
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
        String newToken = (String) token.get("access_token");
        String newRefreshToken = (String) token.get("refresh_token");
        currentToken = newToken.replace("+", "%2B");
        refreshToken = newRefreshToken.replace("+", "%2B");
        return refreshToken;
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
        return currentToken;
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

    
}
