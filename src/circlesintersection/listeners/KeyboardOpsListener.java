package circlesintersection.listeners;

import circlesintersection.Settings;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Listener for a keyboard keys operating.
 */
public class KeyboardOpsListener implements KeyListener {

    private final Settings mSettings;
    private final CirclesRendererListener mRendererListener;

    /**
     * Public constructor
     *
     * @param rendererListener listener for UI updating callbacks
     * @param settings         all the settings for the application.
     */
    public KeyboardOpsListener(CirclesRendererListener rendererListener,
                               Settings settings) {
        mRendererListener = rendererListener;
        mSettings = settings;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
            mSettings.setKeyCtrl(true);
        }
        if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
            mSettings.setKeyShift(true);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            mRendererListener.scatterCirclesComputeArcsAndRepaint();
        }
        if (e.getKeyCode() == KeyEvent.VK_G) {
            mSettings.setGradientEnabled(!mSettings.isGradientEnabled());
            mRendererListener.updateCirclesAndRepaint(null);
        }
        if (e.getKeyCode() == KeyEvent.VK_A) {
            mSettings.setAntiAliasingEnabled(!mSettings.isAntiAliasingEnabled());
            mRendererListener.updateCirclesAndRepaint(null);
        }
        if (e.getKeyCode() == KeyEvent.VK_M) {
            mSettings.changeDrawingMode();
            mRendererListener.updateCirclesAndRepaint(null);
        }
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
        if (e.getKeyCode() == KeyEvent.VK_F) {
            mRendererListener.toggleFullScreen();
        }
        if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
            mSettings.setKeyCtrl(false);
        }
        if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
            mSettings.setKeyShift(false);
        }
    }
}
