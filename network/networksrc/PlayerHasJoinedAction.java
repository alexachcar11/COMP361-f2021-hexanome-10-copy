package networksrc;

import java.io.IOException;
import java.io.ObjectOutputStream;

import serversrc.GameLobby;
import serversrc.ServerUser;

public class PlayerHasJoinedAction implements Action{

    private String senderName;
    private String gameID;

    public PlayerHasJoinedAction(String senderName, String gameID) {
        if (senderName == null) {
            throw new NullPointerException("PlayerHasJoined: senderName cannot be null.");
        }
        if (gameID == null) {
            throw new NullPointerException("PlayerHasJoined: gameID cannot be null.");
        }
        this.senderName = senderName;
        this.gameID = gameID;
    }

    /**
     * Returns true if the game lobby exists, the senderName exists and the senderName is not already in the game lobby.
     * Returns false otherwise.
     */
    @Override
    public boolean isValid() {
        // gameName is associated with a GameLobby
        GameLobby gameLobby = GameLobby.getGameLobby(gameID);
        if (gameLobby == null) {
            System.err.println("The GameLobby " + gameID + " does not exist on the server.");
            return false;
        }
    
        // senderName exists
        ServerUser sUser = ServerUser.getServerUser(senderName);
        if (sUser == null) {
            System.err.println(senderName + "does not exist on the server.");
            return false;
        }

        // senderName is not already in the Game Lobby
        if (gameLobby.hasUser(senderName)) {
            System.err.println(senderName + " is already in the Game Lobby " + gameID);
            return false;
        }
        System.out.println("is valid");
        return true;
    }


    @Override
    public void execute() {
        // add the user to the lobby
        GameLobby gameLobby = GameLobby.getGameLobby(gameID);
        ServerUser sUser = ServerUser.getServerUser(senderName);
        gameLobby.addUser(sUser);
        // notify all users in the lobby
        PlayerHasJoinedACK actionToSend = new PlayerHasJoinedACK(senderName);
        try {
            Server serverInstance = Server.getInstance();
            for (ServerUser serverUser : gameLobby.getAllUsers()) {
                String username = serverUser.getName();
                // get the user's socket
                ClientTuple clientTupleToNotify = serverInstance.getClientTupleByUsername(username);
                // get the socket's output stream 
                ObjectOutputStream objectOutputStream = clientTupleToNotify.output();
                // send the acknowledgment
                System.out.println("sending back...");
                objectOutputStream.writeObject(actionToSend);
            }
        } catch (IOException e) { 
            System.err.println("IOException in PlayerHasJoined.execute().");
        }
    }
    
}
