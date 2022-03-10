package networksrc;

import org.minueto.MinuetoFileException;

import clientsrc.ClientMain;

public class DealTokenACK implements Action {
    private String tokenString;

    public DealTokenACK(String tokenString) {
        this.tokenString = tokenString;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void execute() {
        System.out.println("BEFORE PHASE TWO");
        try {
            ClientMain.receivePhaseTwo(this.tokenString);
        } catch (MinuetoFileException e) {
            e.printStackTrace();
        }
        System.out.println("AFTER PHASE TWO");
    }

}
