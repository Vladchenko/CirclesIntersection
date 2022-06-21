package circlesintersection;

import circlesintersection.listeners.UiUpdateListener;
import circlesintersection.models.CircleWithArcs;
import circlesintersection.utils.DrawingUtils;

import java.util.List;

import javax.swing.*;
import java.awt.*;

import static circlesintersection.Settings.DEBUG_ENABLED;

/**
 * Component that draws the circles and its intersecting arcs.
 */
public class CirclesPaintComponent extends JPanel implements UiUpdateListener {

    private List<CircleWithArcs> mCirclesList;
    private final DrawingUtils mDrawingUtils;

    /**
     * Public constructor.
     *
     * @param circlesList  Circles and its arcs to be drawn on a canvas.
     * @param drawingUtils Assists in drawing a circles and arcs.
     */
    public CirclesPaintComponent(List<CircleWithArcs> circlesList, DrawingUtils drawingUtils) {
        mCirclesList = circlesList;
        mDrawingUtils = drawingUtils;
    }

    @Override
    public void paintComponent(Graphics g) {

        super.setBackground(Color.BLACK);
        // Erasing a previous drawing
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        mDrawingUtils.changeIntersectedArcsAppearance(g2d);
        mDrawingUtils.toggleAntiAliasing(g2d);

        mDrawingUtils.drawShapesForAllCircles(g2d, mCirclesList);
        mDrawingUtils.drawNotIntersectedArcs(g2d, mCirclesList);
        if (DEBUG_ENABLED) {
            mDrawingUtils.drawDebugData(g2d, mCirclesList);
        }
        mDrawingUtils.drawFrameDrawingTime(g2d);
    }

    @Override
    public void updateCirclesAndRepaint(List<CircleWithArcs> circlesList) {
        mCirclesList = circlesList;
        repaint();
    }

    @Override
    public void toggleFullScreen() {
        mDrawingUtils.toggleFullScreen(
                this,
                GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0]);
    }
}
