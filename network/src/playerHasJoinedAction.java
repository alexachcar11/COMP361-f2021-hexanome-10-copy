import java.lang.reflect.Array;
import java.util.ArrayList;

public class playerHasJoinedAction extends Action{

    private String userName;
    private String gameServiceName;

    /**
     * CONSTRUCTOR
     * @param userName user that has joined the lobby
     * @param gameServiceName game service that was joined by the user
     */
    public playerHasJoinedAction(String userName, String gameServiceName) {
        // sanity check
        if (userName == null) {
            throw new NullPointerException("userJoining cannot be null.");
        } if (gameServiceName == null) {
            throw new NullPointerException("gameServiceName cannot be null.");
        }
        this.userName = userName;
        this.gameServiceName = gameServiceName;
    }

    @Override
    public boolean isValid() {
        // game exists?
        GameLobby gameLobby = GameLobby.getGameLobby(gameServiceName);
        if (gameLobby == null) {
            System.err.println("No game called " + gameServiceName);
            return false;
        }
        // user is in gameLobby?
        if (!gameLobby.hasUser(userName)) {
            System.err.println(gameServiceName + " does not have user " + userName);
            return false;
        }
        return true;
    }

    @Override
    public void execute() {
        if (isValid()) {
            // send all users (for this game) a message that the player has joined
            GameLobby gameLobby = GameLobby.getGameLobby(gameServiceName);
            ArrayList<Client> clientsToNotify = gameLobby.getClients();
            for (Client c : clientsToNotify) {
                // TODO
            }
        } else {
            System.err.println("playerHasJoinedAction is not valid.");
        }
    }
}
