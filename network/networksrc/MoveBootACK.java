package networksrc;

import clientsrc.*;

// updates state of boot on GUI for clients
public class MoveBootACK implements Action {

    private String newTown;
    private String playerThatMovedName;
    

    public MoveBootACK(String newTown, String playerThatMovedName){
        this.newTown = newTown;
        this.playerThatMovedName = playerThatMovedName;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void execute() {
        System.out.println("MoveBootACK received");

        // who moved their boot
        Player p = Player.getPlayerByName(playerThatMovedName);

        // move the boot
        Town t = Town.getTownByName(newTown);
        p.moveBoot(t);
    }

}
