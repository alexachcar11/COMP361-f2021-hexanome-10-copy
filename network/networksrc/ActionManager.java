package networksrc;

import java.io.IOException;
import java.io.ObjectOutputStream;

import clientsrc.ClientMain;
import serversrc.Player;
import serversrc.ServerGame;

public class ActionManager {

    // singleton
    private static ActionManager INSTANCE = new ActionManager();

    private ActionManager() {

    }

    /**
     * Returns the singleton INSTANCE of the ActionManager
     * 
     * @return INSTANCE
     */
    public static ActionManager getInstance() {
        return INSTANCE;
    }

    /**
     * Sends the action to the Server and waits for a reply. When the reply is
     * received, it is executed if it is valid.
     * 
     * @param action action to send to the user. Don't forget to set senderName in
     *               the action parameters.
     */
    public Action sendAction(Action action) {
        try {
            // SEND TEST
            ObjectOutputStream out = ClientMain.currentClient.getObjectOutputStream();
            out.writeObject(action);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
            e.printStackTrace();
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
