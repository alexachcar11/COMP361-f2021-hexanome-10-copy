package networksrc;

import java.awt.event.MouseEvent;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import org.minueto.MinuetoFileException;
import org.minueto.window.MinuetoWindow;

import clientsrc.ClientMain;
import clientsrc.SwingTokenSprite;
import clientsrc.TokenSprite;

public class DisplayPhaseThreeACK implements Action {

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
        MouseInputListener mouseListener = new MouseInputListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                try {
                    JLabel origin = (JLabel) e.getComponent();
                    SwingTokenSprite sprite = (SwingTokenSprite) origin.getIcon();
                    String gameID = ClientMain.currentSession.getSessionID();
                    ActionManager.getInstance()
                            .sendAction(new TokenSelectedAction(gameID, sprite.getTypeString()));
                } catch (ClassCastException exception) {
                    // do nothing if not a JLabel
                    return;
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }

            @Override
            public void mouseDragged(MouseEvent e) {
            }

            @Override
            public void mouseMoved(MouseEvent e) {
            }
        };
        JFrame tokenFrame = new JFrame("Select a token.");
        JPanel tokenPanel = new JPanel();
        tokenPanel.setLayout(new BoxLayout(tokenPanel, BoxLayout.X_AXIS));
        tokenImages.forEach((tokenImage) -> {
            JLabel pic = new JLabel(new SwingTokenSprite(tokenImage));
            pic.addMouseListener(mouseListener);
            tokenPanel.add(pic);
        });
        tokenFrame.add(tokenPanel);
        tokenFrame.setVisible(true);
        window.render();
        Thread.yield();
    }

}
