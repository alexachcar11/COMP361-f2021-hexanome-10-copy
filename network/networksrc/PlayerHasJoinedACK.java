package networksrc;

import org.minueto.MinuetoFileException;

import clientsrc.ClientMain;

public class PlayerHasJoinedACK implements Action {

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void execute() {
        try {
            ClientMain.displayUsers();
            System.out.println("displaying users.");
        } catch (MinuetoFileException e) {
            e.printStackTrace();
        }
    }
    
}
