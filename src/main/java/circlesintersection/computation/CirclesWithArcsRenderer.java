package circlesintersection.computation;

import circlesintersection.listeners.CirclesRendererListener;
import circlesintersection.presentation.UiUpdateListener;
import circlesintersection.models.CircleWithArcs;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.List;

import static circlesintersection.listeners.MouseListenerImpl.MOUSE_POINTER;
import static circlesintersection.listeners.MouseListenerImpl.MOUSE_POINTER_DELTA;
import static circlesintersection.models.CircleWithArcs.DIAMETER_MINIMUM;
import static circlesintersection.models.CircleWithArcs.DIAMETER_RANDOM_RANGE;
import static circlesintersection.presentation.Canvas.CANVAS_HEIGHT;
import static circlesintersection.presentation.Canvas.CANVAS_WIDTH;
import static circlesintersection.computation.CircleUtils.*;
import static circlesintersection.computation.geometry.CirclesGeometryUtils.*;
import static circlesintersection.computation.geometry.IntersectionUtils.computeIntersections;

/**
 * Performs operations on circles, such as creation, update, rotation, scaling and notifies UI about it.
 */
public class CirclesWithArcsRenderer implements CirclesRendererListener {

    private final List<CircleWithArcs> mCirclesList;
    private final UiUpdateListener mUiUpdateListener;

    /**
     * Public constructor
     *
     * @param circlesList      circles and their arcs to be drawn on a canvas.
     * @param uiUpdateListener listener to update UI, when some operation is performed.
     */
    public CirclesWithArcsRenderer(List<CircleWithArcs> circlesList,
                                   UiUpdateListener uiUpdateListener) {
        mCirclesList = circlesList;
        mUiUpdateListener = uiUpdateListener;
        computeIntersections(mCirclesList, 0, 0);
    }

    @Override
    public void scatterCirclesComputeArcsAndRepaint() {
        initiateCircles(mCirclesList);
        randomizeCircles(mCirclesList,
                CANVAS_WIDTH,
                CANVAS_HEIGHT,
                DIAMETER_RANDOM_RANGE,
                DIAMETER_MINIMUM);
        computeIntersectionsAndRepaintCanvas(0, 0);
    }

    @Override
    public void updateCirclesAndRepaint(@Nullable Point point) {
        initiateCircles(mCirclesList);
        setCircleToMousePosition(mCirclesList.get(mCirclesList.size() - 1));
        computeIntersectionsAndRepaintCanvas(0, 0);
    }

    @Override
    public void updateCirclesAndRepaint(Point point, int wheelRotationValue) {
        initiateCircles(mCirclesList);
        CircleWithArcs lastCircle = mCirclesList.get(mCirclesList.size() - 1);
        setCircleToMousePosition(lastCircle);
        changeCircleDiameter(lastCircle, wheelRotationValue);
        computeIntersectionsAndRepaintCanvas(0, 0);
    }

    @Override
    public void rotateCirclesAndRepaint(int wheelRotation) {
        rotateCircles(mCirclesList, wheelRotation);
        computeIntersectionsAndRepaintCanvas(0, 0);
    }

    @Override
    public void scaleCirclesAndRepaint(int wheelRotation) {
        prepareCirclesWhenRotatedOrScaled(mCirclesList);
        scaleCircles(mCirclesList, wheelRotation);
        computeIntersectionsAndRepaintCanvas(0, 0);
    }

    @Override
    public void dragCirclesAndRepaint(Point point) {
        prepareCirclesWhenDragged(mCirclesList);
        int deltaX = (int) MOUSE_POINTER.getX() - point.x;
        int deltaY = (int) MOUSE_POINTER.getY() - point.y;
        MOUSE_POINTER_DELTA.setLocation(deltaX, deltaY);
        computeIntersectionsAndRepaintCanvas(deltaX, deltaY);
    }

    @Override
    public void dropCirclesAndRepaint() {
        for (CircleWithArcs circle : mCirclesList) {
            circle.setX(circle.getX() - MOUSE_POINTER_DELTA.getX());
            circle.setY(circle.getY() - MOUSE_POINTER_DELTA.getY());
        }
        MOUSE_POINTER_DELTA.setLocation(0, 0);
    }

    @Override
    public void toggleFullScreen() {
        mUiUpdateListener.toggleFullScreen();
    }

    private void computeIntersectionsAndRepaintCanvas(int deltaX, int deltaY) {
        computeIntersections(mCirclesList, deltaX, deltaY);
        mUiUpdateListener.updateCirclesAndRepaint(mCirclesList);
    }
}
