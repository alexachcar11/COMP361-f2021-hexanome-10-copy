package serversrc;

public class GoldCard extends AbstractCard{

    public GoldCard() {
        super(CardType.GOLD, "images/elfenroads-sprites/Gold.png");
    }

    // for each Gold Card, they give player 3 gold if they choose the gold card
    // stack
    public int getGold() {
        return 3;
    }
}
