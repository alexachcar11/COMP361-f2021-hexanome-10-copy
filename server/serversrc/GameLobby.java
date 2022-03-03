package serversrc;

import java.util.ArrayList;

public class GameLobby {

    private String gameID;
    private ArrayList<ServerUser> serverUsers;
    private static ArrayList<GameLobby> allGameLobbies;
    private ServerGame serverGame;
    private boolean launched;

    public GameLobby(String gameID, ServerGame serverGame) {
        this.gameID = gameID;
        this.serverGame = serverGame;
        this.launched = false;
        allGameLobbies.add(this);
    }

    public void addUser(ServerUser user) {
        this.serverUsers.add(user);
    }

    public static GameLobby getGameLobby(String gameID) {
        for (GameLobby gameLobby : allGameLobbies) {
            if (gameLobby.getGameID().equals(gameID)) {
                return gameLobby;
            }
        }
        return null;
    }

    public boolean hasUser(String name) {
        for (ServerUser serverUser : serverUsers) {
            if (serverUser.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<ServerUser> getAllUsers() {
        return serverUsers;
    }

    public String getGameID() {
        return gameID;
    }

    public void setLaunched(boolean launch) {
        this.launched = launch;
    }

    public boolean isLaunched() {
        return launched;
    }

    public ServerGame getServerGame() {
        return serverGame;
    }
}
