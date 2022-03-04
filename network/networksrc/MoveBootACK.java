package networksrc;

import clientsrc.*;


// updates state of boot on GUI for clients
public class MoveBootACK implements Action{

    private String newTown;
    private String bootColor;

    public MoveBootACK(String newTown, String bootColor){
        this.newTown = newTown;
        this.bootColor = bootColor;
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
        ClientMain.moveBoot(String bootColor, String toTown);
    }
    
}
