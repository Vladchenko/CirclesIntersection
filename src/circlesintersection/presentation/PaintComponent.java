package circlesintersection.presentation;

import circlesintersection.presentation.drawing.CirclesDrawingHelper;
import circlesintersection.presentation.drawing.DebugDrawingHelper;
import circlesintersection.presentation.drawing.DrawingHelper;

import javax.swing.*;
import java.awt.*;

import static circlesintersection.Settings.DEBUG_ENABLED;

/**
 * Component that draws the circles and its intersecting arcs.
 */
public class PaintComponent extends JPanel {

    private final DrawingHelper mDrawingHelper;
    private final DebugDrawingHelper mDebugDrawingHelper;
    private final CirclesDrawingHelper mCirclesDrawingHelper;

    /**
     * Public constructor.
     *
     * @param drawingHelper         Assists in drawing a circles and arcs.
     * @param circlesDrawingHelper  Helper methods for CircleWithArcs list drawing.
     * @param debugDrawingHelper    Helper methods to draw a debug info on canvas.
     */
    public PaintComponent(DrawingHelper drawingHelper,
                          CirclesDrawingHelper circlesDrawingHelper,
                          DebugDrawingHelper debugDrawingHelper) {
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

        mCirclesDrawingHelper.drawShapesForAllCircles(g2d);
        mDrawingHelper.drawNotIntersectedArcs(g2d);
        if (DEBUG_ENABLED) {
            mDebugDrawingHelper.drawDebugData(g2d);
        }
        mDebugDrawingHelper.drawFrameDrawingTime(g2d);
    }
}
