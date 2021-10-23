/* This class contains all info relevant to the game window*/
import org.minueto.window.MinuetoWindow;

public class GameWindow {

    enum Screen {
        ENTRY,
        LOGIN,
        ELFENLAND,
        ELFENGOLD
    }

    MinuetoWindow window;
    Screen currentlyShowing;

    GameWindow(MinuetoWindow window, Screen currentlyShowing) {
        this.window = window;
        this.currentlyShowing = currentlyShowing;
    }

}
