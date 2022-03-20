package serversrc;

import java.util.ArrayList;

public class GameLobby {

    private String gameID;
    private ArrayList<ServerUser> serverUsers = new ArrayList<ServerUser>();
    private static ArrayList<GameLobby> allGameLobbies = new ArrayList<GameLobby>();
    private ServerGame serverGame;
    private boolean launched;
    private String displayName;
    private ServerUser creator;

    public GameLobby(String gameID, ServerGame serverGame, String displayName, ServerUser creator) {
        this.gameID = gameID;
        this.serverGame = serverGame;
        this.displayName = displayName;
        this.creator = creator;
        this.launched = false;
        allGameLobbies.add(this);
    }

    /**
     * Retrieves all game lobbies that are NOT launched.
     * @return ArrayList<GameLobby> of unlaunched lobbies
     */
    public static ArrayList<GameLobby> getAvailableLobbies() {
        ArrayList<GameLobby> availableLobbies = new ArrayList<>();
        for (GameLobby gl : allGameLobbies) {
            if (!gl.isLaunched()) {
                availableLobbies.add(gl);
            }
        }
        return availableLobbies;
    }

    public static GameLobby getGameLobby(String gameID) {
        for (GameLobby gameLobby : allGameLobbies) {
            if (gameLobby.getGameID().equals(gameID)) {
                return gameLobby;
            }
        }
        return null;
    }

    public ServerUser getCreator() {
        return creator;
    }
    
    public void addUser(ServerUser user) {
        this.serverUsers.add(user);
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

    /**
     * Returns the number of users currently registered in the lobby.
     * @return size of serverUsers
     */
    public int getNumberOfUsersCurrently() {
        return serverUsers.size();
    }

    public String getGameID() {
        return gameID;
    }

    public String getName() {
        return displayName;
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
