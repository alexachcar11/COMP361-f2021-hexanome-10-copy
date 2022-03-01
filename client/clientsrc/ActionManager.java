package clientsrc;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import networksrc.Action;

public class ActionManager {

    private User currentUser;

    // singleton
    private static ActionManager INSTANCE = new ActionManager();

    private ActionManager() {
        this.currentUser = ClientMain.currentUser;
    }

    /**
     * Returns the singleton INSTANCE of the ActionManager
     * @return INSTANCE
     */
    public static ActionManager getInstance() {
        return INSTANCE;
    }

    /**
     * Waits for a message from the Server. This runs while it is NOT the player's turn.
     * When a message is received, execute it if it's valid, then wait for another message.
     */
    public void waitForMessages() {
        // WAIT FOR A MESSAGE
        ObjectInputStream in = currentUser.getClient().getObjectInputStream();
        Player currentPlayer = Player.getPlayerByName(currentUser.getName());
        while (!currentPlayer.isTurn()) {
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
    public void sendActionAndGetReply(Action action) {
        try {
            // SEND TEST
            ObjectOutputStream out = currentUser.getClient().getObjectOutputStream();
            out.writeObject(action);

            // WAIT FOR REPLY
            ObjectInputStream in = currentUser.getClient().getObjectInputStream();
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
                }   
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
