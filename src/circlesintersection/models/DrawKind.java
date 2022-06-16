package circlesintersection.models;

/**
 * Defines what kind of drawing is applied.
 */
public enum DrawKind {
    /**
     * Only dashed circles are drawn.
     */
    CIRCLES,
    /**
     * Only intersected arcs are drawn.
     */
    ARCS,
    /**
     * Both - dashed circles and intersected arcs.
     */
    BOTH
};
