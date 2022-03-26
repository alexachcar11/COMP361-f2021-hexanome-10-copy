package networksrc;

import org.minueto.MinuetoColor;
import org.minueto.image.MinuetoText;

import clientsrc.ClientMain;
import clientsrc.User;

public class LoginACK implements Action {

    private String username;
    private String result;

    public LoginACK(String username, String result) {
        this.username = username;
        this.result = result;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void execute() {
        if (result.equals("success")) {
            // create a new User
            User newUser = new User(username);
            ClientMain.currentUser = newUser;
            // gui updates
            ClientMain.displayAvailableGames();
        } else if (result.equals("invalid-credentials")) {
            // the next attempt to login will reset the Client name
            ClientMain.clientNeedsNewName = true;
            // display error message to the user
            String invalidText = "Invalid Credentials";
            MinuetoText usernameFailed = new MinuetoText(invalidText, ClientMain.fontArial20, MinuetoColor.RED);
            ClientMain.gui.window.draw(usernameFailed, 200, 360);
            MinuetoText passwordFailed = new MinuetoText(invalidText, ClientMain.fontArial20, MinuetoColor.RED);
            ClientMain.gui.window.draw(passwordFailed, 200, 450);
        }
        
    }
    
}
