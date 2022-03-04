package networksrc;

import java.util.ArrayList;

import serversrc.Color;
import serversrc.GameLobby;
import serversrc.ServerUser;

public class GetAvailableColorsAction implements Action {

    private String senderName;
    private String gameID;

    public GetAvailableColorsAction(String senderName, String gameID) {
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
    
        // senderName exists
        ServerUser sUser = ServerUser.getServerUser(senderName);
        if (sUser == null) {
            System.err.println(senderName + " does not exist on the server.");
            return false;
        }

        // senderName doesn't have a color yet
        Color color = sUser.getColor();
        if (color == null) {
            System.err.println(senderName + " already has the color " + color);
            return false;
        }

        return true;
    }

    @Override
    public void execute() {
        // list all colors
        ArrayList<String> availableColors = new ArrayList<String>();
        availableColors.add("green");
        availableColors.add("blue");
        availableColors.add("black");
        availableColors.add("red");
        availableColors.add("purple");
        availableColors.add("yellow");
        
        // remove colors that are taken
        GameLobby gameLobby = GameLobby.getGameLobby(gameID);
        for (ServerUser sUser : gameLobby.getAllUsers()) {
            Color colorTaken = sUser.getColor();
            if (colorTaken != null)
            availableColors.remove(colorTaken.name());
        }

        // send ACK to sender
        ACKManager ackManager = ACKManager.getInstance();
        GetAvailableColorsACK actionToSend = new GetAvailableColorsACK(availableColors);
        ackManager.sendToSender(actionToSend, senderName);
    }
    
}
