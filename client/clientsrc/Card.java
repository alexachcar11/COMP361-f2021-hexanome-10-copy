/*
Interface representing a Card.
 */

package clientsrc;

import org.minueto.image.MinuetoImage;

// import serversrc.Card;

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
        // TODO: make sure is consistent w CardType enum in serversrc
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

    public String getName(){
        return this.aName;
    }

    /** DID NOT NEED THIS. USED Game.getFaceDownCard(cardString) directly from game in Player
    public Card getCardByName(String cardString){
        return Game.getFaceDownCard(cardString);
    } **/
}
