package networksrc;

import java.awt.event.MouseEvent;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import org.minueto.MinuetoFileException;
import org.minueto.window.MinuetoWindow;

import clientsrc.ClientMain;
import clientsrc.SwingTokenSprite;
import clientsrc.TokenSprite;
import clientsrc.PhaseThreeMouseListener;

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
        MouseInputListener tokenListener = new PhaseThreeMouseListener() {

            @Override
            public void mousePressed(MouseEvent e) {
                try {
                    JLabel origin = (JLabel) e.getComponent();
                    SwingTokenSprite sprite = (SwingTokenSprite) origin.getIcon();
                    ActionManager.getInstance()
                            .sendAction(new TokenSelectedAction(sprite.getTypeString()));
                    // TODO: add some acknowledgement of token selection

                    // close pop up
                    origin.getParent().getParent().setVisible(false);
                } catch (ClassCastException exception) {
                    // do nothing if not a JLabel
                    exception.printStackTrace();
                }
            }
        };

        MouseInputListener buttonListener = new PhaseThreeMouseListener() {

            @Override
            public void mousePressed(MouseEvent e) {
                ActionManager.getInstance().sendAction(new TokenSelectedAction("random"));
                e.getComponent().getParent().getParent().setVisible(false);
            }
        };

        JFrame tokenFrame = new JFrame("Select a token.");
        JPanel tokenPanel = new JPanel();
        tokenPanel.setLayout(new BoxLayout(tokenPanel, BoxLayout.X_AXIS));
        tokenImages.forEach((tokenImage) -> {
            JLabel pic = new JLabel(new SwingTokenSprite(tokenImage));
            tokenPanel.add(pic);
            pic.addMouseListener(tokenListener);
        });
        JButton faceDownButton = new JButton("Face-down Token.");
        faceDownButton.addMouseListener(buttonListener);
        tokenPanel.add(faceDownButton);
        tokenFrame.add(tokenPanel);
        tokenFrame.setVisible(true);
        window.render();
        Thread.yield();
    }
}
