import org.minueto.MinuetoFileException;
import org.minueto.image.MinuetoImage;
import org.minueto.image.MinuetoImageFile;

import java.io.File;
import java.util.*;

public class Boot extends Image{

    Color color;

    /**
     * CONSTRUCTOR : Creates a boot object
     * 
     * @param color color associated with the boot
     */
    public Boot(Color color, int minX, int maxX, int minY, int maxY) {
        super(minX, maxX, minY, maxY, Boot.getBootImage(color));
        this.color = color;
        // TODO: fix these values
    }

    // TODO: write documentation for this method
    private static final List<MinuetoImage> BOOT_IMAGES = new ArrayList<>();
    {
        File bootDir = new File("images/böppels-and-boots/");
        List<String> bootFileNames = new ArrayList<>();

        for (File file : bootDir.listFiles()) {
            if (file.getName().startsWith("boot-"))
                bootFileNames.add("images/böppels-and-boots/" + file.getName());
        }

        for (String name : bootFileNames) {
            try {
                BOOT_IMAGES.add(new MinuetoImageFile(name)); // name is null, gives exception
            } catch (MinuetoFileException e) {
                System.out.println("Could not load image file " + name);
                e.printStackTrace();
            }
        }
    }

    /**
     * GETTER : Returns the MinuetoImage associated with this pColor
     * 
     * @param pColor color of the boot
     * @return MinuetoImage associated with pColor
     */
    public static MinuetoImage getBootImage(Color pColor) {
        return BOOT_IMAGES.get(pColor.ordinal());
    }
}
