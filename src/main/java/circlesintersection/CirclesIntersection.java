package circlesintersection;

import circlesintersection.computation.CirclesWithArcsRenderer;
import circlesintersection.listeners.KeyboardKeysHolder;
import circlesintersection.listeners.KeyboardListenerImpl;
import circlesintersection.listeners.MouseListenerImpl;
import circlesintersection.presentation.*;
import circlesintersection.models.CircleWithArcs;
import circlesintersection.presentation.drawing.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

import static circlesintersection.models.CircleWithArcs.DIAMETER_MINIMUM;
import static circlesintersection.models.CircleWithArcs.DIAMETER_RANDOM_RANGE;
import static circlesintersection.presentation.Canvas.CANVAS_HEIGHT;
import static circlesintersection.presentation.Canvas.CANVAS_WIDTH;
import static circlesintersection.computation.CircleUtils.populateCircles;
import static circlesintersection.computation.CircleUtils.randomizeCircles;

/**
 * Application entry point class.
 */
public class CirclesIntersection {

    private static final int CIRCLES_QUANTITY = 70;

    private static DrawingHelper sDrawingHelper;
    private static FrameTimeDrawingHelper sFrameDrawingHelper;
    private static DebugDrawingHelper sDebugDrawingHelper;
    private static CirclesDrawingHelper sCirclesDrawingHelper;

    /**
     * Entry point.
     *
     * @param args command line parameters.
     */
    public static void main(String[] args) {
        final GraphicsSettings graphicsSettings = GraphicsSettings.getInstance();
        final List<CircleWithArcs> circleWithArcsList = createAndInitializeCircleWithArcs();
        final FrameTimeCounter frameTimeCounter = new FrameTimeCounter();

        initializeHelpers(graphicsSettings, frameTimeCounter, circleWithArcsList);
        final PaintComponent paintComponent = new PaintComponent(
                sDrawingHelper,
                sFrameDrawingHelper,
                sCirclesDrawingHelper,
                sDebugDrawingHelper);
        final UiUpdateListenerImpl uiUpdateListenerImpl = new UiUpdateListenerImpl(
                sDrawingHelper,
                sDebugDrawingHelper,
                sCirclesDrawingHelper,
                paintComponent);
        final CirclesWithArcsRenderer renderer = new CirclesWithArcsRenderer(circleWithArcsList,
                uiUpdateListenerImpl,
                frameTimeCounter);
        final KeyboardKeysHolder keysHolder = KeyboardKeysHolder.getInstance();
        new Canvas(
                new JFrame(),
                new MouseListenerImpl(renderer, keysHolder),
                new KeyboardListenerImpl(renderer, keysHolder, graphicsSettings),
                paintComponent
        ).initializeCanvas();
    }

    private static List<CircleWithArcs> createAndInitializeCircleWithArcs() {
        final List<CircleWithArcs> circleWithArcsList = new ArrayList<>();
        populateCircles(circleWithArcsList, CIRCLES_QUANTITY);
        randomizeCircles(circleWithArcsList,
                CANVAS_WIDTH,
                CANVAS_HEIGHT,
                DIAMETER_RANDOM_RANGE,
                DIAMETER_MINIMUM);
        return circleWithArcsList;
    }

    private static void initializeHelpers(GraphicsSettings graphicsSettings,
                                          FrameTimeCounter frameTimeCounter,
                                          List<CircleWithArcs> circleWithArcsList) {
        sDrawingHelper = DrawingHelper.getInstance(graphicsSettings, circleWithArcsList, frameTimeCounter);
        sFrameDrawingHelper = FrameTimeDrawingHelper.getInstance(frameTimeCounter);
        sDebugDrawingHelper = DebugDrawingHelper.getInstance(circleWithArcsList);
        sCirclesDrawingHelper = CirclesDrawingHelper.getInstance(graphicsSettings, circleWithArcsList);
    }
}
