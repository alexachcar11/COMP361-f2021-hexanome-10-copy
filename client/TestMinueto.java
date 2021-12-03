import org.minueto.*;
import org.minueto.handlers.*;
import org.minueto.image.*;
import org.minueto.window.*;

public class TestMinueto {

    public static void main(String[] args) {
        MinuetoWindow window;
        MinuetoImage demoImage;

        MinuetoRectangle rectangle = new MinuetoRectangle(50, 1000, MinuetoColor.WHITE, true);

        window = new MinuetoFrame(640, 480, true);
        window.setMaxFrameRate(60);

        try {
            demoImage = new MinuetoImageFile("böppels-and-boots/boot-black.png");
        } catch (MinuetoFileException e) {
            System.out.println("Could not load image file");
            return;
        }

        window.setVisible(true);

        while (true) {
            window.draw(rectangle, 10, 10);
            window.render();
            Thread.yield();
        }
    }

}
