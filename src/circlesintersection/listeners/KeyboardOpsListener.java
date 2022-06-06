package circlesintersection.listeners;

import circlesintersection.Settings;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Listener for a keyboard keys operating.
 */
public class KeyboardOpsListener implements KeyListener {

    private final Settings settings;
    private final UiUpdateListener uiUpdateListener;

    /**
     * Public constructor
     *
     * @param uiUpdateListener  listener for UI updating callbacks
     * @param settings  class that keeps all the settings for the application
     */
    public KeyboardOpsListener(UiUpdateListener uiUpdateListener,
                               Settings settings) {
        this.uiUpdateListener = uiUpdateListener;
        this.settings = settings;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            uiUpdateListener.createNewArcsAndRepaint();
        }
        if (e.getKeyCode() == KeyEvent.VK_G) {
            settings.setGradientEnabled(!settings.isGradientEnabled());
            uiUpdateListener.updateArcsAndRepaint();
        }
        if (e.getKeyCode() == KeyEvent.VK_A) {
            settings.setAntiAliasingEnabled(!settings.isAntiAliasingEnabled());
            uiUpdateListener.updateArcsAndRepaint();
        }
        if (e.getKeyCode() == KeyEvent.VK_M) {
            settings.changeDrawingMode();
            uiUpdateListener.updateArcsAndRepaint();
        }
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
        if (e.getKeyCode() == KeyEvent.VK_F) {
            uiUpdateListener.toggleFullScreen();
        }
    }
}
