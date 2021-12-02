/* This class contains all info relevant to the game window*/
import org.minueto.window.MinuetoWindow;

public class GUI {

    enum Screen {
        MENU,
        LOGIN,
        LOBBY,
        ELFENLAND,
        ELFENGOLD
    }

    MinuetoWindow window;
    Screen currentBackground;

    GUI(MinuetoWindow window, Screen currentBackground) {
        this.window = window;
        this.currentBackground = currentBackground;
    }

}
