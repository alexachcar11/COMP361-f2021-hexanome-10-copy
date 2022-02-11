/*
Represents one user from the moment the game is launched (they may not be a player yet)
 */

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class User {

    // FIELDS
    private String name;
    private String password;
    private JSONObject currentTokenJSON;
    private static final Registrator REGISTRATOR = Registrator.instance();
    private boolean ready;

    // CONSTRUCTOR
    public User(String name, String password) {
        this.name = name;
        this.password = password;
        try {
            this.currentTokenJSON = REGISTRATOR.createToken(name, password);
        } catch (ParseException e) {
            throw new RuntimeException("Error: could not create user.");
        }
    }

    /**
     * GETTER: returns the user's name as seen on LS
     * @return User.name
     */
    public String getName() {
        return name;
    }

    /**
     * GETTER: returns the user's current token from the LS
     * @return token in String format
     */
    public String getToken() {
        return (String) this.currentTokenJSON.get("access_token");
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

}
