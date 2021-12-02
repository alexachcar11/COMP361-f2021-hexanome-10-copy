/*
Interface representing a Card.
 */

import org.minueto.image.MinuetoImage;

public abstract class Card {

    MinuetoImage image;
    Hitbox hitbox;

    /**
     * CONSTRUCTOR : Creates a Card object
     * @param image MinuetoImage associated with the Card
     * @param hitbox Hitbox describing the location of the Card
     */
    public Card(MinuetoImage image, Hitbox hitbox) {
        this.image = image;
        this.hitbox = hitbox;
    }
}
