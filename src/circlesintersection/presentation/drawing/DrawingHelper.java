package circlesintersection.presentation.drawing;

import circlesintersection.presentation.GraphicsSettings;
import circlesintersection.models.CircleWithArcs;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Arc2D;
import java.util.List;

import static circlesintersection.presentation.drawing.DrawingConsts.ARCS_COLOR;
import static circlesintersection.presentation.drawing.DrawingConsts.SUBJECT_CIRCLE_COLOR;

/**
 * Utils methods for drawing the shapes on canvas.
 */
public final class DrawingHelper {

    private final GraphicsSettings mGraphicsSettings;
    private final FrameTimeCounter mFrameTimeCounter;
    private static DrawingHelper sDrawingHelper;
    private List<CircleWithArcs> mCirclesList;

    /**
     * Private constructor.
     *
     * @param graphicsSettings    all the settings for the application.
     * @param circlesList circles and their arcs to be drawn on a canvas.
     */
    private DrawingHelper(GraphicsSettings graphicsSettings,
                          List<CircleWithArcs> circlesList,
                          FrameTimeCounter frameTimeCounter) {
        mGraphicsSettings = graphicsSettings;
        mFrameTimeCounter = frameTimeCounter;
        mCirclesList = circlesList;
    }

    /**
     * @param graphicsSettings all the settings for the application.
     * @return Retrieve instance of this class.
     */
    public static DrawingHelper getInstance(GraphicsSettings graphicsSettings,
                                            List<CircleWithArcs> circlesList,
                                            FrameTimeCounter frameTimeCounter) {
        if (sDrawingHelper == null) {
            sDrawingHelper = new DrawingHelper(graphicsSettings, circlesList, frameTimeCounter);
        }
        return sDrawingHelper;
    }

    /**
     * Turns full screen mode on or off
     *
     * @param g2d Graphics component to perform drawing.
     */
    public void toggleAntiAliasing(Graphics2D g2d) {
        if (mGraphicsSettings.isAntiAliasingEnabled()) {
            // Smooths the picture, but slows down drawing a lot
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }
    }

    /**
     * Draw arcs for this circle that do not intersect with any other circle.
     *
     * @param g2d Graphics component to perform drawing.
     */
    public void drawNotIntersectedArcs(Graphics2D g2d) {
        g2d.setStroke(       //new BasicStroke(2));
                new BasicStroke(4,
                        BasicStroke.CAP_ROUND,
                        BasicStroke.JOIN_ROUND,
                        2, null, 2.0f));
        g2d.setColor(ARCS_COLOR);

        // Drawing an arcs for a not intersected areas of circles
        if (mGraphicsSettings.getDrawKind().equals(GraphicsSettings.DrawKind.ARCS)
                || mGraphicsSettings.getDrawKind().equals(GraphicsSettings.DrawKind.BOTH)) {
            for (int i = 0; i < mCirclesList.size() - 1; i++) {
                if (!mCirclesList.get(i).isExcluded()) {
                    for (Arc2D arc2D : mCirclesList.get(i).getArcs2D()) {
                        g2d.draw(arc2D);
                    }
                }
            }
            g2d.setColor(SUBJECT_CIRCLE_COLOR);
            // Drawing a last circle with a different color
            CircleWithArcs lastCircle = mCirclesList.get(mCirclesList.size() - 1);
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
        mFrameTimeCounter.setTimeBegin(System.nanoTime());
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

    public void setCirclesList(List<CircleWithArcs> circlesList) {
        mCirclesList = circlesList;
    }
}
