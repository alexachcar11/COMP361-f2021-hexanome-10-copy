package clientsrc;

import org.minueto.MinuetoFileException;
import org.minueto.image.MinuetoImage;
import org.minueto.image.MinuetoImageFile;

import java.io.File;
import java.util.*;

public class Boot extends MinuetoImageFile {

    // idk if we need this
    private Color color;

    /**
     * CONSTRUCTOR : Creates a boot object
     * 
     * @param color color associated with the boot
     */
    public Boot(Color color, int minX, int minY) throws MinuetoFileException {
        super("images/böppels-and-boots/boot-" + color.toString().toLowerCase() + ".png");
        this.color = color;
    }

    public Color getColor() {
        return this.color;
    }
}
