/*
Interface representing a Card.
 */

package serversrc;

public abstract class AbstractCard {

    private CardType type;

    AbstractCard(CardType someType) {
        type = someType;
    }

    @Override
    public boolean equals(Object o) {
        // if compared with itself then true
        if (o == this) {
            return true;
        }
        // check if o is instance of Card
        if (!(o instanceof AbstractCard)) {
            return false;
        }

        // typecast o to Card to compare
        AbstractCard c = (AbstractCard) o;

        // Compare them by name
        return c.getCardType() == this.getCardType();
    }

    private CardType getCardType() {
        return type;
    }
}
