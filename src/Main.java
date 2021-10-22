import org.minueto.*;
import org.minueto.handlers.*;
import org.minueto.image.*;
import org.minueto.window.*;
import java.util.ArrayList;
import java.util.List;
import java.io.File;

public class Main {

    public static void main(String[] args) {

        // TEMPORARY
        boolean playingElfengold = true;

        // dir containing boot image files
        File bootDir = new File("böppels-and-boots/");

        List<String> bootFileNames = new ArrayList<>();
        // add file names of boot images to the bootFiles list
        for (File file : bootDir.listFiles()) {
            if (file.getName().substring(-3) == "png") {
                bootFileNames.add(file.getName());
            }
        }

        // create window that will contain our game
        MinuetoWindow window = new MinuetoFrame(1024, 768, true);
        MinuetoEventQueue mEventQueue = new MinuetoEventQueue();

        // make images
        MinuetoImage elfenlandImage;
        MinuetoImage elfengoldImage;
        List<MinuetoImage> bootImages = getBootImages(bootFileNames);
        configImages(bootImages);
        try {
            elfengoldImage = new MinuetoImageFile("elfengold.png");
            elfenlandImage = new MinuetoImageFile("elfenland.png");
        } catch (MinuetoFileException e) {
            System.out.println("Could not load image file");
            return;
        }

        // make window visible
        window.setVisible(true);
        // draw on the window
        while (true) {
            if (playingElfengold) {
                window.draw(elfengoldImage, 0, 0);
            } else {
                window.draw(elfenlandImage, 0, 0);
            }
            for (int i = 0; i < bootImages.size(); i++) {
                // not how we actually want to draw them
                window.draw(bootImages.get(i), 300 + 20 * i, 200 + 20 * i);
            }

            window.render();
            Thread.yield();
        }
    }

    private static List<MinuetoImage> getBootImages(List<String> pNames) {
        List<MinuetoImage> toReturn = new ArrayList<>();
        for (String name : pNames) {
            try {
                toReturn.add(new MinuetoImageFile(name)); // name is null, gives exception
            } catch (MinuetoFileException e) {
                System.out.println("Could not load image file " + name);
                e.printStackTrace();
            }
        }
        return toReturn;
    }

    private static void configImages(List<MinuetoImage> pImages) {
        for (int i = 0; i < pImages.size(); i++) {
            MinuetoImage newImage = pImages.get(i);
            newImage.rotate(-90);
            newImage.scale(.125, .125);
            pImages.set(i, newImage);
        }
    }

}
