package circlesintersection.listeners;

import circlesintersection.models.CircleWithArcs;

import java.util.List;

/**
 * Callbacks to UI to update and repaint it.
 */
public interface UiUpdateListener {

    /**
     * Update circles, compute their intersections(i.e. arcs) and repaint a canvas.
     */
    void updateCirclesAndRepaint(List<CircleWithArcs> circleWithArcsList);

    /**
     * Toggles full screen mode.
     */
    void toggleFullScreen();
}
