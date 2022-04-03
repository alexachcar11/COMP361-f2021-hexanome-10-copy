package clientsrc;

import org.minueto.*;
import org.minueto.handlers.MinuetoMouseHandler;
import org.minueto.image.*;
import org.minueto.window.*;

import networksrc.Client;
import networksrc.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class TestMinueto {

    public static void main(String[] args) throws IOException {
        // MINUETO TESTING
        MinuetoWindow window;

        MinuetoImage elfenlandImage;
        MinuetoEventQueue eventQueue;

        window = new MinuetoFrame(1024, 768, true);

        try {
            elfenlandImage = new MinuetoImageFile("images/elfenland.png");
        } catch (MinuetoFileException e) {
            System.out.println("Could not load image file");
            return;
        }

        window.setVisible(true);

        window.draw(elfenlandImage, 0, 0);

        MinuetoCircle indicator = new MinuetoCircle(10, MinuetoColor.GREEN, true);
        window.draw(indicator, 90, 55);
        window.draw(indicator, 38, 189);
        window.draw(indicator, 169, 126);
        window.draw(indicator, 121, 162);
        window.draw(indicator, 45, 318);
        window.draw(indicator, 78, 307);
        window.draw(indicator, 119, 231);
        window.draw(indicator, 125, 282);
        window.draw(indicator, 246, 130);
        window.draw(indicator, 259, 210);
        window.draw(indicator, 194, 424);
        window.draw(indicator, 165, 510);
        window.draw(indicator, 283, 442);
        window.draw(indicator, 378, 545);
        window.draw(indicator, 279, 57);
        window.draw(indicator, 381, 199);
        window.draw(indicator, 241, 342);
        window.draw(indicator, 354, 401);
        window.draw(indicator, 368, 462);
        window.draw(indicator, 451, 467);
        window.draw(indicator, 563, 431);
        window.draw(indicator, 577, 483);
        window.draw(indicator, 584, 557);
        window.draw(indicator, 728, 489);
        window.draw(indicator, 620, 171);
        window.draw(indicator, 726, 373);
        window.draw(indicator, 635, 252);
        window.draw(indicator, 49, 450);
        window.draw(indicator, 244, 551);
        window.draw(indicator, 621, 423);
        window.draw(indicator, 443, 219);
        window.draw(indicator, 376, 255);
        window.draw(indicator, 302, 311);
        window.draw(indicator, 699, 395);
        window.draw(indicator, 488, 80);
        window.draw(indicator, 383, 131);
        window.draw(indicator, 302, 313);
        window.draw(indicator, 555, 141);
        window.draw(indicator, 717, 291);
        window.draw(indicator, 450, 339);
        window.draw(indicator, 489, 254);
        window.draw(indicator, 526, 390);
        window.draw(indicator, 364, 315);
        window.draw(indicator, 687, 174);
        window.draw(indicator, 510, 310);
        window.draw(indicator, 149, 102);
        window.draw(indicator, 533, 185);
        window.draw(indicator, 438, 549);
        window.draw(indicator, 536, 185);
        window.draw(indicator, 88, 439);

        window.render();

        MinuetoMouseHandler handler = new MinuetoMouseHandler() {

            @Override
            public void handleMouseMove(int arg0, int arg1) {
                // TODO Auto-generated method stub

            }

            @Override
            public void handleMousePress(int arg0, int arg1, int arg2) {
                // Thread testThread = new Thread(() -> System.out.println("Hello"));
                // testThread.start();
                System.out.println("x coord is : " + arg0 + ", and the y coord is : " + arg1 );

            }

            @Override
            public void handleMouseRelease(int arg0, int arg1, int arg2) {
                // TODO Auto-generated method stub

            }

        };

        eventQueue = new MinuetoEventQueue();
        window.registerMouseHandler(handler, eventQueue);

        while(true){ 
            while (eventQueue.hasNext()) {
                eventQueue.handle();
            }
        }
        // TestServer server = new TestServer();
        // TestClient client = new TestClient();

        // MINUETO TESTING END
    }

}
