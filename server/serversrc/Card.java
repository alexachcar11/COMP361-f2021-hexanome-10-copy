/*
Interface representing a Card.
 */

package serversrc;

public abstract class Card{

    private String aName;

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

<<<<<<< Updated upstream
    private String getName(){
        return this.aName;
=======
    // public or private idk it says to make it public
    public String getName(){
        return this.aCardType.name();
>>>>>>> Stashed changes
    }

}
