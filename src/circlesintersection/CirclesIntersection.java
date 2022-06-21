package circlesintersection;

import circlesintersection.listeners.KeyboardOpsListener;
import circlesintersection.listeners.MouseOpsListener;
import circlesintersection.models.CircleWithArcs;
import circlesintersection.utils.DrawingUtils;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

import static circlesintersection.utils.CircleUtils.populateCircles;
import static circlesintersection.utils.CircleUtils.randomizeCircles;

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
        final List<CircleWithArcs> circleWithArcsList = new ArrayList<>();

        // TODO Put this code to some of classes ?
        populateCircles(circleWithArcsList, settings.getCirclesQuantity());
        randomizeCircles(circleWithArcsList,
                settings.getCanvasWidth(),
                settings.getCanvasHeight(),
                settings.getDiameterSpan(),
                settings.getIncrement());

        final DrawingUtils drawingComponent = new DrawingUtils(settings);
        final CirclesPaintComponent paintComponent = new CirclesPaintComponent(circleWithArcsList, drawingComponent);
        final CirclesWithArcsRenderer renderer = new CirclesWithArcsRenderer(circleWithArcsList, settings, paintComponent);
        new Canvas(
                new JFrame(),
                new MouseOpsListener(renderer, settings),
                new KeyboardOpsListener(renderer, settings),
                paintComponent
        ).initializeRendering();
    }
}
