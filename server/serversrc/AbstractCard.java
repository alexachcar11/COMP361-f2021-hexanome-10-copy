/*
Interface representing a Card.
 */

package serversrc;

import java.io.Serializable;

import org.minueto.MinuetoFileException;
import org.minueto.image.MinuetoImage;
import org.minueto.image.MinuetoImageFile;

public abstract class AbstractCard implements Serializable{

    private CardType type;
    private transient MinuetoImage image;

    AbstractCard(CardType someType, String fileName) {
        type = someType;
        try {
            this.image = new MinuetoImageFile(fileName);
        } catch (MinuetoFileException e) {
            e.printStackTrace();
        }
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

    public MinuetoImage getMinuetoImage() {
        return this.image;
    }
}
