package serversrc;
/*
Represents one user from the moment the game is launched (they may not be a player yet)
 */

// import clientsrc.Color;

public class ServerUser {

    // FIELDS
    private boolean ready;
    private Color color;
    private String name;

    // CONSTRUCTOR
    public ServerUser(String name) {
        this.name = name;
    }

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
