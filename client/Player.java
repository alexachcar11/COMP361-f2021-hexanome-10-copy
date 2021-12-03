
/* This class contains all info relevant to a single Player */
import java.util.*;

public class Player {
    boolean isTurn = false;

    private Client aClient;
    private Boot aBoot;

    int gold;
    Boot boot;
    List<Card> cardsInHand;
    List<Token> tokensInHand;

    private String aName;

    public Player(Client pClient, Color pColor) {
        aClient = pClient;
        aName = aClient.getHost();
        aBoot = new Boot(pColor);
        this.gold = 0;
        this.cardsInHand = new ArrayList<>();
        this.tokensInHand = new ArrayList<>();
    }

    public void setTurn(boolean bool) {
        isTurn = bool;
    }
}
