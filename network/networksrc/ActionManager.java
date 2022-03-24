package networksrc;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.minueto.MinuetoFileException;
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
     * Waits for a message from the Server. This runs while the session is NOT
     * launchable.
     * When a message is received, execute it if it's valid, then wait for another
     * message.
     * 
     * @throws MinuetoFileException
     */
    public void waitForPlayersAsCreator() throws MinuetoFileException {
        // WAIT FOR PLAYERS
        ObjectInputStream in = ClientMain.currentClient.getObjectInputStream();

        // wait until launched
        while (!ClientMain.currentSession.isLaunchable()) {
            Action actionIn = null;
            try {
                actionIn = (Action) in.readObject();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (actionIn != null) {
                // MESSAGE RECEIVED
                if (actionIn.isValid()) {
                    actionIn.execute();
                }
                // update gui
                ClientMain.gui.window.render();
            }
        }
    }

    /**
     * Waits for a message from the Server. This runs while the session is NOT
     * launched.
     * When a message is received, execute it if it's valid, then wait for another
     * message.
     * 
     * @throws MinuetoFileException
     */
    public void waitForPlayers() throws MinuetoFileException {
        // WAIT FOR PLAYERS
        ObjectInputStream in = ClientMain.currentClient.getObjectInputStream();

        // wait until launched
        while (!ClientMain.currentSession.isLaunched()) {
            Action actionIn = null;
            try {
                actionIn = (Action) in.readObject();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (actionIn != null) {
                // MESSAGE RECEIVED
                if (actionIn.isValid()) {
                    actionIn.execute();
                }
                // show wait for launch image
                if (ClientMain.currentSession.isLaunchable()) {
                    ClientMain.gui.window.draw(ClientMain.waitingForLaunch, 822, 580);
                }
                // update gui
                ClientMain.gui.window.render();
            }
        }
    }

    /**
     * Waits for a message from the Server. This runs while it is NOT the player's
     * turn.
     * When a message is received, execute it if it's valid, then wait for another
     * message.
     * 
     * @throws MinuetoFileException
     */
    public void waitForMessages() {
        // WAIT FOR A MESSAGE
        ObjectInputStream in = ClientMain.currentClient.getObjectInputStream();
        while (!ClientMain.currentPlayer.isTurn()) {
            Action actionIn = null;
            try {
                actionIn = (Action) in.readObject();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (actionIn != null) {
                // MESSAGE RECEIVED
                if (actionIn.isValid()) {
                    actionIn.execute();
                }
            }
            // update gui
            ClientMain.gui.window.render();
        }
    }

    /**
     * Sends the action to the Server and waits for a reply. When the reply is
     * received, it is executed if it is valid.
     * 
     * @param action action to send to the user. Don't forget to set senderName in
     *               the action parameters.
     */
    public Action sendActionAndGetResponse(Action action) {
        try {
            ObjectOutputStream out = ClientMain.currentClient.getObjectOutputStream();
            out.writeObject(action);
            ObjectInputStream in = ClientMain.currentClient.getObjectInputStream();
            Action toExecute = (Action) in.readObject();
            if (toExecute.isValid()) {
                toExecute.execute();
            }
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
