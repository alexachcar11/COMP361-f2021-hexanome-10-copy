import org.minueto.*;
import org.minueto.handlers.*;
import org.minueto.image.*;
import org.minueto.window.*;

public class Main {

    public static void main(String[] args) {

        // TEMPORARY
        boolean playingElfengold = true;

        // create window that will contain our game
        MinuetoWindow window = new MinuetoFrame(1024, 768, true);
        window.setMaxFrameRate(60);

        // make images
        MinuetoImage elfenlandImage;
        MinuetoImage elfengoldImage;
        try {
            elfengoldImage= new MinuetoImageFile("elfengold.png");
            elfenlandImage= new MinuetoImageFile("elfenland.png");
        } catch(MinuetoFileException e) {
            System.out.println("Could not load image file");
            return;
        }

        // make window visible
        window.setVisible(true);
        // draw on the window
        while(true) {
            if (playingElfengold) {
                window.draw(elfengoldImage, 0, 0);
            } else {
                window.draw(elfenlandImage, 0, 0);
            }

            window.render();
            Thread.yield();
        }
    }

}
