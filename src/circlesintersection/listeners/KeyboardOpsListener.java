package circlesintersection.listeners;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * TODO
 */
public class KeyboardOpsListener implements KeyListener {

    private final UIUpdateCallbacks callbacks;

    /**
     * TODO
     *
     * @param callbacks
     */
    public KeyboardOpsListener(UIUpdateCallbacks callbacks) {
        this.callbacks = callbacks;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() != KeyEvent.VK_ALT) {
            callbacks.createNewArcsAndRepaint();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
