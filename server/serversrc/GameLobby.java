package serversrc;

import java.util.ArrayList;

import networksrc.Client;

public class GameLobby {

    private String gameID;
    private ArrayList<ServerUser> serverUsers;
    private static ArrayList<GameLobby> allGameLobbies;

    public GameLobby(String gameID) {
        this.gameID = gameID;
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
