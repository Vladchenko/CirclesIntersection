package circlesintersection.listeners;

import circlesintersection.Arcs;

/**
 * Callbacks to UI to update and repaint it.
 */
public interface UiUpdateListener {

    /**
     * Update arcs, compute their intersections and repaint a canvas.
     */
    void updateArcsAndRepaint(Arcs arcs);

    /**
     * Toggles full screen mode.
     */
    void toggleFullScreen();
}
