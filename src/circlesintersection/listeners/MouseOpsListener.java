package circlesintersection.listeners;

import java.awt.event.*;

/**
 * TODO
 */
public class MouseOpsListener implements MouseListener, MouseMotionListener, MouseWheelListener {

    private final UIUpdateCallbacks callbacks;

    /**
     * TODO
     *
     * @param callbacks
     */
    public MouseOpsListener(UIUpdateCallbacks callbacks) {
        this.callbacks = callbacks;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        callbacks.createNewArcsAndRepaint();
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
        callbacks.updateArcsAndRepaint(e.getPoint());
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        callbacks.updateArcsAndRepaint(e);
    }
}
