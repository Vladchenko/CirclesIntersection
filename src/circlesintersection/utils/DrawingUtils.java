package circlesintersection.utils;

import circlesintersection.Settings;
import circlesintersection.models.CircleWithArcs;
import circlesintersection.models.DrawKind;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Arc2D;
import java.util.List;

/**
 * Utils methods for drawing the shapes on canvas.
 */
public class DrawingUtils {

    private final Settings mSettings;

    /**
     * Public constructor.
     *
     * @param settings all the settings for the application.
     */
    public DrawingUtils(Settings settings) {
        mSettings = settings;
    }

    /**
     * Turns full screen mode on or off
     *
     * @param g2d Graphics component to perform drawing.
     */
    public void toggleAntiAliasing(Graphics2D g2d) {
        if (mSettings.isAntiAliasingEnabled()) {
            // Smooths the picture, but slows down drawing a lot
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }
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

    /**
     * Draw arcs for this circle that do not intersect with any other circle.
     *
     * @param g2d         Graphics component to perform drawing.
     * @param circlesList Circles and its arcs to be drawn on a canvas.
     */
    public void drawNotIntersectedArcs(Graphics2D g2d, List<CircleWithArcs> circlesList) {
        g2d.setStroke(       //new BasicStroke(2));
                new BasicStroke(4,
                        BasicStroke.CAP_ROUND,
                        BasicStroke.JOIN_ROUND,
                        2, null, 2.0f));
        g2d.setColor(mSettings.getArcsColor());

        // Drawing an arcs for a not intersected areas of circles
        if (mSettings.getDrawKind().equals(DrawKind.ARCS)
                || mSettings.getDrawKind().equals(DrawKind.BOTH)) {
            for (int i = 0; i < circlesList.size() - 1; i++) {
                if (!circlesList.get(i).isExcluded()) {
                    for (Arc2D arc2D : circlesList.get(i).getArcs2D()) {
                        g2d.draw(arc2D);
                    }
                }
            }
            g2d.setColor(mSettings.getSubjectCircleColor());
            // Drawing a last circle with a different color
            CircleWithArcs lastCircle = circlesList.get(circlesList.size() - 1);
            for (int i = 0; i < lastCircle.getArcs2D().size(); i++) {
                g2d.draw(lastCircle.getArcs2D().get(i));
            }
        }
    }

    /**
     * TODO
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
     * Turns on or off full screen mode.
     *
     * @param jPanel         Swing {@link JPanel} component that draws circles and their arcs.
     * @param graphicsDevice Component to perform full screen switching
     */
    public void toggleFullScreen(JPanel jPanel, GraphicsDevice graphicsDevice) {
        mSettings.setTimeBegin(System.nanoTime());
        JFrame frame = (JFrame) SwingUtilities.getRoot(jPanel);
        if (graphicsDevice.getFullScreenWindow() == null) {  // Go to full screen mode
            frame.dispose();    // When this line of code is moved outside of if, full screen toggling doesn't work
            frame.setUndecorated(true);
            graphicsDevice.setFullScreenWindow(frame);
        } else { // back to windowed mode
            frame.dispose();    // When this line of code is moved outside of if, full screen toggling doesn't work
            frame.setUndecorated(true);
            graphicsDevice.setFullScreenWindow(null);
        }
        frame.setVisible(true);
    }

    /**
     * Set appearance of lines for intersected arcs
     *
     * @param g2d Graphics component to perform drawing.
     */
    public void changeIntersectedArcsAppearance(Graphics2D g2d) {
        // Changing appearance of intersected arcs
        float[] dash1 = {2.0f, 4.0f};
        g2d.setStroke(new BasicStroke(2,
                BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_ROUND,
                2, dash1, 0.0f));
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

    private void drawCirclesCenterDots(Graphics2D g2d, java.util.List<CircleWithArcs> circlesList) {
        CircleWithArcs lastCircle = circlesList.get(circlesList.size() - 1);
        g2d.setColor(mSettings.getArcsColor());
        for (int i = 0; i < circlesList.size() - 1; i++) {
            circlesList.get(i).drawCircleCenter(g2d, mSettings.getMouseDeltaX(), mSettings.getMouseDeltaY());
        }
        g2d.setColor(mSettings.getSubjectCircleColor());
        lastCircle.drawCircleCenter(g2d, mSettings.getMouseDeltaX(), mSettings.getMouseDeltaY());
    }

    private void drawCircles(Graphics2D g2d, java.util.List<CircleWithArcs> circlesList) {
        CircleWithArcs lastCircle = circlesList.get(circlesList.size() - 1);
        g2d.setColor(mSettings.getFadedArcsColor());
        for (int i = 0; i < circlesList.size() - 1; i++) {
            circlesList.get(i).drawCircle(g2d, mSettings.getMouseDeltaX(), mSettings.getMouseDeltaY());
        }
        g2d.setColor(mSettings.getFadedSubjectCircleColor());
        lastCircle.drawCircle(g2d, mSettings.getMouseDeltaX(), mSettings.getMouseDeltaY());
    }

    private void drawCirclesGradients(Graphics2D g2d, List<CircleWithArcs> circlesList) {
        CircleWithArcs lastCircle = circlesList.get(circlesList.size() - 1);
        for (int i = 0; i < circlesList.size() - 1; i++) {
            circlesList.get(i).drawCircleGradient(g2d, mSettings.getMouseDeltaX(), mSettings.getMouseDeltaY(), mSettings.getFadedArcsColor());
        }
        lastCircle.drawCircleGradient(g2d, mSettings.getMouseDeltaX(), mSettings.getMouseDeltaY(), mSettings.getFadedSubjectCircleColor());
    }
}
