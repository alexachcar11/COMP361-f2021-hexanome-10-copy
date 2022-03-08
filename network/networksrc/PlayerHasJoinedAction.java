package networksrc;

import java.io.IOException;
import java.io.ObjectOutputStream;

import serversrc.Color;
import serversrc.GameLobby;
import serversrc.ServerUser;

public class PlayerHasJoinedAction implements Action{

    private String senderName;
    private String gameID;
    private String color;

    public PlayerHasJoinedAction(String senderName, String gameID, String color) {
        if (senderName == null) {
            throw new NullPointerException("PlayerHasJoined: senderName cannot be null.");
        }
        if (gameID == null) {
            throw new NullPointerException("PlayerHasJoined: gameID cannot be null.");
        }
        if (color == null) {
            throw new NullPointerException("PlayerHasJoined: color cannot be null.");
        }
        this.senderName = senderName;
        this.gameID = gameID;
        this.color = color;
        System.out.println("created a playerhasjoineaction!");
    }

    /**
     * Returns true if the game lobby exists, the senderName exists and the senderName is not already in the game lobby.
     * Returns false otherwise.
     */
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
            System.err.println(senderName + "does not exist on the server.");
            return false;
        }

        // senderName is not already in the Game Lobby
        if (gameLobby.hasUser(senderName)) {
            System.err.println(senderName + " is already in the Game Lobby " + gameID);
            return false;
        }

        // senderName doesn't already have a color
        Color currentColor = sUser.getColor();
        if (currentColor != null) {
            System.err.println("ChooseBootColorAction: senderName already has a color.");
            return false;
        }

        // the color is not taken yet
        for (ServerUser user : gameLobby.getAllUsers()) {
            Color colorTaken = user.getColor();
            if (colorTaken != null && color.equals(colorTaken.name())) {
                System.err.println("ChooseBootColorAction: the color is already taken");
                return false;
            }
        }
        System.out.println("is valid");
        return true;
    }


    @Override
    public void execute() {
        // add the user to the lobby
        GameLobby gameLobby = GameLobby.getGameLobby(gameID);
        ServerUser sUser = ServerUser.getServerUser(senderName);
        gameLobby.addUser(sUser);

        // set the user's color
        if (color.equals("BLUE")) {
            sUser.setColor(Color.BLUE);
        } else if (color.equals("BLACK")) {
            sUser.setColor(Color.BLACK);
        } else if (color.equals("RED")) {
            sUser.setColor(Color.RED);
        } else if (color.equals("GREEN")) {
            sUser.setColor(Color.GREEN);
        } else if (color.equals("YELLOW")) {
            sUser.setColor(Color.YELLOW);
        } else if (color.equals("PURPLE")) {
            sUser.setColor(Color.PURPLE);
        }

        // notify all users in the lobby
        PlayerHasJoinedACK actionToSend = new PlayerHasJoinedACK(senderName, color);
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
            System.err.println("IOException in PlayerHasJoined.execute().");
        }
    }
    
}
