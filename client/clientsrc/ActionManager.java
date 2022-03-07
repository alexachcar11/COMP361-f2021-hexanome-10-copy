package clientsrc;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import networksrc.Action;

public class ActionManager {

    // singleton
    private static ActionManager INSTANCE = new ActionManager();

    private ActionManager() {
        
    }

    /**
     * Returns the singleton INSTANCE of the ActionManager
     * @return INSTANCE
     */
    public static ActionManager getInstance() {
        return INSTANCE;
    }

    /**
     * Waits for a message from the Server. This runs while the session is NOT launched.
     * When a message is received, execute it if it's valid, then wait for another message.
     */
    public void waitForPlayers() {
        // WAIT FOR PLAYERS
        ObjectInputStream in = ClientMain.currentUser.getClient().getObjectInputStream();
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
            }   
        }
    }

    /**
     * Waits for a message from the Server. This runs while it is NOT the player's turn.
     * When a message is received, execute it if it's valid, then wait for another message.
     */
    public void waitForMessages() {
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
     * Sends the action to the Server and waits for a reply. When the reply is received, it is executed if it is valid.
     * @param action action to send to the user. Don't forget to set senderName in the action parameters.
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
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
}
