import org.minueto.image.MinuetoImage;

/* This class contains all info relevant to a single Player */

public class Player {
    MinuetoImage icon;
    int xPos;
    int yPos;
    boolean isTurn = false;

    public Player(MinuetoImage pIcon, int x, int y) {
        icon = pIcon;
        xPos = x;
        yPos = y;
    }

    public void moveBoot(int x, int y) {
        xPos = x;
        yPos = y;
    }

    public MinuetoImage getIcon() {
        return icon;
    }

    public int getxPos() {
        return xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public boolean isTurn() {
        return isTurn;
    }
}
