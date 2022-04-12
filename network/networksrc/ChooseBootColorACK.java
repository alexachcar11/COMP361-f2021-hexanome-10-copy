package networksrc;

import org.minueto.MinuetoColor;
import org.minueto.MinuetoFileException;
import org.minueto.image.MinuetoText;

import clientsrc.ClientMain;
import clientsrc.Color;

public class ChooseBootColorACK implements Action {

    private String color;
    private String result;

    public ChooseBootColorACK(String color, String result) {
        this.color = color;
        this.result = result;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void execute() {
        if (result.equals("success")) {
            // assign the color
            if (color.equals("BLUE")) {
                ClientMain.currentUser.setColor(Color.BLUE);
            } else if (color.equals("BLACK")) {
                ClientMain.currentUser.setColor(Color.BLACK);
            } else if (color.equals("RED")) {
                ClientMain.currentUser.setColor(Color.RED);
            } else if (color.equals("GREEN")) {
                ClientMain.currentUser.setColor(Color.GREEN);
            } else if (color.equals("YELLOW")) {
                ClientMain.currentUser.setColor(Color.YELLOW);
            } else if (color.equals("PURPLE")) {
                ClientMain.currentUser.setColor(Color.PURPLE);
            } 
            // display users
            try {
                ClientMain.displayUsers();
            } catch (MinuetoFileException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            // display game info
            ClientMain.displayLobbyInfo();
        } else if (result.equals("already-taken")) {
            // get available colors
            ClientMain.ACTION_MANAGER.sendAction(new GetAvailableColorsAction(ClientMain.currentUser.getName(), ClientMain.currentSession.getSessionID()));
            // display error message
            MinuetoText errorText = new MinuetoText("Please select a color.", ClientMain.fontArial22Bold, MinuetoColor.RED);
            ClientMain.gui.window.draw(errorText, 378, 526);
        }
    }
    
}
