package src;
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
        // the game service exists
        LobbyServiceGame gameService =  LobbyServiceGame.getLobbyServiceGame(gameServiceName);
        if (gameService == null) {
            System.err.println("The specified game service does not exist.");
            return false;
        }
        // the user has joined the game via Lobby Service
        LobbyServiceGameSession sessionJoined = gameService.getActiveSession();
        if (!sessionJoined.hasUserName(userName)) {
            System.err.println("The user is not in the specified game.");
            return false;
        }

        return true;
    }

    @Override
    public void execute() {
        if (isValid()) {
            // send all users (for this game) a message that the player has joined
            LobbyServiceGame gameService =  LobbyServiceGame.getLobbyServiceGame(gameServiceName);
            LobbyServiceGameSession sessionJoined = gameService.getActiveSession();
            ArrayList<User> usersToNotify = sessionJoined.getUsers();
            for (User u : usersToNotify) {

            }
        } else {
            System.err.println("playerHasJoinedAction is not valid.");
        }

    }

}
