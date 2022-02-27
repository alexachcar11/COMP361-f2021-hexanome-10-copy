package networksrc;

import serversrc.ServerUser;

public class userConnectionAction extends Action{

    private Client client;
    private String name;

    public userConnectionAction(Client client, String name) {
        this.client = client;
        this.name = name;
    }

    @Override
    public boolean isValid() {
        // TODO
        return true;
    }

    @Override
    public void execute() {
        if (isValid()) {
            // On the server: create User object related to the given client
            ServerUser newUser = new ServerUser(client, name);
        } else {
            System.err.println("clientConnectionAction is not valid.");
        }
    }
}
