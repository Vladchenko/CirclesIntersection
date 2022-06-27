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
    private List<CircleWithArcs> mCirclesList;
    private static CirclesDrawingHelper sCirclesDrawingHelper;

    /**
     * TODO
     * Private constructor.
     *
     * @param settings all the settings for the application.
     */
    private CirclesDrawingHelper(Settings settings,
                                 List<CircleWithArcs> circlesList) {
        mSettings = settings;
        mCirclesList = circlesList;
    }

    /**
     * @param settings all the settings for the application.
     * @return Retrieve instance of this class.
     */
    public static CirclesDrawingHelper getInstance(Settings settings,
                                                   List<CircleWithArcs> circlesList) {
        if (sCirclesDrawingHelper == null) {
            sCirclesDrawingHelper = new CirclesDrawingHelper(settings, circlesList);
        }
        return sCirclesDrawingHelper;
    }

    /**
     * Draw circles, their gradients, centers and arcs using {@link Graphics2D}
     *
     * @param g2d Graphics to draw shapes on
     */
    public void drawShapesForAllCircles(Graphics2D g2d) {
        if (mSettings.isGradientEnabled()) {
            drawCirclesGradients(g2d);
        }
        if (mSettings.getDrawKind().equals(DrawKind.CIRCLES)
                || mSettings.getDrawKind().equals(DrawKind.BOTH)) {
            drawCircles(g2d);
        }
        drawCirclesCenterDots(g2d);
    }

    private void drawCirclesCenterDots(Graphics2D g2d) {
        CircleWithArcs lastCircle = mCirclesList.get(mCirclesList.size() - 1);
        g2d.setColor(mSettings.getArcsColor());
        for (int i = 0; i < mCirclesList.size() - 1; i++) {
            drawCircleCenter(g2d, mCirclesList.get(i), mSettings.getMouseDeltaX(), mSettings.getMouseDeltaY());
        }
        g2d.setColor(mSettings.getSubjectCircleColor());
        drawCircleCenter(g2d, lastCircle, mSettings.getMouseDeltaX(), mSettings.getMouseDeltaY());
    }

    private void drawCircles(Graphics2D g2d) {
        CircleWithArcs lastCircle = mCirclesList.get(mCirclesList.size() - 1);
        g2d.setColor(mSettings.getFadedArcsColor());
        for (int i = 0; i < mCirclesList.size() - 1; i++) {
            drawCircle(g2d, mCirclesList.get(i), mSettings.getMouseDeltaX(), mSettings.getMouseDeltaY());
        }
        g2d.setColor(mSettings.getFadedSubjectCircleColor());
        drawCircle(g2d, lastCircle, mSettings.getMouseDeltaX(), mSettings.getMouseDeltaY());
    }

    private void drawCirclesGradients(Graphics2D g2d) {
        CircleWithArcs lastCircle = mCirclesList.get(mCirclesList.size() - 1);
        for (int i = 0; i < mCirclesList.size() - 1; i++) {
            drawCircleGradient(g2d, mCirclesList.get(i), mSettings.getMouseDeltaX(), mSettings.getMouseDeltaY(), mSettings.getFadedArcsColor());
        }
        drawCircleGradient(g2d, lastCircle, mSettings.getMouseDeltaX(), mSettings.getMouseDeltaY(), mSettings.getFadedSubjectCircleColor());
    }

    public void setCirclesList(List<CircleWithArcs> circlesList) {
        mCirclesList = circlesList;
    }
}
