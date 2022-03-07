package serversrc;

// import clientsrc.Card;
// import clientsrc.CardType;

public class GoldCard extends Card {

    public GoldCard() {
        super(CardType.GOLD);
    }

    // for each Gold Card, they give player 3 gold if they choose the gold card
    // stack
    public int getGold() {
        return 3;
    }
}
