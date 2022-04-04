package clientsrc;

import javax.swing.event.MouseInputListener;
import java.awt.event.MouseEvent;

public interface PhaseThreeMouseHandler extends MouseInputListener {

    @Override
    public default void mouseClicked(MouseEvent e) {
    }

    @Override
    public default void mouseReleased(MouseEvent e) {
    }

    @Override
    public default void mouseEntered(MouseEvent e) {
    }

    @Override
    public default void mouseExited(MouseEvent e) {
    }

    @Override
    public default void mouseDragged(MouseEvent e) {
    }

    @Override
    public default void mouseMoved(MouseEvent e) {
    }
}
