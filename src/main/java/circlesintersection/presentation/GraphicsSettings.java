package circlesintersection.presentation;

/**
 * Represents values for drawing on a canvas.
 */
public class GraphicsSettings {

    // region Constants
    // Switching a debug method
    public static final boolean DEBUG_ENABLED = false;
    // endregion Constants

    //region Fields
    private static GraphicsSettings graphicsSettings;

    // To use gradient for drawing
    private boolean gradientEnabled = true;
    // To use antialiasing for drawing
    private boolean antiAliasingEnabled = false;

    // Kind of drawing of the circles
    private DrawKind drawKind = DrawKind.BOTH;

    //endregion Fields

    private GraphicsSettings() {
    }

    /**
     * @return Instance of this class.
     */
    public static GraphicsSettings getInstance() {
        if (graphicsSettings == null) {
            graphicsSettings = new GraphicsSettings();
        }
        return graphicsSettings;
    }

    //region Getters & Setters
    public boolean isAntiAliasingEnabled() {
        return antiAliasingEnabled;
    }

    public void setAntiAliasingEnabled(boolean antiAliasingEnabled) {
        this.antiAliasingEnabled = antiAliasingEnabled;
    }

    public boolean isGradientEnabled() {
        return gradientEnabled;
    }

    public void setGradientEnabled(boolean gradientEnabled) {
        this.gradientEnabled = gradientEnabled;
    }

    public DrawKind getDrawKind() {
        return drawKind;
    }

    //endregion Getters & Setters

    /**
     * Circle through a mode for drawing.
     */
    public void changeDrawingMode() {
        if (drawKind == DrawKind.ARCS) {
            drawKind = DrawKind.CIRCLES;
        } else if (drawKind == DrawKind.CIRCLES) {
            drawKind = DrawKind.BOTH;
        } else if (drawKind == DrawKind.BOTH) {
            drawKind = DrawKind.ARCS;
        }
    }

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
    }
}
