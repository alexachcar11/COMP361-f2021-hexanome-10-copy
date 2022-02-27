package src;
public class GoldCard extends Card{

    //field
    CardType aType = CardType.GOLD;


    // for each Gold Card, they give player 3 gold if they choose the gold card stack
    public int getGold(){
        return 3;
    }
}
