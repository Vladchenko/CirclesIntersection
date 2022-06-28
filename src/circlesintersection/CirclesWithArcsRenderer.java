package circlesintersection;

import circlesintersection.listeners.CirclesRendererListener;
import circlesintersection.presentation.UiUpdateListener;
import circlesintersection.models.CircleWithArcs;
import com.sun.istack.internal.Nullable;

import java.awt.*;
import java.util.List;

import static circlesintersection.utils.CircleUtils.*;
import static circlesintersection.utils.geometry.CirclesGeometryUtils.*;
import static circlesintersection.utils.geometry.IntersectionUtils.computeIntersections;

/**
 * Performs operations on circles, such as creation, update, rotation, scaling and notifies UI about it.
 */
public class CirclesWithArcsRenderer implements CirclesRendererListener {

    private final Settings mSettings;
    private final List<CircleWithArcs> mCirclesList;
    private final UiUpdateListener mUiUpdateListener;

    /**
     * Public constructor
     *
     * @param circlesList      circles and their arcs to be drawn on a canvas.
     * @param settings         all the settings for the application.
     * @param uiUpdateListener listener to update UI, when some operation is performed.
     */
    public CirclesWithArcsRenderer(List<CircleWithArcs> circlesList,
                                   Settings settings,
                                   UiUpdateListener uiUpdateListener) {
        mCirclesList = circlesList;
        mSettings = settings;
        mUiUpdateListener = uiUpdateListener;
        computeIntersections(mCirclesList, 0, 0);
    }

    @Override
    public void scatterCirclesComputeArcsAndRepaint() {
        mSettings.setTimeBegin(System.nanoTime());
        initiateCircles(mCirclesList);
        randomizeCircles(mCirclesList,
                mSettings.getCanvasWidth(),
                mSettings.getCanvasHeight(),
                mSettings.getDiameterSpan(),
                mSettings.getIncrement());
        computeIntersectionsAndRepaintCanvas(0, 0);
    }

    @Override
    public void updateCirclesAndRepaint(@Nullable Point point) {
        mSettings.setTimeBegin(System.nanoTime());
        initiateCircles(mCirclesList);
        setCircleToMousePosition(mCirclesList.get(mCirclesList.size() - 1));
        computeIntersectionsAndRepaintCanvas(0, 0);
    }

    @Override
    public void updateCirclesAndRepaint(Point point, int wheelRotationValue) {
        mSettings.setTimeBegin(System.nanoTime());
        initiateCircles(mCirclesList);
        CircleWithArcs lastCircle = mCirclesList.get(mCirclesList.size() - 1);
        setCircleToMousePosition(lastCircle);
        changeCircleDiameter(lastCircle, wheelRotationValue);
        computeIntersectionsAndRepaintCanvas(0, 0);
    }

    @Override
    public void rotateCirclesAndRepaint(int wheelRotation) {
        mSettings.setTimeBegin(System.nanoTime());
        rotateCircles(mCirclesList, wheelRotation);
        computeIntersectionsAndRepaintCanvas(0, 0);
    }

    @Override
    public void scaleCirclesAndRepaint(int wheelRotation) {
        mSettings.setTimeBegin(System.nanoTime());
        prepareCirclesWhenRotatedOrScaled(mCirclesList);
        scaleCircles(mCirclesList, wheelRotation);
        computeIntersectionsAndRepaintCanvas(0, 0);
    }

    @Override
    public void dragCirclesAndRepaint(Point point) {
        mSettings.setTimeBegin(System.nanoTime());
        prepareCirclesWhenDragged(mCirclesList);
        int deltaX = mSettings.getMouseX() - point.x;
        int deltaY = mSettings.getMouseY() - point.y;
        mSettings.setMouseDeltaX(deltaX);
        mSettings.setMouseDeltaY(deltaY);
        computeIntersectionsAndRepaintCanvas(deltaX, deltaY);
    }

    @Override
    public void dropCirclesAndRepaint() {
        mSettings.setTimeBegin(System.nanoTime());
        for (CircleWithArcs circle : mCirclesList) {
            circle.setX(circle.getX() - mSettings.getMouseDeltaX());
            circle.setY(circle.getY() - mSettings.getMouseDeltaY());
        }
        mSettings.setMouseDeltaX(0);
        mSettings.setMouseDeltaY(0);
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
