package src;
import org.minueto.image.MinuetoImage;

// modifier includes the spells and so on for elfenGold and obstacles
public class ModifierToken extends Token {

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
    public ModifierToken(int minX, int maxX, int minY, int maxY, MinuetoImage image, ModifierType pType) {
        super(minX, maxX, minY, maxY, image);
        aType = pType;
    }

    // get type of modifier
    public ModifierType getModifier(){
        return aType;
    }
    
}
