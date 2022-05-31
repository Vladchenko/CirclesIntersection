package circlesintersection;

import circlesintersection.listeners.KeyboardOpsListener;
import circlesintersection.listeners.MouseOpsListener;
import circlesintersection.listeners.UIUpdateCallbacks;

import javax.swing.*;
import java.awt.*;

import static circlesintersection.Settings.APPLICATION_TITLE;

/**
 * TODO
 */
public class Canvas {

    private final JFrame frame = new JFrame();
    private final JLabel timeSpentLabel = new JLabel();
    private final JLabel fpsLabel = new JLabel();

    private final Settings settings;
    private final UIUpdateCallbacks callbacks;
    private final MouseOpsListener mouseOpsListener;
    private final KeyboardOpsListener keyboardListener;

    /**
     * TODO
     *
     * @param settings different values to tune the program.
     * @param mouseOpsListener
     * @param keyboardListener
     * @param callbacks
     */
    public Canvas(Settings settings,
                  MouseOpsListener mouseOpsListener,
                  KeyboardOpsListener keyboardListener,
                  UIUpdateCallbacks callbacks) {
        this.settings = settings;
        this.mouseOpsListener = mouseOpsListener;
        this.keyboardListener = keyboardListener;
        this.callbacks = callbacks;
    }

    /**
     * TODO
     */
    public void initializeRendering() {
        initializeFrame();

        initializeViews(settings);
        frame.add(timeSpentLabel);
        frame.add(fpsLabel);

        addListeners();

        callbacks.createNewArcsAndRepaint();
    }

    private void initializeFrame() {
        frame.setContentPane((JPanel) callbacks);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // +24 pixels are added because of an upper stripe of a window
        frame.setSize(settings.getCanvasWidth(), settings.getCanvasHeight());
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setBackground(Color.BLACK);
        frame.setTitle(APPLICATION_TITLE);
        frame.setUndecorated(true);
    }

    private void initializeViews(Settings settings) {
        timeSpentLabel.setSize(160, 15);
        timeSpentLabel.setForeground(Color.GRAY);
        timeSpentLabel.setLocation(settings.getCanvasWidth() - 160, 0);
        fpsLabel.setSize(160, 15);
        fpsLabel.setForeground(Color.GRAY);
        fpsLabel.setLocation(settings.getCanvasWidth() - 160, 35);
    }

    private void addListeners() {
        frame.addKeyListener(keyboardListener);
        frame.addMouseListener(mouseOpsListener);
        frame.addMouseMotionListener(mouseOpsListener);
        frame.addMouseWheelListener(mouseOpsListener);
        frame.setVisible(true);
    }

//    @Override
//    public void updateLabelsData(String fps, String timeSpent) {
//        timeSpentLabel.setText(timeSpent);
//        fpsLabel.setText(fps);
//    }
}
