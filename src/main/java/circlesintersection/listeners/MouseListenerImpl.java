package circlesintersection.listeners;

import circlesintersection.computation.CirclesRendererListener;

import java.awt.*;
import java.awt.event.*;

/**
 * Listener for a mouse clicks and wheel rotating.
 */
public class MouseListenerImpl implements MouseListener, MouseMotionListener, MouseWheelListener {

    public static final Point MOUSE_POINTER = new Point(0,0);
    public static final Point MOUSE_POINTER_DELTA = new Point(0,0);

    private final KeyboardKeysHolder mKeysHolder;
    private final CirclesRendererListener mRendererListener;

    /**
     * Public constructor
     *
     * @param rendererListener listener for UI updating callbacks
     * @param keysHolder       keys pressed holder
     */
    public MouseListenerImpl(CirclesRendererListener rendererListener,
                             KeyboardKeysHolder keysHolder) {
        mKeysHolder = keysHolder;
        mRendererListener = rendererListener;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        mRendererListener.scatterCirclesComputeArcsAndRepaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        MOUSE_POINTER.setLocation(e.getX(), e.getY());
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
        if (mKeysHolder.isKeyCtrl()) {
            mRendererListener.rotateCirclesAndRepaint(e.getWheelRotation());
        }
        if (mKeysHolder.isKeyShift()) {
            mRendererListener.scaleCirclesAndRepaint(e.getWheelRotation());
        }
        if (!mKeysHolder.isKeyCtrl()
                && !mKeysHolder.isKeyShift()) {
            mRendererListener.updateCirclesAndRepaint(e.getPoint(), e.getWheelRotation());
        }
    }
}
