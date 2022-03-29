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
        MinuetoImage demoImage;

        MinuetoRectangle rectangle = new MinuetoRectangle(1000, 1000, MinuetoColor.BLUE, true);

        window = new MinuetoFrame(1024, 768, true);
        window.setMaxFrameRate(60);

        try {
            demoImage = new MinuetoImageFile("images/böppels-and-boots/boot-black.png");
        } catch (MinuetoFileException e) {
            System.out.println("Could not load image file");
            return;
        }

        window.setVisible(true);

        window.draw(rectangle, 10, 10);
        window.draw(demoImage, 20, 20);

        window.render();

        MinuetoMouseHandler handler = new MinuetoMouseHandler() {

            @Override
            public void handleMouseMove(int arg0, int arg1) {
                // TODO Auto-generated method stub

            }

            @Override
            public void handleMousePress(int arg0, int arg1, int arg2) {
                Thread testThread = new Thread(() -> System.out.println("Hello"));
                testThread.start();

            }

            @Override
            public void handleMouseRelease(int arg0, int arg1, int arg2) {
                // TODO Auto-generated method stub

            }

        };
        // TestServer server = new TestServer();
        // TestClient client = new TestClient();

        // MINUETO TESTING END
    }

}
