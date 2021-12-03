/* This class contains all info relevant to a single Player */
import java.util.*;

public class Player {

    boolean isTurn = false;
    private Client aClient;
    private Boot aBoot;
    
    int gold;
    GUI guiDisplayed; // TODO: initialize this
    Boot boot;
    List<Card> cardsInHand;
    List<Token> tokensInHand;

    private String aName;

    public Player(Client pClient, Color pColor) {
        aClient = pClient;
        aName = aClient.getHost();
        aBoot = new Boot(pColor);
        this.gold = 0;
        // TODO: guiDisplayed
        this.cardsInHand = new ArrayList<>();
        this.tokensInHand = new ArrayList<>();
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
    public void setTurn(boolean bool) {
        isTurn = bool;
    }
}
