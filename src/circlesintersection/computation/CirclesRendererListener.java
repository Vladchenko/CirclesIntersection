package circlesintersection.computation;

import java.awt.*;

/**
 * Performs operations on arcs, such as creation, update, rotation, scaling and notifies UI about it.
 */
public interface CirclesRendererListener {

    /**
     * Create new circles, compute their intersections(arcs) and repaint a canvas.
     */
    void scatterCirclesComputeArcsAndRepaint();

    /**
     * Update circles, compute their intersections(arcs) and repaint a canvas.
     *
     * @param point coordinate for a specific circle
     */
    void updateCirclesAndRepaint(Point point);

    /**
     * Update circles, compute their intersections(arcs) and repaint a canvas.
     *
     * @param point              mouse x,y position
     * @param wheelRotationValue mouse wheel moved value
     */
    void updateCirclesAndRepaint(Point point, int wheelRotationValue);

    /**
     * Rotate circles around a chosen circle and repaint a canvas.
     *
     * @param wheelRotation which way a mouse is rotated
     */
    void rotateCirclesAndRepaint(int wheelRotation);

    /**
     * Scale the circles relatively to chosen one and repaint a canvas.
     *
     * @param wheelRotation which way a mouse is rotated
     */
    void scaleCirclesAndRepaint(int wheelRotation);

    /**
     * Drag mouse and repaint a canvas.
     *
     * @param point mouse cursor location
     */
    void dragCirclesAndRepaint(Point point);

    /**
     * Drop mouse dragging and repaint a canvas.
     */
    void dropCirclesAndRepaint();

    /**
     * Enter or quit full screen
     */
    void toggleFullScreen();
}
