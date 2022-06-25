package circlesintersection;

import circlesintersection.models.CircleWithArcs;
import circlesintersection.utils.DebugDrawingHelper;
import circlesintersection.utils.DrawingHelper;
import circlesintersection.utils.circlewitharcs.CirclesDrawingHelper;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import static circlesintersection.Settings.DEBUG_ENABLED;

/**
 * Component that draws the circles and its intersecting arcs.
 */
public class CirclesPaintComponent extends JPanel {

    private List<CircleWithArcs> mCirclesList;
    private final DrawingHelper mDrawingHelper;
    private final DebugDrawingHelper mDebugDrawingHelper;
    private final CirclesDrawingHelper mCirclesDrawingHelper;

    /**
     * Public constructor.
     *
     * @param circlesList  Circles and its arcs to be drawn on a canvas.
     * @param drawingHelper Assists in drawing a circles and arcs.
     */
    public CirclesPaintComponent(List<CircleWithArcs> circlesList,
                                 DrawingHelper drawingHelper,
                                 CirclesDrawingHelper circlesDrawingHelper,
                                 DebugDrawingHelper debugDrawingHelper) {
        mCirclesList = circlesList;
        mDrawingHelper = drawingHelper;
        mDebugDrawingHelper = debugDrawingHelper;
        mCirclesDrawingHelper = circlesDrawingHelper;
    }

    @Override
    public void paintComponent(Graphics g) {

        super.setBackground(Color.BLACK);
        // Erasing a previous drawing
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        mDrawingHelper.changeIntersectedArcsAppearance(g2d);
        mDrawingHelper.toggleAntiAliasing(g2d);

        mCirclesDrawingHelper.drawShapesForAllCircles(g2d, mCirclesList);
        mDrawingHelper.drawNotIntersectedArcs(g2d, mCirclesList);
        if (DEBUG_ENABLED) {
            mDebugDrawingHelper.drawDebugData(g2d, mCirclesList);
        }
        mDebugDrawingHelper.drawFrameDrawingTime(g2d);
    }

    public void setCirclesList(List<CircleWithArcs> circlesList) {
        mCirclesList = circlesList;
    }
}
