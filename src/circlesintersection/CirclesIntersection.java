package circlesintersection;

import circlesintersection.listeners.KeyboardOpsListener;
import circlesintersection.listeners.MouseOpsListener;
import circlesintersection.listeners.PaintComponentListener;
import circlesintersection.models.CircleWithArcs;
import circlesintersection.utils.DebugDrawingHelper;
import circlesintersection.utils.DrawingHelper;
import circlesintersection.utils.circlewitharcs.CirclesDrawingHelper;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

import static circlesintersection.utils.circlewitharcs.CircleUtils.populateCircles;
import static circlesintersection.utils.circlewitharcs.CircleUtils.randomizeCircles;

/**
 * Application entry point class.
 */
public class CirclesIntersection {

    private static DrawingHelper sDrawingHelper;
    private static DebugDrawingHelper sDebugDrawingHelper;
    private static CirclesDrawingHelper sCirclesDrawingHelper;

    /**
     * Entry point.
     *
     * @param args command line parameters.
     */
    public static void main(String[] args) {
        final Settings settings = Settings.getInstance();
        final List<CircleWithArcs> circleWithArcsList = createAndInitializeCircleWithArcs(settings);

        initializeHelpers(settings);
        final CirclesPaintComponent paintComponent = new CirclesPaintComponent(
                circleWithArcsList,
                sDrawingHelper,
                sCirclesDrawingHelper,
                sDebugDrawingHelper);
        final PaintComponentListener paintComponentListener = new PaintComponentListener(paintComponent, sDrawingHelper);
        final CirclesWithArcsRenderer renderer = new CirclesWithArcsRenderer(circleWithArcsList, settings, paintComponentListener);
        new Canvas(
                new JFrame(),
                new MouseOpsListener(renderer, settings),
                new KeyboardOpsListener(renderer, settings),
                paintComponent
        ).initializeRendering();
    }

    private static List<CircleWithArcs> createAndInitializeCircleWithArcs(Settings settings) {
        final List<CircleWithArcs> circleWithArcsList = new ArrayList<>();
        populateCircles(circleWithArcsList, settings.getCirclesQuantity());
        randomizeCircles(circleWithArcsList,
                settings.getCanvasWidth(),
                settings.getCanvasHeight(),
                settings.getDiameterSpan(),
                settings.getIncrement());
        return circleWithArcsList;
    }

    private static void initializeHelpers(Settings settings) {
        sDrawingHelper = DrawingHelper.getInstance(settings);
        sDebugDrawingHelper = DebugDrawingHelper.getInstance(settings);
        sCirclesDrawingHelper = CirclesDrawingHelper.getInstance(settings);
    }
}
