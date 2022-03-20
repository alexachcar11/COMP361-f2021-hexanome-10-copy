package networksrc;

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
        } else if (result.equals("user-dne")) {
            // the next attempt to login will reset the Client name
            ClientMain.clientNeedsNewName = true;
            // TODO : display user does not exist message on screen
        } else if (result.equals("wrong-pw")) {
            // TODO : display error message on screen
        }
        
    }
    
}
