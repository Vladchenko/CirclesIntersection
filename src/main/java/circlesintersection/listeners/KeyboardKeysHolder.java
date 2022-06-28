package circlesintersection.listeners;

/**
 * Keeps the values for a keys being pressed.
 */
public class KeyboardKeysHolder {

    // Flag checks if a Ctrl key is pressed.
    private boolean mKeyCtrl = false;
    // Flag checks if a Shift key is pressed.
    private boolean mKeyShift = false;
    private static KeyboardKeysHolder keysHolder;

    private KeyboardKeysHolder() {
    }

    /**
     * @return Instance of this class.
     */
    public static KeyboardKeysHolder getInstance() {
        if (keysHolder == null) {
            keysHolder = new KeyboardKeysHolder();
        }
        return keysHolder;
    }

    public boolean isKeyCtrl() {
        return mKeyCtrl;
    }

    public void setKeyCtrl(boolean keyCtrl) {
        this.mKeyCtrl = keyCtrl;
    }

    public boolean isKeyShift() {
        return mKeyShift;
    }

    public void setKeyShift(boolean keyShift) {
        this.mKeyShift = keyShift;
    }
}
