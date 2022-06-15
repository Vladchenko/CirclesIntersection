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
        final Settings settings = Settings.getInstance();
        final Arcs arcs = new Arcs(settings);
        final PaintComponent paintComponent = new PaintComponent(arcs, settings);
        final ArcsRenderer renderer = new ArcsRenderer(arcs, settings, paintComponent);
        new Canvas(
                new MouseOpsListener(renderer, settings),
                new KeyboardOpsListener(renderer, settings),
                paintComponent
        ).initializeRendering();
    }
}
