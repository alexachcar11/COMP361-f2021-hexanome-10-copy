import org.minueto.MinuetoFileException;
import org.minueto.image.MinuetoImage;
import org.minueto.image.MinuetoImageFile;

import java.io.File;
import java.util.*;

public class Boot {
    private int x;
    private int y;
    private MinuetoImage aImage;

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

    public Boot(Color pColor) {
        x = 0;
        y = 0;
        aImage = getBootImage(pColor);
    }

    private static MinuetoImage getBootImage(Color pColor) {
        return BOOT_IMAGES.get(pColor.ordinal());
    }
}
