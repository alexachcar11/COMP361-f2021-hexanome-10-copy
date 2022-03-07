package networksrc;

import java.util.ArrayList;

import serversrc.GameLobby;
import serversrc.ServerUser;

public class UpdateUsersAction implements Action{

    private String senderName;
    private String gameID;

    public UpdateUsersAction(String senderName, String gameID) {
        this.senderName = senderName;
        this.gameID = gameID;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void execute() {
        // get all users in the ServerGame
        GameLobby gameLobby = GameLobby.getGameLobby(gameID);
        ArrayList<ServerUser> allUsers = gameLobby.getAllUsers();

        // get new users
        ArrayList<String> newUsers = new ArrayList<>();
        for (ServerUser sUser : allUsers) {
            String name = sUser.getName();
            newUsers.add(name);
            newUsers.add(sUser.getColor().name());
        }

        // send ACK to sender
        ACKManager ackManager = ACKManager.getInstance();
        UpdateUsersACK actionToSend = new UpdateUsersACK(newUsers);
        ackManager.sendToSender(actionToSend, senderName);
    }
    
}
