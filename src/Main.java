import org.minueto.*;
import org.minueto.handlers.*;
import org.minueto.image.*;
import org.minueto.window.*;
// Dijian's temp note:
// run main and you can click on the window that will print the mouse button and the x, y in console
// problem:     draw boot repeatedly in the while loop and when player clicks on map it should redraw boot
//              at position x, y where mouse click was detected.
// solution:    in the main while loop, redraw the background everytime and update position of boot
//              that needs to be drawn when player clicks on map
//
//              currently I think everything is set up except for how to use the x, y from the mouse input
//              to redraw the boot on the map, I've tried different ways but nothing worked so far.

class MouseHandler_MovingBoot implements MinuetoMouseHandler {

    @Override
    public void handleMousePress(int x, int y, int button) {
        System.out.println("Mouse click on button " + button + " detected at " + x + "," + y);
    }
    @Override
    public void handleMouseRelease(int i, int i1, int i2) {
        // do nothing
    }
    @Override
    public void handleMouseMove(int i, int i1) {
        // do nothing
    }
}
public class Main {

    public static void main(String[] args) {

        // TEMPORARY
        boolean playingElfengold = true;

        // create window that will contain our game
        MinuetoWindow window = new MinuetoFrame(1024, 768, true);
        // event queue
        MinuetoEventQueue queue = new MinuetoEventQueue();
        // sets up mouse handler inputs
        window.registerMouseHandler(new MouseHandler_MovingBoot(), queue);

        // set max frame rate
        window.setMaxFrameRate(60);
        // set cursor visible
        window.setCursorVisible(true);

        // make images
        MinuetoImage elfenlandImage;
        MinuetoImage elfengoldImage;
        MinuetoImage boot_black;
        try {
            elfengoldImage= new MinuetoImageFile("elfengold.png");
            elfenlandImage= new MinuetoImageFile("elfenland.png");
            boot_black= new MinuetoImageFile("boot-black.png");

        } catch(MinuetoFileException e) {
            System.out.println("Could not load image file");
            return;
        }
        // make window visible
        window.setVisible(true);
        // set Title of window
        window.setTitle("GAME BOARD");

        // draw on the window
        while(true) {
            if (playingElfengold) {
                window.draw(elfengoldImage, 0, 0);
            } else {
                window.draw(elfenlandImage, 0, 0);
            }
            // draw the boot on map
            window.draw(boot_black,0,0);

            // executes events from the event queue
            while (queue.hasNext()) {
                queue.handle();
            }

            window.render();
            Thread.yield();
        }

    }
}
