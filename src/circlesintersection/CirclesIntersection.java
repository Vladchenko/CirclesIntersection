package circlesintersection;

import circlesintersection.listeners.KeyboardOpsListener;
import circlesintersection.listeners.MouseOpsListener;

/**
 * Application entry point class.
 */
public class CirclesIntersection {

    /**
     * Entry point.
     *
     * @param args command line parameters.
     */
    public static void main(String[] args) {
        Settings settings = Settings.getInstance();
        final PaintComponent paintComponent = new PaintComponent(new Arcs(settings), settings);
        new Canvas(
                new MouseOpsListener(paintComponent),
                new KeyboardOpsListener(paintComponent, settings),
                paintComponent
        ).initializeRendering();
    }
}
