package networksrc;

import org.minueto.MinuetoFileException;

import clientsrc.*;

// updates state of boot on GUI for clients
public class MoveBootACK implements Action {

    private String newTown;
    private String playerThatMovedName;
    private String aCardType;
    private int aCost;

    public MoveBootACK(String newTown, String playerThatMovedName, String pCardType, int pCost) {
        this.newTown = newTown;
        this.playerThatMovedName = playerThatMovedName;
        this.aCardType = pCardType;
        this.aCost = pCost;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void execute() {
        System.out.println("MoveBootACK received");
        ClientPlayer p = ClientPlayer.getPlayerByName(playerThatMovedName);
        // TODO: call a method that updates GUI by changing the boot with bootColor to
        // newTown
        // get town by name
        ClientTown t = Game.getTownByName(newTown);
        p.moveBoot(t);

        // display
        try {
            ClientMain.displayBoardElements();
        } catch (MinuetoFileException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
