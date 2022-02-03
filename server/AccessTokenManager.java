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

/**
 * Singleton object: the token manager handles the creation/deletion/update of tokens
 */

public class AccessTokenManager {

    // fields
    private static JSONObject currentTokenJSON;
    private static AccessTokenManager tokenManagerInstance = new AccessTokenManager();

    // constructor - is private to ensure singleton
    private AccessTokenManager() {

    }

    /**
     * GETTER: retrieves the singleton instance of tokenManager
     * @return tokenManagerInstance
     */
    public static AccessTokenManager getAccessTokenManagerInstance() {
        return tokenManagerInstance;
    }

    /**
     * Creates a new access token for an admin.
     * @throws IOException
     * @throws ParseException
     */
    public static void createNewAccessToken() throws IOException, ParseException {
        URL url = new URL("http://127.0.0.1:4242/oauth/token?grant_type=password&username=maex&password=abc123_ABC123");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");

        // Encode the token scope
        String encoded = Base64.getEncoder()
                .encodeToString(("bgp-client-name:bgp-client-pw").getBytes(StandardCharsets.UTF_8)); // Java 8
        con.setRequestProperty("Authorization", "Basic " + encoded);
        /* Payload support */
        con.setDoOutput(true);

        int status = con.getResponseCode();
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        con.disconnect();
        System.out.println("Response status: " + status);
        System.out.println(content.toString());

        JSONParser parser = new JSONParser();
        JSONObject newToken = (JSONObject) parser.parse(content.toString());
        currentTokenJSON = newToken;
    }

    /**
     * Refreshes the access token and set currentAccessToken to this new token
     * @throws IOException
     * @throws ParseException
     */
    public static void refreshAccessToken() throws IOException, ParseException {
        URL url = new URL(
                "http://127.0.0.1:4242/oauth/token?grant_type=refresh_token&refresh_token="
                        + currentTokenJSON.get("refresh_token"));
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");

        // Encode the token scope
        String encoded = Base64.getEncoder()
                .encodeToString(("bgp-client-name:bgp-client-pw").getBytes(StandardCharsets.UTF_8)); // Java 8
        con.setRequestProperty("Authorization", "Basic " + encoded);

        /* Payload support */
        con.setDoOutput(true);

        int status = con.getResponseCode();
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        con.disconnect();
        System.out.println("Response status: " + status);
        System.out.println(content.toString());

        JSONParser parser = new JSONParser();
        JSONObject newToken =  (JSONObject) parser.parse(content.toString());
        currentTokenJSON = newToken;
    }

    /**
     * Revoke token
     */
    public static void revokeToken() {

    }

    /**
     * Takes currentTokenJSON and returns its string value
     * @return current token in String format
     */
    public static String getCurrentAccessTokenString() {
        return (String) currentTokenJSON.get("access_token");
    }

}
