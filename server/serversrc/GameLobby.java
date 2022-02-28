package serversrc;

import java.util.ArrayList;

import networksrc.Client;

public class GameLobby {

    private String name;
    private ArrayList<ServerUser> serverUsers;
    private static ArrayList<GameLobby> allGameLobbies;

    public GameLobby(String name) {
        this.name = name;
        allGameLobbies.add(this);
    }

    public void addUser(ServerUser user) {
        this.serverUsers.add(user);
    }

    public static GameLobby getGameLobby(String name) {
        for (GameLobby gameLobby : allGameLobbies) {
            if (gameLobby.name.equals(name)) {
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

    /* public ArrayList<Client> getClients() {
        ArrayList<Client> clients = new ArrayList<>();
        for (ServerUser u : serverUsers) {
            Client c = u.getClient();
            clients.add(c);
        }
        return clients;
    } */

}
