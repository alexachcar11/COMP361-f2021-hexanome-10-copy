package networksrc;

import clientsrc.*;


// updates state of boot on GUI for clients
public class MoveBootACK implements Action{

    private String newTown;
    

    public MoveBootACK(String newTown){
        this.newTown = newTown;
    }
    
    @Override
    public boolean isValid() {
        return true;
    }
    
    @Override
    public void execute() {
        System.out.println("MoveBootACK received");
        Player p = ClientMain.currentPlayer;
        Game g = ClientMain.currentGame;
        // TODO: call a method that updates GUI by changing the boot with bootColor to newTown
        // get town by name
        Town t = g.getTownByName(newTown);
        p.moveBoot(t);
    }
    
}
