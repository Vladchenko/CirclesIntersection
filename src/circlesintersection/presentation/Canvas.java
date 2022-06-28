package circlesintersection.presentation;

import circlesintersection.listeners.KeyboardListenerImpl;
import circlesintersection.listeners.MouseListenerImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Provides a {@link JFrame} to draw a circles, having a keyboard and mouse listeners attached to it.
 */
public class Canvas {

    private static final String APPLICATION_TITLE = "Circles Intersecting v1.3";
    private static final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    public static final int CANVAS_WIDTH = (int) screenSize.getWidth();// - 10; //- (int) screenSize.getWidth() / 12;
    public static final int CANVAS_HEIGHT = (int) screenSize.getHeight();// - 8; //- (int) screenSize.getHeight() / 9;

    private final JFrame mFrame;

    private final PaintComponent mPaintComponent;
    private final MouseListenerImpl mMouseOpsListener;
    private final KeyboardListenerImpl mKeyboardListener;

    /**
     * Public constructor.
     *
     * @param frame            to have a JPanel on it, to draw a graphics shapes.
     * @param mouseOpsListener listener for a mouse clicks and wheel rotating.
     * @param keyboardListener listener for a keyboard keys operating.
     * @param paintComponent   callbacks to UI to update and repaint it.
     */
    public Canvas(JFrame frame,
                  MouseListenerImpl mouseOpsListener,
                  KeyboardListenerImpl keyboardListener,
                  PaintComponent paintComponent) {
        mFrame = frame;
        mMouseOpsListener = mouseOpsListener;
        mKeyboardListener = keyboardListener;
        mPaintComponent = paintComponent;
    }

    /**
     * Perform an initialization of program.
     */
    public void initializeCanvas() {
        initializeFrame();
        addListeners();
    }

    private void initializeFrame() {
        mFrame.setContentPane(mPaintComponent);
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
