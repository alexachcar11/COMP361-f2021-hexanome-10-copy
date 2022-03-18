package networksrc;

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
        Player p = ClientMain.currentPlayer;
        Town t = Game.getTownByName(TargetTown);
        p.setTargetDestinationTown(t);
        
    }
    
}
