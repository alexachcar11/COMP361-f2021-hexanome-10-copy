import org.minueto.image.MinuetoImage;

/* This class contains all info relevant to a single Player */

public class Player {

    boolean isTurn = false;
    private Client aClient;
    private Boot aBoot;

    private String aName;

    public Player(Client pClient, Color pColor) {
        aClient = pClient;
        aName = aClient.getHost();
        aBoot = new Boot(pColor);
    }

    public void moveBoot(int x, int y) {
        aBoot.move(x, y);
    }

    public MinuetoImage getIcon() {
        return aBoot.getImage();
    }

    public int[] getCoords() {
        return aBoot.getCoords();
    }

    public int getxPos() {
        return this.getCoords()[0];
    }

    public int getyPos() {
        return this.getCoords()[1];
    }

    public boolean isTurn() {
        return isTurn;
    }

    public void setTurn(boolean bool) {
        isTurn = bool;
    }
}
