package circlesintersection.listeners;

import java.awt.*;
import java.awt.event.*;

/**
 * Listener for a mouse clicks and wheel rotating.
 */
public class MouseOpsListener implements MouseListener, MouseMotionListener, MouseWheelListener {

    private final UiUpdateListener uiUpdateListener;

    /**
     * Public constructor
     *
     * @param uiUpdateListener listener for UI updating callbacks
     */
    public MouseOpsListener(UiUpdateListener uiUpdateListener) {
        this.uiUpdateListener = uiUpdateListener;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        uiUpdateListener.createNewArcsAndRepaint();
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
        uiUpdateListener.updateArcsAndRepaint(e.getPoint());
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        uiUpdateListener.updateArcsAndRepaint(e.getPoint(), e.getWheelRotation());
    }
}
