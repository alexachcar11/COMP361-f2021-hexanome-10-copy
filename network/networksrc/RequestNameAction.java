package networksrc;

import clientsrc.ClientMain;

public class RequestNameAction extends Action{

    @Override
    public boolean isValid() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void execute() {
        String name = ClientMain.currentUser.getName();
        // client-side retrieves it's name and sends it to the server
        GiveNameAction giveNameAction = new GiveNameAction(name);
        
    }
    
}
