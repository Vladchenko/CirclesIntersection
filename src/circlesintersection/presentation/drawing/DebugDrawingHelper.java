package circlesintersection.presentation.drawing;

import circlesintersection.models.CircleWithArcs;

import java.awt.*;
import java.util.List;

import static circlesintersection.presentation.drawing.DrawingConsts.ARCS_COLOR;

/**
 * Helper methods to draw a debug info on canvas.
 */
public final class DebugDrawingHelper {

    private List<CircleWithArcs> mCirclesList;
    private static DebugDrawingHelper sDebugDrawingHelper;

    /**
     * Private constructor.
     *
     * @param circlesList list of circles to draw on canvas.
     */
    private DebugDrawingHelper(List<CircleWithArcs> circlesList) {
        mCirclesList = circlesList;
    }

    /**
     * @param circlesList list of circles to be drawn on canvas.
     * @return Retrieve instance of this class.
     */
    public static DebugDrawingHelper getInstance(List<CircleWithArcs> circlesList) {
        if (sDebugDrawingHelper == null) {
            sDebugDrawingHelper = new DebugDrawingHelper(circlesList);
        }
        return sDebugDrawingHelper;
    }

    public void setCirclesList(List<CircleWithArcs> circlesList) {
        mCirclesList = circlesList;
    }

    /**
     * Draw an index of a circle at the center of it.
     *
     * @param g2d         Graphics component to perform drawing.
     */
    public void drawDebugData(Graphics2D g2d) {
        g2d.setColor(ARCS_COLOR);
        for (int i = 0; i < mCirclesList.size(); i++) {
            if (i < 10) {
                drawDebugString(g2d, i, mCirclesList.get(i), 3,7);
            } else {
                drawDebugString(g2d, i, mCirclesList.get(i), 6,7);
            }
        }
    }

    private void drawDebugString(Graphics2D g2d,
                                 int circlePosition,
                                 CircleWithArcs circle,
                                 int deltaX, int deltaY) {
        g2d.drawString(
                Integer.toString(circlePosition),
                (int)(circle.getX() - deltaX),
                (int)(circle.getY() - deltaY)
        );
    }
}
