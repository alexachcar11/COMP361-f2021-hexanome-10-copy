import org.minueto.*;
import org.minueto.handlers.*;
import org.minueto.image.*;
import org.minueto.window.*;


public class TestMinueto {

    public static void main(String[] args) {
        MinuetoWindow window;
        MinuetoImage demoImage;

        window = new MinuetoFrame(640, 480, true);
        window.setMaxFrameRate(60);

        try {
            demoImage = new MinuetoImageFile("böppels-and-boots/boot-black.png");
        } catch(MinuetoFileException e) {
            System.out.println("Could not load image file");
            return;
        }

        window.setVisible(true);

        while(true) {
            window.draw(demoImage, 0, 0);
            window.render();
            Thread.yield();
        }
    }

}
