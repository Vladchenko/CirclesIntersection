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
public final class DrawingHelper {

    private final Settings mSettings;
    private static DrawingHelper sDrawingHelper;

    /**
     * Private constructor.
     *
     * @param settings all the settings for the application.
     */
    private DrawingHelper(Settings settings) {
        mSettings = settings;
    }

    /**
     * @param settings all the settings for the application.
     * @return Retrieve instance of this class.
     */
    public static DrawingHelper getInstance(Settings settings) {
        if (sDrawingHelper == null) {
            sDrawingHelper = new DrawingHelper(settings);
        }
        return sDrawingHelper;
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
}
