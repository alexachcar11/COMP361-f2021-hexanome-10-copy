package serversrc;
/*
Represents one user from the moment the game is launched (they may not be a player yet)
 */

// import clientsrc.Color;
import networksrc.Client;

public class ServerUser {

    // FIELDS
    private boolean ready;
    private Color color;
    private Client client;
    private String name;

    // CONSTRUCTOR
    public ServerUser(Client client, String name) {
        this.client = client;
        this.name = name;
    }

    public Client getClient() {
        return client;
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
