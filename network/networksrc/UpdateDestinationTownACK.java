package networksrc;

import org.minueto.MinuetoFileException;

import clientsrc.*;

public class UpdateDestinationTownACK implements Action{

    private String TargetTown;

    public UpdateDestinationTownACK(String town){
        this.TargetTown = town;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void execute() {
        System.out.println("UpdateDestinationTownACK received");
        ClientPlayer p = ClientMain.currentPlayer;
        ClientTown t = Game.getTownByName(TargetTown);
        p.setTargetDestinationTown(t);
        // display
        try {
            ClientMain.displayBoardElements();
        } catch (MinuetoFileException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
}
