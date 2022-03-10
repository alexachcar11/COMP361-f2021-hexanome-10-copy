package networksrc;

<<<<<<< HEAD
=======
import org.minueto.MinuetoColor;
import org.minueto.MinuetoFileException;

>>>>>>> 7cbbf56517982ed252e421f4aeb47415372c5b5e
import clientsrc.ClientMain;

public class WinnerACK implements Action {

    private String winnerName;

    public WinnerACK(String name) {
        this.winnerName = name;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void execute() {
        // display winner
<<<<<<< HEAD
        /* try {
            ClientMain.displayWinnerByString(winnerName);
        } catch (MinuetoFileException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } */
=======
        // try {
        // ClientMain.displayWinnerByString(winnerName);
        // } catch (MinuetoFileException e) {
        // // TODO Auto-generated catch block
        // e.printStackTrace();
        // }
>>>>>>> 7cbbf56517982ed252e421f4aeb47415372c5b5e
    }

}
