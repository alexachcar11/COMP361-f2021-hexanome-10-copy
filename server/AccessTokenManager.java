import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import java.util.Map;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import unirest.shaded.com.google.gson.Gson;

/**
 * Singleton object: the token manager handles the creation/deletion/update of
 * tokens
 */

public class AccessTokenManager {

    // fields
    private static JSONObject currentTokenJSON;
    private final static AccessTokenManager tokenManagerInstance = new AccessTokenManager();

    // constructor - is private to ensure singleton
    private AccessTokenManager() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                try {
                    currentTokenJSON = createNewAccessToken();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        }, 0, 1790 * 1000);
        timer.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                try {
                    currentTokenJSON = refreshAccessToken();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }

        }, 590 * 1000, 590 * 1000);
    }

    /**
     * GETTER: retrieves the singleton instance of tokenManager
     * 
     * @return tokenManagerInstance
     */
    public static AccessTokenManager instance() {
        return tokenManagerInstance;
    }

    public static JSONObject getAccessToken() {
        return currentTokenJSON;
    }

    /**
     * Creates a new access token for an admin.
     * 
     * @throws IOException
     * @throws ParseException
     */
    public static JSONObject createNewAccessToken() throws ParseException {
        // Encode the token scope
        String encoded = Base64.getEncoder()
                .encodeToString(("bgp-client-name:bgp-client-pw").getBytes(StandardCharsets.UTF_8)); // Java 8

        Map<String, Object> fields = new HashMap<>();
        fields.put("token_type", "service");

        String body = new Gson().toJson(fields);

        HttpResponse<String> jsonResponse = Unirest
                .post("http://127.0.0.1:4242/oauth/token?grant_type=password&username=maex&password=abc123_ABC123")
                .header("Authorization", "Basic " + encoded)
                .body(body)
                .asString();

        if (jsonResponse.getStatus() != 200) {
            System.err.println("Error" + jsonResponse.getStatus() + ": could not create access token.");
        }

        JSONParser parser = new JSONParser();
        JSONObject token = (JSONObject) parser.parse(jsonResponse.getBody());

        System.out.println(token.toString());

        return token;
    }

    /**
     * Refreshes the access token and set currentAccessToken to this new token
     * 
     * @throws IOException
     * @throws ParseException
     */
    public static JSONObject refreshAccessToken() throws ParseException {
        String encoded = Base64.getEncoder()
                .encodeToString(("bgp-client-name:bgp-client-pw").getBytes(StandardCharsets.UTF_8)); // Java 8

        HttpResponse<String> jsonResponse = Unirest
                .post("http://127.0.0.1:4242/oauth/token?grant_type=refresh_token&refresh_token="
                        + currentTokenJSON.get("refresh_token"))
                .header("Authorization", "Basic " + encoded)
                .asString();

        if (jsonResponse.getStatus() != 200) {
            System.err.println("Error" + jsonResponse.getStatus() + ": cannot refresh token.");
        }
        JSONParser parser = new JSONParser();
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
    public static String getCurrentAccessTokenString() {
        return (String) currentTokenJSON.get("access_token");
    }

}
