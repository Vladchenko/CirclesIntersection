package circlesintersection.listeners;

import circlesintersection.Settings;

import java.awt.event.*;

/**
 * Listener for a mouse clicks and wheel rotating.
 */
public class MouseOpsListener implements MouseListener, MouseMotionListener, MouseWheelListener {

    private final Settings settings;
    private final UiUpdateListener uiUpdateListener;

    /**
     * Public constructor
     *
     * @param uiUpdateListener listener for UI updating callbacks
     * @param settings         class that keeps all the settings for the application
     */
    public MouseOpsListener(UiUpdateListener uiUpdateListener,
                            Settings settings) {
        this.settings = settings;
        this.uiUpdateListener = uiUpdateListener;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        uiUpdateListener.createNewArcsAndRepaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        uiUpdateListener.dropArcsAndRepaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        uiUpdateListener.dragArcsAndRepaint(e.getPoint());
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        uiUpdateListener.updateArcsAndRepaint(e.getPoint());
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (settings.isKeyCtrl()) {
            uiUpdateListener.rotateArcsAndRepaint(e.getWheelRotation());
        }
        if (settings.isKeyShift()) {
            uiUpdateListener.scaleArcsAndRepaint(e.getWheelRotation());
        }
        if (!settings.isKeyCtrl()
            && !settings.isKeyShift()) {
            uiUpdateListener.updateArcsAndRepaint(e.getPoint(), e.getWheelRotation());
        }
    }
}
