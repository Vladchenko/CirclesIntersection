package circlesintersection;

import circlesintersection.listeners.KeyboardOpsListener;
import circlesintersection.listeners.MouseOpsListener;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import static circlesintersection.Settings.APPLICATION_TITLE;

/**
 * Provides a {@link JFrame} to draw a circles, having a keyboard and mouse listeners attached to it.
 */
public class Canvas {

    private final JFrame mFrame;

    private final CirclesPaintComponent mCirclesPaintComponent;
    private final MouseOpsListener mMouseOpsListener;
    private final KeyboardOpsListener mKeyboardListener;

    /**
     * Public constructor.
     *
     * @param frame            to have a JPanel on it, to draw a graphics shapes.
     * @param mouseOpsListener listener for a mouse clicks and wheel rotating.
     * @param keyboardListener listener for a keyboard keys operating.
     * @param paintComponent callbacks to UI to update and repaint it.
     */
    public Canvas(JFrame frame,
                  MouseOpsListener mouseOpsListener,
                  KeyboardOpsListener keyboardListener,
                  CirclesPaintComponent paintComponent) {
        mFrame = frame;
        mMouseOpsListener = mouseOpsListener;
        mKeyboardListener = keyboardListener;
        mCirclesPaintComponent = paintComponent;
    }

    /**
     * Perform an initialization of program.
     */
    public void initializeRendering() {
        initializeFrame();
        addListeners();
    }

    private void initializeFrame() {
        mFrame.setContentPane((JPanel) mCirclesPaintComponent);
        mFrame.setLayout(null);
        mFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mFrame.setSize(Toolkit.getDefaultToolkit().getScreenSize());
        mFrame.setLocationRelativeTo(null);
        mFrame.setResizable(false);
        mFrame.setBackground(Color.BLACK);
        mFrame.setTitle(APPLICATION_TITLE);
        mFrame.setUndecorated(true);
        // Hiding a mouse cursor
        mFrame.setCursor(mFrame.getToolkit().createCustomCursor(
                new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB), new Point(0, 0),
                "null"));
        mFrame.setVisible(true);
    }

    private void addListeners() {
        mFrame.addKeyListener(mKeyboardListener);
        mFrame.addMouseListener(mMouseOpsListener);
        mFrame.addMouseMotionListener(mMouseOpsListener);
        mFrame.addMouseWheelListener(mMouseOpsListener);
    }
}
