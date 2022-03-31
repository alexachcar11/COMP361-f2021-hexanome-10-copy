package networksrc;

import java.util.List;
import java.util.stream.Collectors;

import org.minueto.MinuetoEventQueue;
import org.minueto.MinuetoFileException;
import org.minueto.handlers.MinuetoMouseHandler;
import org.minueto.window.MinuetoWindow;

import clientsrc.ClientMain;
import clientsrc.TokenSprite;

public class DisplayPhaseThreeACK implements Action{

    private List<String> faceUpCopy;

    public DisplayPhaseThreeACK(List<String> faceUpCopy) {
        this.faceUpCopy = faceUpCopy;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public void execute() {
        MinuetoWindow window = ClientMain.WINDOW;
        final List<TokenSprite> tokenImages = faceUpCopy.stream().map((s) -> {
            try {
                return TokenSprite.getTokenSpriteByString(s);
            } catch (MinuetoFileException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
            return null;
        })
                .collect(Collectors.toList());
        int count = 0;
        for (TokenSprite tImage : tokenImages) {
            // change these coords
            tImage.setPos(200 + count * 20, 200);
            window.draw(tImage, tImage.getX(), tImage.getY());
            count++;
        }
        // mouse handler for selecting token from face up tokens
        MinuetoMouseHandler tokenSelect = new MinuetoMouseHandler() {

            @Override
            public void handleMousePress(int xClicked, int yClicked, int arg2) {
                for (TokenSprite t : tokenImages) {
                    if (t.hasCollidePoint(xClicked, yClicked)) {
                        // inform server that user has selected t
                        ActionManager.getInstance()
                                .sendAction(new TokenSelectedAction(
                                        ClientMain.currentSession.getSessionID(), t.getTokenName()));
                        break;
                    }
                }
            }

            @Override
            public void handleMouseRelease(int arg0, int arg1, int arg2) {
            }

            @Override
            public void handleMouseMove(int arg0, int arg1) {
            }
        };
        MinuetoEventQueue selectTokenQueue = new MinuetoEventQueue();
        window.registerMouseHandler(tokenSelect, selectTokenQueue);
    }
    
}
