package circlesintersection.presentation;

import circlesintersection.models.CircleWithArcs;
import circlesintersection.presentation.drawing.DebugDrawingHelper;
import circlesintersection.presentation.drawing.DrawingHelper;
import circlesintersection.presentation.drawing.CirclesDrawingHelper;

import java.awt.*;
import java.util.List;

/**
 * Updates (repaints) paint component, when specific event occurs.
 */
public class UiUpdateListenerImpl implements UiUpdateListener {

    private final DrawingHelper mDrawingHelper;
    private final PaintComponent mPaintComponent;
    private final DebugDrawingHelper mDebugDrawingHelper;
    private final CirclesDrawingHelper mCirclesDrawingHelper;

    /**
     * Public constructor.
     *
     * @param drawingHelper        Utils methods for drawing the shapes on canvas.
     * @param debugDrawingHelper   Helper methods to draw a debug info on canvas.
     * @param circlesDrawingHelper Helper methods for CircleWithArcs list drawing.
     * @param paintComponent       Component that draws the circles and its intersecting arcs.
     */
    public UiUpdateListenerImpl(DrawingHelper drawingHelper,
                                DebugDrawingHelper debugDrawingHelper,
                                CirclesDrawingHelper circlesDrawingHelper,
                                PaintComponent paintComponent) {
        mCirclesDrawingHelper = circlesDrawingHelper;
        mDebugDrawingHelper = debugDrawingHelper;
        mPaintComponent = paintComponent;
        mDrawingHelper = drawingHelper;
    }

    @Override
    public void updateCirclesAndRepaint(List<CircleWithArcs> circlesList) {
        mDrawingHelper.setCirclesList(circlesList);
        mDebugDrawingHelper.setCirclesList(circlesList);
        mCirclesDrawingHelper.setCirclesList(circlesList);
        mPaintComponent.repaint();
    }

    @Override
    public void toggleFullScreen() {
        mDrawingHelper.toggleFullScreen(
                mPaintComponent,
                GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0]);
    }
}
