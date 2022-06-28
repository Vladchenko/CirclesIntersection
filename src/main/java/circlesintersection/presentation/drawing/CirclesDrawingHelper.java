package circlesintersection.presentation.drawing;

import circlesintersection.presentation.GraphicsSettings;
import circlesintersection.models.CircleWithArcs;

import java.awt.*;
import java.util.List;

import static circlesintersection.listeners.MouseListenerImpl.MOUSE_POINTER_DELTA;
import static circlesintersection.presentation.drawing.CircleDrawingUtils.*;
import static circlesintersection.presentation.drawing.CircleDrawingUtils.drawCircleGradient;
import static circlesintersection.presentation.drawing.DrawingConsts.*;

/**
 * Helper methods for CircleWithArcs list drawing.
 */
public final class CirclesDrawingHelper {

    private List<CircleWithArcs> mCirclesList;
    private final GraphicsSettings mGraphicsSettings;
    private static CirclesDrawingHelper sCirclesDrawingHelper;

    /**
     * Private constructor.
     *
     * @param graphicsSettings Graphics settings for the application.
     * @param circlesList      Circles and their arcs to be drawn on a canvas.
     */
    private CirclesDrawingHelper(GraphicsSettings graphicsSettings,
                                 List<CircleWithArcs> circlesList) {
        mGraphicsSettings = graphicsSettings;
        mCirclesList = circlesList;
    }

    /**
     * @param graphicsSettings all the settings for the application.
     * @return Retrieve instance of this class.
     */
    public static CirclesDrawingHelper getInstance(GraphicsSettings graphicsSettings,
                                                   List<CircleWithArcs> circlesList) {
        if (sCirclesDrawingHelper == null) {
            sCirclesDrawingHelper = new CirclesDrawingHelper(graphicsSettings, circlesList);
        }
        return sCirclesDrawingHelper;
    }

    /**
     * Draw circles, their gradients, centers and arcs using {@link Graphics2D}
     *
     * @param g2d Graphics to draw shapes on
     */
    public void drawShapesForAllCircles(Graphics2D g2d) {
        if (mGraphicsSettings.isGradientEnabled()) {
            drawCirclesGradients(g2d);
        }
        if (mGraphicsSettings.getDrawKind().equals(GraphicsSettings.DrawKind.CIRCLES)
                || mGraphicsSettings.getDrawKind().equals(GraphicsSettings.DrawKind.BOTH)) {
            drawCircles(g2d);
        }
        drawCirclesCenterDots(g2d);
    }

    private void drawCirclesCenterDots(Graphics2D g2d) {
        CircleWithArcs lastCircle = mCirclesList.get(mCirclesList.size() - 1);
        g2d.setColor(ARCS_COLOR);
        for (int i = 0; i < mCirclesList.size() - 1; i++) {
            drawCircleCenter(g2d, mCirclesList.get(i), (int) MOUSE_POINTER_DELTA.getX(), (int) MOUSE_POINTER_DELTA.getY());
        }
        g2d.setColor(SUBJECT_CIRCLE_COLOR);
        drawCircleCenter(g2d, lastCircle, (int) MOUSE_POINTER_DELTA.getX(), (int) MOUSE_POINTER_DELTA.getY());
    }

    private void drawCircles(Graphics2D g2d) {
        CircleWithArcs lastCircle = mCirclesList.get(mCirclesList.size() - 1);
        g2d.setColor(FADED_ARCS_COLOR);
        for (int i = 0; i < mCirclesList.size() - 1; i++) {
            drawCircle(g2d, mCirclesList.get(i), (int) MOUSE_POINTER_DELTA.getX(), (int) MOUSE_POINTER_DELTA.getY());
        }
        g2d.setColor(FADED_SUBJECT_CIRCLE_COLOR);
        drawCircle(g2d, lastCircle, (int) MOUSE_POINTER_DELTA.getX(), (int) MOUSE_POINTER_DELTA.getY());
    }

    private void drawCirclesGradients(Graphics2D g2d) {
        CircleWithArcs lastCircle = mCirclesList.get(mCirclesList.size() - 1);
        for (int i = 0; i < mCirclesList.size() - 1; i++) {
            drawCircleGradient(g2d, mCirclesList.get(i), (int) MOUSE_POINTER_DELTA.getX(), (int) MOUSE_POINTER_DELTA.getY(), FADED_ARCS_COLOR);
        }
        drawCircleGradient(g2d, lastCircle, (int) MOUSE_POINTER_DELTA.getX(), (int) MOUSE_POINTER_DELTA.getY(), FADED_SUBJECT_CIRCLE_COLOR);
    }

    public void setCirclesList(List<CircleWithArcs> circlesList) {
        mCirclesList = circlesList;
    }
}
