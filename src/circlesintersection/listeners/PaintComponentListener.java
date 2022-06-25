package circlesintersection.listeners;

import circlesintersection.CirclesPaintComponent;
import circlesintersection.models.CircleWithArcs;
import circlesintersection.utils.DrawingHelper;

import java.awt.*;
import java.util.List;

/**
 * Updates (repaints) paint component, when specific event occurs.
 */
public class PaintComponentListener implements UiUpdateListener {

    private final DrawingHelper mDrawingHelper;
    private final CirclesPaintComponent mPaintComponent;

    /**
     * Public constructor.
     *
     * @param paintComponent    Component that draws the circles and its intersecting arcs.
     * @param drawingHelper     Utils methods for drawing the shapes on canvas.
     */
    public PaintComponentListener(CirclesPaintComponent paintComponent,
                                  DrawingHelper drawingHelper) {
        mPaintComponent = paintComponent;
        mDrawingHelper = drawingHelper;
    }

    @Override
    public void updateCirclesAndRepaint(List<CircleWithArcs> circlesList) {
        mPaintComponent.setCirclesList(circlesList);
        mPaintComponent.repaint();
    }

    @Override
    public void toggleFullScreen() {
        mDrawingHelper.toggleFullScreen(
                mPaintComponent,
                GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0]);
    }
}
