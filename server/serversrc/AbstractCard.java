package serversrc;

public abstract class AbstractCard {
    CardType aType;

    public String getName(){
        return aType.name();
    }
}
