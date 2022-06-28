package circlesintersection.listeners;

import circlesintersection.Settings;

import java.awt.event.*;

/**
 * Listener for a mouse clicks and wheel rotating.
 */
public class MouseListenerImpl implements MouseListener, MouseMotionListener, MouseWheelListener {

    private final Settings mSettings;
    private final CirclesRendererListener mRendererListener;

    /**
     * Public constructor
     *
     * @param rendererListener listener for UI updating callbacks
     * @param settings         all the settings for the application
     */
    public MouseListenerImpl(CirclesRendererListener rendererListener,
                             Settings settings) {
        mSettings = settings;
        mRendererListener = rendererListener;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        mRendererListener.scatterCirclesComputeArcsAndRepaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mSettings.setMouseX(e.getX());
        mSettings.setMouseY(e.getY());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mRendererListener.dropCirclesAndRepaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mRendererListener.dragCirclesAndRepaint(e.getPoint());
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mRendererListener.updateCirclesAndRepaint(e.getPoint());
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (mSettings.isKeyCtrl()) {
            mRendererListener.rotateCirclesAndRepaint(e.getWheelRotation());
        }
        if (mSettings.isKeyShift()) {
            mRendererListener.scaleCirclesAndRepaint(e.getWheelRotation());
        }
        if (!mSettings.isKeyCtrl()
                && !mSettings.isKeyShift()) {
            mRendererListener.updateCirclesAndRepaint(e.getPoint(), e.getWheelRotation());
        }
    }
}
