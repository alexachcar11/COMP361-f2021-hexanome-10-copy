package networksrc;

import java.util.ArrayList;

import serversrc.GameLobby;
import serversrc.ServerUser;

public class UpdateUsersAction implements Action{

    private String senderName;
    private String gameID;
    private ArrayList<String> currentUsers;

    public UpdateUsersAction(String senderName, String gameID, ArrayList<String> currentUsers) {
        this.senderName = senderName;
        this.gameID = gameID;
        this.currentUsers = currentUsers;
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
            if (!currentUsers.contains(name)) {
                newUsers.add(name);
                newUsers.add(sUser.getColor().name());
            }
        }

        // send ACK to sender
        ACKManager ackManager = ACKManager.getInstance();
        UpdateUsersACK actionToSend = new UpdateUsersACK(newUsers);
        ackManager.sendToSender(actionToSend, senderName);
    }
    
}
