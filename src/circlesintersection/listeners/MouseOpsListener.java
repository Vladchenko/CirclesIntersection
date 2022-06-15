package circlesintersection.listeners;

import circlesintersection.Settings;

import java.awt.event.*;

/**
 * Listener for a mouse clicks and wheel rotating.
 */
public class MouseOpsListener implements MouseListener, MouseMotionListener, MouseWheelListener {

    private final Settings settings;
    private final ArcsRendererListener rendererListener;

    /**
     * Public constructor
     *
     * @param rendererListener listener for UI updating callbacks
     * @param settings         class that keeps all the settings for the application
     */
    public MouseOpsListener(ArcsRendererListener rendererListener,
                            Settings settings) {
        this.settings = settings;
        this.rendererListener = rendererListener;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        rendererListener.createNewArcsAndRepaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        rendererListener.dropArcsAndRepaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        rendererListener.dragArcsAndRepaint(e.getPoint());
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        rendererListener.updateArcsAndRepaint(e.getPoint());
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (settings.isKeyCtrl()) {
            rendererListener.rotateArcsAndRepaint(e.getWheelRotation());
        }
        if (settings.isKeyShift()) {
            rendererListener.scaleArcsAndRepaint(e.getWheelRotation());
        }
        if (!settings.isKeyCtrl()
            && !settings.isKeyShift()) {
            rendererListener.updateArcsAndRepaint(e.getPoint(), e.getWheelRotation());
        }
    }
}
