package networksrc;

import serversrc.Color;
import serversrc.GameLobby;
import serversrc.ServerUser;

public class ChooseBootColorAction implements Action {

    private String senderName;
    private String color;
    private String gameID;

    public ChooseBootColorAction(String senderName, String color, String gameID) {
        this.senderName = senderName;
        this.color = color;
        this.gameID = gameID;
    }

    @Override
    public boolean isValid() {
        // null checks
        if (senderName == null) {
            System.err.println("ChooseBootColorAction: senderName cannot be null.");
            return false;
        }
        if (color == null) {
            System.err.println("ChooseBootColorAction: color cannot be null.");
            return false;
        }

        // senderName exists
        ServerUser sUser = ServerUser.getServerUser(senderName);
        if (sUser == null) {
            System.err.println("ChooseBootColorAction: senderName does not exist");
            return false;
        }

        // senderName doesn't already have a color
        Color currentColor = sUser.getColor();
        if (currentColor != null) {
            System.err.println("ChooseBootColorAction: senderName already has a color.");
            return false;
        }

        // the color is not taken yet
        GameLobby gameLobby = GameLobby.getGameLobby(gameID);
        for (ServerUser user : gameLobby.getAllUsers()) {
            Color colorTaken = user.getColor();
            if (colorTaken != null && color.equals(colorTaken.name())) {
                // send ack to the sender only
                ActionManager ackManager = ActionManager.getInstance();
                ChooseBootColorACK actionToSend = new ChooseBootColorACK(color, "already-taken");
                ackManager.sendToSender(actionToSend, senderName);
                return false;
            }
        }
        return true;
    }

    @Override
    public void execute() {
        ServerUser sUser = ServerUser.getServerUser(senderName);

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

        // send ack to the sender only
        ActionManager ackManager = ActionManager.getInstance();
        ChooseBootColorACK actionToSend = new ChooseBootColorACK(color, "success");
        ackManager.sendToSender(actionToSend, senderName);
    }

}
