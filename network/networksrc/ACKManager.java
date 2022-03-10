package networksrc;

import java.io.IOException;
import java.io.ObjectOutputStream;

import serversrc.Player;
import serversrc.ServerGame;

public class ACKManager {

    // singleton
    private static ACKManager INSTANCE = new ACKManager();

    private ACKManager() {

    }

    public static ACKManager getInstance() {
        return INSTANCE;
    }

    /**
     * Send an action to senderName.
     * 
     * @param action     action to send (most likely an ACK)
     * @param senderName name of the user that should receive the action
     */
    public void sendToSender(Action action, String senderName) {
        try {
            // get the senderName's socket
            Server serverInstance = Server.getInstance();
            ClientTuple clientTupleToNotify = serverInstance.getClientTupleByUsername(senderName);
            // get the socket's output stream
            ObjectOutputStream objectOutputStream = clientTupleToNotify.output();
            // send
            objectOutputStream.writeObject(action);
        } catch (IOException e) {
            System.err.println("IOException in sendToSender().");
        }
    }

    /**
     * Send an actino to all players in the specified game.
     * 
     * @param action action to send
     * @param game   game in which the players are
     */
    public void sentToAllPlayersInGame(Action action, ServerGame game) {
        try {
            Server serverInstance = Server.getInstance();
            for (Player p : game.getAllPlayers()) {
                String playersName = p.getName();
                // get the player's socket
                ClientTuple clientTupleToNotify = serverInstance.getClientTupleByUsername(playersName);
                // get the socket's output stream (don't forget to import
                // java.io.ObjectOutputStream;)
                ObjectOutputStream objectOutputStream = clientTupleToNotify.output();
                // send the acknowledgment
                objectOutputStream.writeObject(action);
            }
        } catch (IOException e) {
            System.err.println("IOException in sendToAllPlayersInGame().");
            e.printStackTrace();
        }
    }

}
