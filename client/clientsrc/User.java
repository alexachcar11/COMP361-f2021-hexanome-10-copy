/*
Represents one user from the moment the game is launched (they may not be a player yet)
 */

package clientsrc;

import java.util.ArrayList;

public class User {

    // FIELDS
    private String name;
    private boolean ready = false;
    private Color color = null;
    private Client client;
    private static ArrayList<User> allUsers = new ArrayList<>();

    // CONSTRUCTOR
    public User(String name) {
        this.name = name;
    }

    public static User getUserByName(String name) {
        for (User u : allUsers) {
            if (u.getName().equals(name)) {
                return u;
            }
        }
        return null;
    }

    /**
     * GETTER: returns the user's name as seen on LS
     * 
     * @return User.name
     */
    public String getName() {
        return name;
    }

    /**
     * Toggles the User's ready state between true and false.
     */
    public void toggleReady() {
        this.ready = !this.ready;
    }

    /**
     * GETTER: returns user.ready
     * 
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

    /**
     * Return this user's client (for the network stuff)
     * 
     * @return client
     */
    public Client getClient() {
        return client;
    }
}
