package circlesintersection.utils;

import circlesintersection.Settings;
import circlesintersection.models.CircleWithArcs;

import java.awt.*;
import java.util.List;

public final class DebugDrawingHelper {

    private final Settings mSettings;
    private static DebugDrawingHelper sDebugDrawingHelper;

    /**
     * Private constructor.
     *
     * @param settings all the settings for the application.
     */
    private DebugDrawingHelper(Settings settings) {
        mSettings = settings;
    }

    /**
     * @param settings all the settings for the application.
     * @return Retrieve instance of this class.
     */
    public static DebugDrawingHelper getInstance(Settings settings) {
        if (sDebugDrawingHelper == null) {
            sDebugDrawingHelper = new DebugDrawingHelper(settings);
        }
        return sDebugDrawingHelper;
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
     * @param circlesList Circles and its arcs to be drawn on a canvas.
     */
    public void drawDebugData(Graphics2D g2d, List<CircleWithArcs> circlesList) {
        g2d.setColor(mSettings.getArcsColor());
        for (int i = 0; i < circlesList.size(); i++) {
            if (i < 10) {
                drawDebugString(g2d, i, circlesList.get(i), 3,7);
            } else {
                drawDebugString(g2d, i, circlesList.get(i), 6,7);
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
