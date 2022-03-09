package networksrc;

import java.io.IOException;
import java.io.ObjectOutputStream;

import serversrc.GameLobby;
import serversrc.Player;
import serversrc.ServerGame;
import serversrc.ServerUser;

public class LaunchGameAction implements Action{

    private String senderName;
    private String gameID;

    public LaunchGameAction(String senderName, String gameID) {
        this.senderName = senderName;
        this.gameID = gameID;
    }

    @Override
    public boolean isValid() {
        // null check
        if (senderName == null) {
            System.err.println("LaunchGameAction: The senderName cannot be null.");
            return false;
        }
        if (gameID == null) {
            System.err.println("LaunchGameAction: The gameID cannot be null.");
            return false;
        }

        // gameID is associated with a GameLobby
        GameLobby gameLobby = GameLobby.getGameLobby(gameID);
        if (gameLobby == null) {
            System.err.println("The GameLobby " + gameID + " does not exist on the server.");
            return false;
        }
        
        // The gameLobby is not launched yet
        boolean isLaunched = gameLobby.isLaunched();
        if (isLaunched) {
            System.err.println("The GameLobby " + gameID + " has already been laucnehd.");
            return false;
        }
    
        // senderName exists
        ServerUser sUser = ServerUser.getServerUser(senderName);
        if (sUser == null) {
            System.err.println(senderName + "does not exist on the server.");
            return false;
        }

        return true;
    }

    @Override
    public void execute() {
        GameLobby gameLobby = GameLobby.getGameLobby(gameID);
        ServerGame serverGame = gameLobby.getServerGame();

        // create 1 Player per ServerUser in the GameLobby
        for (ServerUser sUser : gameLobby.getAllUsers()) {
            Player newPlayer = new Player(sUser, serverGame);
        }

        // distribute resources here
        
        // notify all users in the lobby
        // TODO: LaunchGameACK will contain the game state
        LaunchGameACK actionToSend = new LaunchGameACK();
        try {
            Server serverInstance = Server.getInstance();
            for (ServerUser serverUser : gameLobby.getAllUsers()) {
                String username = serverUser.getName();
                // get the user's socket
                ClientTuple clientTupleToNotify = serverInstance.getClientTupleByUsername(username);
                // get the socket's output stream 
                ObjectOutputStream objectOutputStream = clientTupleToNotify.output();
                // send the acknowledgment
                objectOutputStream.writeObject(actionToSend);
            }
        } catch (IOException e) { 
            System.err.println("IOException in LaunchGameAction.execute()");
        }
    }
    
}
