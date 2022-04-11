package networksrc;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
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
    private boolean displayWindow = true;

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
        final ArrayList<SwingTokenSprite> selectedSprite = new ArrayList<>();
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
                synchronized(selectedSprite) {
                    try {
                        JLabel origin = (JLabel) e.getComponent();
                        selectedSprite.add((SwingTokenSprite) origin.getIcon());
                        selectedSprite.notify();
                    } catch (ClassCastException exception) {
                        // do nothing if not a JLabel
                        exception.printStackTrace();
                    }
                }
            }
        };

        MouseInputListener buttonListener = new PhaseThreeMouseListener() {

            @Override
            public void mousePressed(MouseEvent e) {
                selectedSprite.notify();
            }
        };

        JFrame tokenFrame = new JFrame("Select a token.");
        tokenFrame.setLocation(300, 200);
        tokenFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        JPanel tokenPanel = new JPanel();
        tokenPanel.setLayout(new BoxLayout(tokenPanel, BoxLayout.X_AXIS));
        tokenImages.forEach((tokenImage) -> {
            JLabel pic = new JLabel(new SwingTokenSprite(tokenImage));
            tokenPanel.add(pic);
            pic.addMouseListener(tokenListener);
        });
        JButton faceDownButton = new JButton("Draw random token");
        faceDownButton.addMouseListener(buttonListener);
        tokenPanel.add(faceDownButton);
        tokenFrame.add(tokenPanel);
        tokenFrame.setSize(600, 200);
        tokenFrame.setVisible(true);
        window.render();
        try {
            selectedSprite.wait();
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
        synchronized(selectedSprite)
        {
            tokenFrame.setVisible(false);
            tokenFrame.dispose();
            if (selectedSprite.size() > 0) {
                SwingTokenSprite selected = selectedSprite.get(0);
                selectedSprite.clear();
                ActionManager.getInstance()
                        .sendAction(new TokenSelectedAction(selected.getTypeString()));
                System.out.println("Selected token: " + selected.getTypeString());
            }
            else {
                ActionManager.getInstance().sendAction(new TokenSelectedAction("random"));
                System.out.println("Picked random token.");
            }
    
            // display
            try {
                ClientMain.displayBoardElements();
            } catch (MinuetoFileException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
