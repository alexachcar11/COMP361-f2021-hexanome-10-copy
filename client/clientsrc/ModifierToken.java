package clientsrc;

import org.minueto.MinuetoFileException;

import serversrc.Token;

// import serversrc.Token;

// modifier includes the spells and so on for elfenGold and obstacles
// don't think we need this class
public class ModifierToken extends TokenImage {

    // field
    ModifierType aType;

    /**
     * CONSTRUCTOR : Creates a Hitbox object.
     *
     * @param minX  left-most border of the image
     * @param maxX  right-most border of the image
     * @param minY  bottom-most border of the image
     * @param maxY  top-most border of the image
     * @param image MinuetoImage to display
     */
    public ModifierToken(Token token, String url) throws MinuetoFileException {
        super(token, url);
    }

    // get type of modifier
    public ModifierType getModifier() {
        return aType;
    }

}
