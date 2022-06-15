package circlesintersection;

import circlesintersection.listeners.KeyboardOpsListener;
import circlesintersection.listeners.MouseOpsListener;
import circlesintersection.listeners.UiUpdateListener;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import static circlesintersection.Settings.APPLICATION_TITLE;

/**
 * Provides a {@link JFrame} to draw a circles, having a keyboard and mouse listeners attached to it.
 */
public class Canvas {

    private final JFrame frame;

    private final UiUpdateListener uiUpdateListener;
    private final MouseOpsListener mouseOpsListener;
    private final KeyboardOpsListener keyboardListener;

    /**
     * Public constructor.
     *
     * @param mouseOpsListener listener for a mouse clicks and wheel rotating.
     * @param keyboardListener listener for a keyboard keys operating.
     * @param uiUpdateListener callbacks to UI to update and repaint it.
     */
    public Canvas(MouseOpsListener mouseOpsListener,
                  KeyboardOpsListener keyboardListener,
                  UiUpdateListener uiUpdateListener) {
        frame = new JFrame();
        this.mouseOpsListener = mouseOpsListener;
        this.keyboardListener = keyboardListener;
        this.uiUpdateListener = uiUpdateListener;
    }

    /**
     * TODO
     */
    public void initializeRendering() {
        initializeFrame();
        addListeners();
    }

    private void initializeFrame() {
        frame.setContentPane((JPanel) uiUpdateListener);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setBackground(Color.BLACK);
        frame.setTitle(APPLICATION_TITLE);
        frame.setUndecorated(true);
        // Hiding a mouse cursor
        frame.setCursor(frame.getToolkit().createCustomCursor(
                new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB), new Point(0, 0),
                "null"));
    }

    private void addListeners() {
        frame.addKeyListener(keyboardListener);
        frame.addMouseListener(mouseOpsListener);
        frame.addMouseMotionListener(mouseOpsListener);
        frame.addMouseWheelListener(mouseOpsListener);
        frame.setVisible(true);
    }
}
