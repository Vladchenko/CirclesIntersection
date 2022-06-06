package circlesintersection.listeners;

import java.awt.*;

/**
 * Callbacks to UI to update and repaint it.
 */
public interface UiUpdateListener {

    /**
     * Create new arcs, compute their intersections and paint them.
     */
    void createNewArcsAndRepaint();

    /**
     * Update arcs, compute their intersections and paint them.
     */
    void updateArcsAndRepaint();

    /**
     * Update arcs, compute their intersections and paint them.
     *
     * @param point coordinate for a specific circle
     */
    void updateArcsAndRepaint(Point point);

    /**
     * Update arcs, compute their intersections and paint them.
     *
     * @param point mouse x,y position
     * @param wheelRotationValue    mouse wheel moved value
     */
    void updateArcsAndRepaint(Point point, int wheelRotationValue);

    /**
     * Sets full screen mode to be on or off.
     */
    void toggleFullScreen();
}
