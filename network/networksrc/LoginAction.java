package networksrc;


import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import serversrc.Registrator;
import serversrc.ServerUser;

public class LoginAction implements Action {

    private String username;
    private String password;

    public LoginAction(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public boolean isValid() {

        // null check
        if (username == null) {
            System.err.println("username cannot be null");
            return false;
        } else if (password == null) {
            System.err.println("password cannot be null");
            return false;
        }

        // check that the user exists
        Registrator REGISTRATOR = Registrator.instance();
        if (!REGISTRATOR.userExists(username)) {
            LoginACK action = new LoginACK(username, "user-dne");
            ACKManager.getInstance().sendToSender(action, username);
            return false;
        }

        return true;
    }

    @Override
    public void execute() {
        
        
        try {
            Registrator REGISTRATOR = Registrator.instance();
            // create a token
            JSONObject token = REGISTRATOR.createToken(username, password);

            // create a ServerUser
            new ServerUser(username, token);

            // send ACK to sender
            LoginACK action = new LoginACK(username, "success");
            ACKManager.getInstance().sendToSender(action, username);

        } catch (IllegalAccessException e) {
            LoginACK action = new LoginACK(username, "wrong-pw");
            ACKManager.getInstance().sendToSender(action, username);
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        
    }
    
}
