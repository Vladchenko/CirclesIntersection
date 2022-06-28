package circlesintersection.presentation.drawing;

import circlesintersection.Settings;
import circlesintersection.models.CircleWithArcs;

import java.awt.*;
import java.util.List;

/**
 * Helper methods to draw a debug info on canvas.
 */
public final class DebugDrawingHelper {

    private final Settings mSettings;
    private List<CircleWithArcs> mCirclesList;
    private static DebugDrawingHelper sDebugDrawingHelper;

    /**
     * Private constructor.
     *
     * @param settings all the settings for the application.
     * @param circlesList list of circles to draw on canvas.
     */
    private DebugDrawingHelper(Settings settings,
                               List<CircleWithArcs> circlesList) {
        mSettings = settings;
        mCirclesList = circlesList;
    }

    /**
     * @param settings all the settings for the application.
     * @return Retrieve instance of this class.
     */
    public static DebugDrawingHelper getInstance(Settings settings,
                                                 List<CircleWithArcs> circlesList) {
        if (sDebugDrawingHelper == null) {
            sDebugDrawingHelper = new DebugDrawingHelper(settings, circlesList);
        }
        return sDebugDrawingHelper;
    }

    public void setCirclesList(List<CircleWithArcs> circlesList) {
        mCirclesList = circlesList;
    }

    /**
     * Draw time taken to repaint a canvas
     *
     * @param g2d Graphics component to perform drawing.
     */
    public void drawFrameDrawingTime(Graphics2D g2d) {
        g2d.setPaint(Color.GRAY);
        // Time taken in nanoseconds to draw a frame.
        mSettings.setTimeTemp(System.nanoTime() - mSettings.getTimeBegin());
        g2d.drawString("Frame render time: " + mSettings.getTimeTemp() / 1_000_000
                        + "."
                        + Long.toString(mSettings.getTimeTemp() / 10_000).substring(1)
                        + " milliseconds",
                10,
                40);
    }

    /**
     * Draw an index of a circle at the center of it.
     *
     * @param g2d         Graphics component to perform drawing.
     */
    public void drawDebugData(Graphics2D g2d) {
        g2d.setColor(mSettings.getArcsColor());
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
