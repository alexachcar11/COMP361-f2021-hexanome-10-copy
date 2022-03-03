package serversrc;

import java.util.ArrayList;

public class GameLobby {

    private String gameID;
    private ArrayList<ServerUser> serverUsers = new ArrayList<ServerUser>();
    private static ArrayList<GameLobby> allGameLobbies = new ArrayList<GameLobby>();
    private ServerGame serverGame;

    public GameLobby(String gameID, ServerGame serverGame) {
        this.gameID = gameID;
        this.serverGame = serverGame;
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
}
