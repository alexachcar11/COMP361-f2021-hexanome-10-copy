package networksrc;

import serversrc.ServerUser;

public class UserConnectionAction extends Action{

    private String name;

    public UserConnectionAction(Client client, String name) {
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
        } else {
            System.err.println("clientConnectionAction is not valid.");
        }
    }
}
