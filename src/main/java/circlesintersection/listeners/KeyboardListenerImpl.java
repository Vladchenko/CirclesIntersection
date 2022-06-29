package circlesintersection.listeners;

import circlesintersection.presentation.GraphicsSettings;
import circlesintersection.presentation.drawing.FrameTimeCounter;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Listener for a keyboard keys operating.
 */
public class KeyboardListenerImpl implements KeyListener {

    private final KeyboardKeysHolder mKeysHolder;
    private final FrameTimeCounter mFrameTimeCounter;
    private final GraphicsSettings mGraphicsSettings;
    private final CirclesRendererListener mRendererListener;

    /**
     * Public constructor
     *
     * @param keysHolder       keys pressed holder
     * @param graphicsSettings settings for drawings a circles and arcs on a canvas
     * @param frameTimeCounter counter of time spent for one frame
     * @param rendererListener listener for UI updating callbacks
     */
    public KeyboardListenerImpl(KeyboardKeysHolder keysHolder,
                                GraphicsSettings graphicsSettings,
                                FrameTimeCounter frameTimeCounter,
                                CirclesRendererListener rendererListener) {
        mKeysHolder = keysHolder;
        mFrameTimeCounter = frameTimeCounter;
        mGraphicsSettings = graphicsSettings;
        mRendererListener = rendererListener;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
            mKeysHolder.setKeyCtrl(true);
        }
        if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
            mKeysHolder.setKeyShift(true);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            mFrameTimeCounter.setTimeBegin(System.nanoTime());
            mRendererListener.scatterCirclesComputeArcsAndRepaint();
        }
        if (e.getKeyCode() == KeyEvent.VK_G) {
            mFrameTimeCounter.setTimeBegin(System.nanoTime());
            mGraphicsSettings.setGradientEnabled(!mGraphicsSettings.isGradientEnabled());
            mRendererListener.updateCirclesAndRepaint(null);
        }
        if (e.getKeyCode() == KeyEvent.VK_A) {
            mFrameTimeCounter.setTimeBegin(System.nanoTime());
            mGraphicsSettings.setAntiAliasingEnabled(!mGraphicsSettings.isAntiAliasingEnabled());
            mRendererListener.updateCirclesAndRepaint(null);
        }
        if (e.getKeyCode() == KeyEvent.VK_M) {
            mFrameTimeCounter.setTimeBegin(System.nanoTime());
            mGraphicsSettings.changeDrawingMode();
            mRendererListener.updateCirclesAndRepaint(null);
        }
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
        if (e.getKeyCode() == KeyEvent.VK_F) {
            mFrameTimeCounter.setTimeBegin(System.nanoTime());
            mRendererListener.toggleFullScreen();
        }
        if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
            mKeysHolder.setKeyCtrl(false);
        }
        if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
            mKeysHolder.setKeyShift(false);
        }
    }
}
