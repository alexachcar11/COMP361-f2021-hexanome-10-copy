/*
Interface representing a Card.
 */

package src;

import org.minueto.image.MinuetoImage;

public abstract class Card extends Image{

    private String aName;

    /**
     * CONSTRUCTOR : Creates a Hitbox object.
     *
     * @param minX  left-most border of the image
     * @param maxX  right-most border of the image
     * @param minY  bottom-most border of the image
     * @param maxY  top-most border of the image
     * @param image MinuetoImage to display
     */
    public Card(int minX, int maxX, int minY, int maxY, MinuetoImage image, String name) {
        super(minX, maxX, minY, maxY, image);
        // name of card
        aName = name;
    }

    @Override
    public boolean equals(Object o){
        // if compared with itself then true
        if (o == this) {
            return true;
        }
        // check if o is instance of Card
        if (!(o instanceof Card)){
            return false;
        }

        // typecast o to Card to compare
        Card c = (Card) o;

        // Compare them by name
        return c.getName().equalsIgnoreCase(this.aName);
    }

    private String getName(){
        return this.aName;
    }
}
