package circlesintersection;

import circlesintersection.listeners.KeyboardOpsListener;
import circlesintersection.listeners.MouseOpsListener;
import circlesintersection.models.Arcs;

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
        final ArcsPaintComponent paintComponent = new ArcsPaintComponent(arcs, settings);
        final ArcsRenderer renderer = new ArcsRenderer(arcs, settings, paintComponent);
        new Canvas(
                new MouseOpsListener(renderer, settings),
                new KeyboardOpsListener(renderer, settings),
                paintComponent
        ).initializeRendering();
    }
}
