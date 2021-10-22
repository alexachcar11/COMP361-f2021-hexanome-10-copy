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


/* Lilia's temp note:
   Run main and you can click on Quit to quit the program with exit code 0 (success).
   Run main and you can click on Play to draw the login screen. However it only draws 1 frame and then keeps the play screen as usual

   Problem: cannot modify the boolean inPlayScreen and inLoginScreen in the MinuetoMouseHandler class.

   Solution: perhaps create a new class for our window and assign booleans to instances of that class?

 */
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
        boolean inPlayScreen = true;
        boolean inLoginScreen = false;
        boolean playingElfengold = false;
        boolean playingElfenland = false;

        // create window that will contain our game
        MinuetoWindow window = new MinuetoFrame(1024, 768, true);

        // make images
        MinuetoImage elfenlandImage;
        MinuetoImage elfengoldImage;
        MinuetoImage boot_black;
        MinuetoImage playScreenImage;
        MinuetoImage loginScreenImage;
        try {
            elfengoldImage= new MinuetoImageFile("elfengold.png");
            elfenlandImage= new MinuetoImageFile("elfenland.png");
            boot_black= new MinuetoImageFile("boot-black.png");
            playScreenImage = new MinuetoImageFile("play.png");
            loginScreenImage = new MinuetoImageFile("login.png");
        } catch(MinuetoFileException e) {
            System.out.println("Could not load image file");
            return;
        }

        // event queue
        MinuetoEventQueue moveBootMouseQueue = new MinuetoEventQueue();
        MinuetoEventQueue playGameMouseQueue = new MinuetoEventQueue();
        // sets up mouse handler inputs
        window.registerMouseHandler(new MouseHandler_MovingBoot(), moveBootMouseQueue);
        window.registerMouseHandler(new MinuetoMouseHandler() {
            @Override
            public void handleMousePress(int x, int y, int button) {
                if (x <= 665 && x >= 360 && y >= 345 && y <= 445) {
                    System.out.println("CLICKED PLAY");
                    window.draw(loginScreenImage, 0, 0);
                }
                if (x <= 665 && x >= 360 && y >= 530 && y <= 640) {
                    System.exit(0);
                }
            }

            @Override
            public void handleMouseRelease(int i, int i1, int i2) {
                // Do nothing
            }

            @Override
            public void handleMouseMove(int i, int i1) {
                // Do nothing
            }
        }, playGameMouseQueue);

        // set max frame rate
        window.setMaxFrameRate(60);
        // set cursor visible
        window.setCursorVisible(true);


        // make window visible
        window.setVisible(true);
        // set Title of window
        window.setTitle("GAME BOARD");

        // draw on the window
        while(true) {
            if (inPlayScreen) {
                window.draw(playScreenImage, 0, 0);
                // executes events from the event queue
                while (playGameMouseQueue.hasNext()) {
                    playGameMouseQueue.handle();
                }
            } else if (playingElfengold) {
                window.draw(elfengoldImage, 0, 0);
                // draw the boot on map
                window.draw(boot_black,0,0);
                // executes events from the event queue
                while (moveBootMouseQueue.hasNext()) {
                    moveBootMouseQueue.handle();
                }
            } else if (playingElfenland) {
                window.draw(elfenlandImage, 0, 0);
                // draw the boot on map
                window.draw(boot_black,0,0);
                // executes events from the event queue
                while (moveBootMouseQueue.hasNext()) {
                    moveBootMouseQueue.handle();
                }
            }

            window.render();
            Thread.yield();
        }

    }
}
