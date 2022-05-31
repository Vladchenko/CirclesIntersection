package circlesintersection.listeners;

import java.awt.*;
import java.awt.event.MouseWheelEvent;

/**
 * Callbacks to UI to update and repaint it.
 */
public interface UIUpdateCallbacks {

    /**
     * Create new arcs, compute their intersections and paint them.
     */
    void createNewArcsAndRepaint();

    /**
     * Update arcs, compute their intersections and paint them.
     *
     * @param point coordinate for a specific circle
     */
    void updateArcsAndRepaint(Point point);

    /**
     * Update arcs, compute their intersections and paint them.
     *
     * @param event on a mouse wheel moving that provides data on position of mouse and
     */
    void updateArcsAndRepaint(MouseWheelEvent event);
}
