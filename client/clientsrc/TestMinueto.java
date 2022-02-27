package clientsrc;

import org.minueto.*;
import org.minueto.image.*;
import org.minueto.window.*;
import java.io.IOException;

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

            while (true) {
                window.draw(rectangle, 10, 10);
                window.draw(demoImage, 20,20);


                window.render();
                Thread.yield();
            }
            // MINUETO TESTING END
        }
}
