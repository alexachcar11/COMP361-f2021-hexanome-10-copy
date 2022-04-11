/*
Interface representing a Card.
 */

package serversrc;

import java.io.Serializable;

public abstract class AbstractCard implements Serializable {

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

    public CardType getCardType() {
        return type;
    }
}
