package circlesintersection.listeners;

import circlesintersection.Settings;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Listener for a keyboard keys operating.
 */
public class KeyboardOpsListener implements KeyListener {

    private final Settings settings;
    private final ArcsRendererListener rendererListener;

    /**
     * Public constructor
     *
     * @param rendererListener  listener for UI updating callbacks
     * @param settings  class that keeps all the settings for the application
     */
    public KeyboardOpsListener(ArcsRendererListener rendererListener,
                               Settings settings) {
        this.rendererListener = rendererListener;
        this.settings = settings;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
            settings.setKeyCtrl(true);
        }
        if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
            settings.setKeyShift(true);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            rendererListener.createNewArcsAndRepaint();
        }
        if (e.getKeyCode() == KeyEvent.VK_G) {
            settings.setGradientEnabled(!settings.isGradientEnabled());
            rendererListener.updateArcsAndRepaint(null);
        }
        if (e.getKeyCode() == KeyEvent.VK_A) {
            settings.setAntiAliasingEnabled(!settings.isAntiAliasingEnabled());
            rendererListener.updateArcsAndRepaint(null);
        }
        if (e.getKeyCode() == KeyEvent.VK_M) {
            settings.changeDrawingMode();
            rendererListener.updateArcsAndRepaint(null);
        }
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
        if (e.getKeyCode() == KeyEvent.VK_F) {
            rendererListener.toggleFullScreen();
        }
        if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
            settings.setKeyCtrl(false);
        }
        if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
            settings.setKeyShift(false);
        }
    }
}
