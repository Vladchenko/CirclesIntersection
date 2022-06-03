package circlesintersection.listeners;

import circlesintersection.Settings;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * TODO
 */
public class KeyboardOpsListener implements KeyListener {

    private final UIUpdateCallbacks callbacks;
    private final Settings settings;

    /**
     * TODO
     *
     * @param callbacks
     */
    public KeyboardOpsListener(UIUpdateCallbacks callbacks, Settings settings) {
        this.callbacks = callbacks;
        this.settings = settings;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            callbacks.createNewArcsAndRepaint();
        }
        if (e.getKeyCode() == KeyEvent.VK_G) {
            settings.setGradientEnabled(!settings.isGradientEnabled());
            callbacks.updateArcsAndRepaint();
        }
        if (e.getKeyCode() == KeyEvent.VK_A) {
            settings.setAntiAliasingEnabled(!settings.isAntiAliasingEnabled());
            callbacks.updateArcsAndRepaint();
        }
        if (e.getKeyCode() == KeyEvent.VK_M) {
            settings.changeDrawingMode();
            callbacks.updateArcsAndRepaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
