package clientsrc;

import org.minueto.*;
import org.minueto.handlers.MinuetoMouseHandler;
import org.minueto.image.*;
import org.minueto.window.*;
import java.io.IOException;
import org.minueto.handlers.MinuetoMouseHandler;

public class TestMinueto {


        public static void main(String[] args) throws IOException {

            // MINUETO TESTING
            MinuetoWindow window;
            MinuetoImage demoImage;
            MinuetoImage menuPopup;
            menuPopup = new MinuetoImageFile("images/menuPopup.png").scale(.6,.6);


            MinuetoRectangle rectangle = new MinuetoRectangle(1000, 1000, MinuetoColor.BLUE, true);

            window = new MinuetoFrame(1024, 768, true);
            window.setMaxFrameRate(60);


            // create entry screen mouse handler
            MinuetoEventQueue entryScreenQueue = new MinuetoEventQueue();
            window.registerMouseHandler(entryScreenMouseHandler, entryScreenQueue);

            try {
                demoImage = new MinuetoImageFile("images/elfenland.png");
            } catch (MinuetoFileException e) {
                System.out.println("Could not load image file");
                return;
            }

            window.setVisible(true);

            while (true) {
                //window.draw(rectangle, 10, 10);
                window.draw(demoImage,0 ,0);
                window.draw(menuPopup,420,300);
                //window.draw(menuPopup,359,262);
                //window.draw(menuPopup,532,384);
                while (entryScreenQueue.hasNext()){
                    entryScreenQueue.handle();
                }

                window.render();
                Thread.yield();
            }

            // MINUETO TESTING END
        }
    static MinuetoMouseHandler entryScreenMouseHandler = new MinuetoMouseHandler() {


        @Override
        public void handleMousePress(int x, int y, int button) {
            /*
            MinuetoImage menuPopup;
            menuPopup = new MinuetoImageFile("images/menuPopup.png").scale(.5,.5);
            MinuetoWindow window;
            window = new MinuetoFrame(1024, 768, true);*/

            //gui.currentBackground = GUI.Screen.LOGIN;
                //gui.currentBackground = GUI.Screen.ELFENLAND;
                System.out.println("This is x: " + x + ". This is y: " + y);
            // open menu
            if (x>=11 && x<=58 && y>=15 && y<=61){

                // save game
                if (x>=435 && x<=585 && y>=378 && y<=410){
                    // do save game stuff
                }

                // quit game
                if (x>=435 && x<=585 && y>=436 && y<=470){
                    // do quit game stuff
                }

                // close menu
                if (x>=420 && x<=437 && y>=300 && y<=317){
                    //close menu stuff
                    // menuPopupActive = false
                }

                //MinuetoWindow windowClone = gui.window.clone();

                //window.draw(menuPopup,448,300);


            }




        }

        @Override
        public void handleMouseRelease(int i, int i1, int i2) {

        }

        @Override
        public void handleMouseMove(int i, int i1) {

        }

    };
}
