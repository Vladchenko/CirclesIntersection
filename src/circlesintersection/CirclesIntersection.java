package circlesintersection;

import circlesintersection.listeners.KeyboardListenerImpl;
import circlesintersection.listeners.MouseListenerImpl;
import circlesintersection.presentation.UiUpdateListenerImpl;
import circlesintersection.models.CircleWithArcs;
import circlesintersection.presentation.Canvas;
import circlesintersection.presentation.PaintComponent;
import circlesintersection.presentation.drawing.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

import static circlesintersection.utils.CircleUtils.populateCircles;
import static circlesintersection.utils.CircleUtils.randomizeCircles;

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

        initializeHelpers(settings, circleWithArcsList);
        final PaintComponent paintComponent = new PaintComponent(
                sDrawingHelper,
                sCirclesDrawingHelper,
                sDebugDrawingHelper);
        final UiUpdateListenerImpl uiUpdateListenerImpl = new UiUpdateListenerImpl(
                sDrawingHelper,
                sDebugDrawingHelper,
                sCirclesDrawingHelper,
                paintComponent);
        final CirclesWithArcsRenderer renderer = new CirclesWithArcsRenderer(circleWithArcsList, settings, uiUpdateListenerImpl);
        new Canvas(
                new JFrame(),
                new MouseListenerImpl(renderer, settings),
                new KeyboardListenerImpl(renderer, settings),
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

    private static void initializeHelpers(Settings settings,
                                          List<CircleWithArcs> circleWithArcsList) {
        sDrawingHelper = DrawingHelper.getInstance(settings, circleWithArcsList);
        sDebugDrawingHelper = DebugDrawingHelper.getInstance(settings, circleWithArcsList);
        sCirclesDrawingHelper = CirclesDrawingHelper.getInstance(settings, circleWithArcsList);
    }
}
