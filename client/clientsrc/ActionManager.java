package clientsrc;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;


import org.minueto.MinuetoColor;
import org.minueto.MinuetoFileException;
import org.minueto.image.MinuetoCircle;
import org.minueto.image.MinuetoImage;
import org.minueto.image.MinuetoRectangle;
import org.minueto.image.MinuetoText;

import networksrc.Action;
import serversrc.CardType;
import serversrc.Token;

public class ActionManager {

    // singleton
    private static ActionManager INSTANCE = new ActionManager();
    List<Player> players;

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
        ObjectInputStream in = ClientMain.currentUser.getClient().getObjectInputStream();

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
        ObjectInputStream in = ClientMain.currentUser.getClient().getObjectInputStream();

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
    public void waitForMessages() throws MinuetoFileException {
        // WAIT FOR A MESSAGE
        ObjectInputStream in = ClientMain.currentUser.getClient().getObjectInputStream();
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

        }

    }

    /**
     * Sends the action to the Server and waits for a reply. When the reply is
     * received, it is executed if it is valid.
     * 
     * @param action action to send to the user. Don't forget to set senderName in
     *               the action parameters.
     */
    public Action sendActionAndGetReply(Action action) {
        try {
            // SEND TEST
            ObjectOutputStream out = ClientMain.currentUser.getClient().getObjectOutputStream();
            out.writeObject(action);

            // WAIT FOR REPLY
            ObjectInputStream in = ClientMain.currentUser.getClient().getObjectInputStream();
            boolean noAnswer = true;
            while (noAnswer) {
                Action actionIn = null;
                actionIn = (Action) in.readObject();
                if (actionIn != null) {
                    // REPLY RECEIVED
                    if (actionIn.isValid()) {
                        actionIn.execute();
                    }
                    noAnswer = false;
                    return actionIn;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
