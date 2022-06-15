package circlesintersection.listeners;

import java.awt.*;

/**
 * Performs operations on arcs, such as creation, update, rotation, scaling and notifies UI about it.
 */
public interface ArcsRendererListener {

    /**
     * Create new arcs, compute their intersections and paint them.
     */
    void createNewArcsAndRepaint();

    /**
     * Update arcs, compute their intersections and repaint a canvas.
     *
     * @param point coordinate for a specific circle
     */
    void updateArcsAndRepaint(Point point);

    /**
     * Update arcs, compute their intersections and repaint a canvas.
     *
     * @param point mouse x,y position
     * @param wheelRotationValue    mouse wheel moved value
     */
    void updateArcsAndRepaint(Point point, int wheelRotationValue);

    /**
     * Rotate arcs around a chosen arc and repaint a canvas.
     *
     * @param wheelRotation which way a mouse is rotated
     */
    void rotateArcsAndRepaint(int wheelRotation);

    /**
     * Scale the arcs relatively to chosen one and repaint a canvas.
     *
     * @param wheelRotation which way a mouse is rotated
     */
    void scaleArcsAndRepaint(int wheelRotation);

    /**
     * Drag mouse and repaint a canvas.
     *
     * @param point mouse cursor location
     */
    void dragArcsAndRepaint(Point point);

    /**
     * Drop mouse dragging and repaint a canvas.
     */
    void dropArcsAndRepaint();

    /**
     * Enter or quit full screen
     */
    void toggleFullScreen();
}
