package circlesintersection.listeners;

import circlesintersection.presentation.drawing.FrameTimeCounter;

import java.awt.*;
import java.awt.event.*;

/**
 * Listener for a mouse clicks and wheel rotating.
 */
public class MouseListenerImpl implements MouseListener, MouseMotionListener, MouseWheelListener {

    public static final Point MOUSE_POINTER = new Point(0, 0);
    public static final Point MOUSE_POINTER_DELTA = new Point(0, 0);

    private final KeyboardKeysHolder mKeysHolder;
    private final FrameTimeCounter mFrameTimeCounter;
    private final CirclesRendererListener mRendererListener;

    /**
     * Public constructor
     *
     * @param keysHolder       keys pressed holder
     * @param frameTimeCounter counter of time spent for one frame
     * @param rendererListener listener for UI updating callbacks
     */
    public MouseListenerImpl(KeyboardKeysHolder keysHolder,
                             FrameTimeCounter frameTimeCounter,
                             CirclesRendererListener rendererListener) {
        mKeysHolder = keysHolder;
        mFrameTimeCounter = frameTimeCounter;
        mRendererListener = rendererListener;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        mFrameTimeCounter.setTimeBegin(System.nanoTime());
        mRendererListener.scatterCirclesComputeArcsAndRepaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mFrameTimeCounter.setTimeBegin(System.nanoTime());
        MOUSE_POINTER.setLocation(e.getX(), e.getY());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mFrameTimeCounter.setTimeBegin(System.nanoTime());
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
        mFrameTimeCounter.setTimeBegin(System.nanoTime());
        mRendererListener.dragCirclesAndRepaint(e.getPoint());
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mFrameTimeCounter.setTimeBegin(System.nanoTime());
        mRendererListener.updateCirclesAndRepaint(e.getPoint());
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        mFrameTimeCounter.setTimeBegin(System.nanoTime());
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
