package circlesintersection.utils.circlewitharcs;

import circlesintersection.Settings;
import circlesintersection.models.CircleWithArcs;
import circlesintersection.models.DrawKind;

import java.awt.*;
import java.util.List;

import static circlesintersection.utils.circlewitharcs.CircleDrawingUtils.*;
import static circlesintersection.utils.circlewitharcs.CircleDrawingUtils.drawCircleGradient;

/**
 * Helper methods for CircleWithArcs list drawing.
 */
public final class CirclesDrawingHelper {

    private final Settings mSettings;
    private static CirclesDrawingHelper sCirclesDrawingHelper;

    /**
     * Private constructor.
     *
     * @param settings all the settings for the application.
     */
    private CirclesDrawingHelper(Settings settings) {
        mSettings = settings;
    }

    /**
     * @param settings all the settings for the application.
     * @return Retrieve instance of this class.
     */
    public static CirclesDrawingHelper getInstance(Settings settings) {
        if (sCirclesDrawingHelper == null) {
            sCirclesDrawingHelper = new CirclesDrawingHelper(settings);
        }
        return sCirclesDrawingHelper;
    }

    /**
     * Draw circles, their gradients, centers and arcs using {@link Graphics2D}
     *
     * @param g2d         Graphics to draw shapes on
     * @param circlesList List of circles with arcs
     */
    public void drawShapesForAllCircles(Graphics2D g2d, List<CircleWithArcs> circlesList) {
        if (mSettings.isGradientEnabled()) {
            drawCirclesGradients(g2d, circlesList);
        }
        if (mSettings.getDrawKind().equals(DrawKind.CIRCLES)
                || mSettings.getDrawKind().equals(DrawKind.BOTH)) {
            drawCircles(g2d, circlesList);
        }
        drawCirclesCenterDots(g2d, circlesList);
    }

    private void drawCirclesCenterDots(Graphics2D g2d, java.util.List<CircleWithArcs> circlesList) {
        CircleWithArcs lastCircle = circlesList.get(circlesList.size() - 1);
        g2d.setColor(mSettings.getArcsColor());
        for (int i = 0; i < circlesList.size() - 1; i++) {
            drawCircleCenter(g2d, circlesList.get(i), mSettings.getMouseDeltaX(), mSettings.getMouseDeltaY());
        }
        g2d.setColor(mSettings.getSubjectCircleColor());
        drawCircleCenter(g2d, lastCircle, mSettings.getMouseDeltaX(), mSettings.getMouseDeltaY());
    }

    private void drawCircles(Graphics2D g2d, java.util.List<CircleWithArcs> circlesList) {
        CircleWithArcs lastCircle = circlesList.get(circlesList.size() - 1);
        g2d.setColor(mSettings.getFadedArcsColor());
        for (int i = 0; i < circlesList.size() - 1; i++) {
            drawCircle(g2d, circlesList.get(i), mSettings.getMouseDeltaX(), mSettings.getMouseDeltaY());
        }
        g2d.setColor(mSettings.getFadedSubjectCircleColor());
        drawCircle(g2d, lastCircle, mSettings.getMouseDeltaX(), mSettings.getMouseDeltaY());
    }

    private void drawCirclesGradients(Graphics2D g2d, List<CircleWithArcs> circlesList) {
        CircleWithArcs lastCircle = circlesList.get(circlesList.size() - 1);
        for (int i = 0; i < circlesList.size() - 1; i++) {
            drawCircleGradient(g2d, circlesList.get(i), mSettings.getMouseDeltaX(), mSettings.getMouseDeltaY(), mSettings.getFadedArcsColor());
        }
        drawCircleGradient(g2d, lastCircle, mSettings.getMouseDeltaX(), mSettings.getMouseDeltaY(), mSettings.getFadedSubjectCircleColor());
    }
}
