package circlesintersection.listeners;

import circlesintersection.computation.CirclesRendererListener;
import circlesintersection.presentation.GraphicsSettings;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Listener for a keyboard keys operating.
 */
public class KeyboardListenerImpl implements KeyListener {

    private final GraphicsSettings mGraphicsSettings;
    private final KeyboardKeysHolder mKeysHolder;
    private final CirclesRendererListener mRendererListener;

    /**
     * Public constructor
     *
     * @param rendererListener listener for UI updating callbacks
     * @param keysHolder       keys pressed holder
     */
    public KeyboardListenerImpl(CirclesRendererListener rendererListener,
                                KeyboardKeysHolder keysHolder,
                                GraphicsSettings graphicsSettings) {
        mRendererListener = rendererListener;
        mKeysHolder = keysHolder;
        mGraphicsSettings = graphicsSettings;
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
            mRendererListener.scatterCirclesComputeArcsAndRepaint();
        }
        if (e.getKeyCode() == KeyEvent.VK_G) {
            mGraphicsSettings.setGradientEnabled(!mGraphicsSettings.isGradientEnabled());
            mRendererListener.updateCirclesAndRepaint(null);
        }
        if (e.getKeyCode() == KeyEvent.VK_A) {
            mGraphicsSettings.setAntiAliasingEnabled(!mGraphicsSettings.isAntiAliasingEnabled());
            mRendererListener.updateCirclesAndRepaint(null);
        }
        if (e.getKeyCode() == KeyEvent.VK_M) {
            mGraphicsSettings.changeDrawingMode();
            mRendererListener.updateCirclesAndRepaint(null);
        }
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
        if (e.getKeyCode() == KeyEvent.VK_F) {
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
