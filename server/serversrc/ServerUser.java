package serversrc;
/*
Represents one user from the moment the game is launched (they may not be a player yet)
 */

import java.util.ArrayList;

import org.json.simple.JSONObject;

public class ServerUser {

    // FIELDS
    private boolean ready;
    private Color color;
    private String username;
    private JSONObject currentToken;
    private static ArrayList<ServerUser> allUsers = new ArrayList<ServerUser>();

    // CONSTRUCTOR
    public ServerUser(String username, JSONObject currentToken) {
        this.username = username;
        this.currentToken = currentToken;
        allUsers.add(this);
    }

    public static ServerUser getServerUser(String username) {
        for (ServerUser sUser : allUsers) {
            if (sUser.getName().equals(username)) {
                return sUser;
            }
        }
        return null;
    }

    public String getName() {
        return username;
    }

    /**
     * GETTER: returns the user's current token from the LS
     * @return token in String format
     */
    public String getToken() {
        String token = (String) currentToken.get("access_token");
        token = token.replace("+", "%2B");
        return token;
    }

    /**
     * Toggles the User's ready state between true and false.
     */
    public void toggleReady() {
        this.ready = !this.ready;
    }

    /**
     * GETTER: returns user.ready
     * @return true if the user is ready to play, false otherwise.
     */
    public boolean isReady() {
        return ready;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
