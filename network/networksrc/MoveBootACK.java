package networksrc;

import org.minueto.MinuetoFileException;

import clientsrc.*;

// updates state of boot on GUI for clients
public class MoveBootACK implements Action {

    private String newTown;
    private String playerThatMovedName;

    public MoveBootACK(String newTown, String playerThatMovedName) {
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
        ClientPlayer p = ClientMain.currentPlayer;
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
