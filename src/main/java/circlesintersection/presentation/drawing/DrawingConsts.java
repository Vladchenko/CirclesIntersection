package circlesintersection.presentation.drawing;

import circlesintersection.IllegalInstantiationException;

import java.awt.*;

/**
 * Color consts for drawing on canvas.
 */
public final class DrawingConsts {

    // Color for an areas where the arcs are absent (for an array of circles)
    public static final Color FADED_ARCS_COLOR = new Color(25, 61, 25);
    // Color for an areas where the arcs are present (for an array of circles)
    public static final Color ARCS_COLOR = new Color(100, 255, 100);
    // Color for an areas where the arcs are absent (for a subject circle)
    public static final Color FADED_SUBJECT_CIRCLE_COLOR = new Color(25, 50, 61);
    // Color for an areas where the arcs are present (for a subject circle)
    public static final Color SUBJECT_CIRCLE_COLOR = new Color(100, 200, 255);

    private DrawingConsts() {
        throw new IllegalInstantiationException("DrawingConsts should not be instantiated!");
    }
}
