package circlesintersection;

import circlesintersection.models.DrawKind;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

/**
 * Represents values needed for the problem domain.
 * FIXME This is an antipattern.
 * https://www.baeldung.com/java-constants-good-practices
 * https://stackoverflow.com/questions/6220726/where-do-you-keep-constants-used-throughout-your-application
 */
public class Settings {

    // region Constants
    // Switching a debug method
    public static final boolean DEBUG_ENABLED = false;
    public static final String APPLICATION_TITLE = "Circles Intersecting v1.3";
    // Draw circles that do not intersect with any other circles with a solid line, not a dashed one.
    public static final boolean DRAW_NOT_INTERSECTED_SOLID = true;
    public static final int SCALE_DISTANCE_DIVISION_FACTOR = 15;
    public static final double ROTATION_ANGLE_STEP = 0.04;
    // endregion Constants

    //region Fields
    private static Settings settings;
    // Number of circles present
    private final int circlesQuantity = 80;
    // Range that a "defaultDiameter" field to be changed at.
    private final int diameterSpan = 150;
    // Value that stands for an increment for a diameter of a circles.
    private final int increment = 50;
    private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private final int canvasWidth = (int) screenSize.getWidth();// - 10; //- (int) screenSize.getWidth() / 12;
    private final int canvasHeight = (int) screenSize.getHeight();// - 8; //- (int) screenSize.getHeight() / 9;

    private int mouseX;
    private int mouseY;
    private int mouseDeltaX;
    private int mouseDeltaY;
    // To use gradient for drawing
    private boolean gradientEnabled = true;
    // To use antialiasing for drawing
    private boolean antiAliasingEnabled = false;
    // Time stamp of the moment of the beginning of a rendering
    private long timeBegin;
    // Time stamp of the moment of the ending of a rendering
    private long timeTemp;

    // Kind of drawing of the circles
    private DrawKind drawKind = DrawKind.BOTH;
    // Flag checks if a Ctrl key is pressed.
    private boolean keyCtrl = false;
    // Flag checks if a Shift key is pressed.
    private boolean keyShift = false;

    // Color for an areas where the arcs are absent (for an array of circles)
    private final Color fadedArcsColor = new Color(25, 61, 25);
    // Color for an areas where the arcs are present (for an array of circles)
    private final Color arcsColor = new Color(100, 255, 100);
    // Color for an areas where the arcs are absent (for a subject circle)
    private final Color fadedSubjectCircleColor = new Color(25, 50, 61);
    // Color for an areas where the arcs are present (for a subject circle)
    private final Color subjectCircleColor = new Color(100, 200, 255);
    //endregion Fields

    private Settings() {
    }

    /**
     * @return Retrieve instance of this class.
     */
    public static Settings getInstance() {
        if (settings == null) {
            settings = new Settings();
        }
        return settings;
    }

    //region Getters & Setters
    public int getMouseDeltaX() {
        return mouseDeltaX;
    }

    public void setMouseDeltaX(int mouseDeltaX) {
        this.mouseDeltaX = mouseDeltaX;
    }

    public int getMouseDeltaY() {
        return mouseDeltaY;
    }

    public void setMouseDeltaY(int mouseDeltaY) {
        this.mouseDeltaY = mouseDeltaY;
    }

    public int getMouseX() {
        return mouseX;
    }

    public void setMouseX(int mouseX) {
        this.mouseX = mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }

    public void setMouseY(int mouseY) {
        this.mouseY = mouseY;
    }

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

    public long getTimeBegin() {
        return timeBegin;
    }

    public void setTimeBegin(long timeBegin) {
        this.timeBegin = timeBegin;
    }

    public long getTimeTemp() {
        return timeTemp;
    }

    public void setTimeTemp(long timeTemp) {
        this.timeTemp = timeTemp;
    }

    public Color getFadedSubjectCircleColor() {
        return fadedSubjectCircleColor;
    }

    public Color getSubjectCircleColor() {
        return subjectCircleColor;
    }

    public Color getFadedArcsColor() {
        return fadedArcsColor;
    }

    public Color getArcsColor() {
        return arcsColor;
    }

    public DrawKind getDrawKind() {
        return drawKind;
    }

    public int getIncrement() {
        return increment;
    }

    public int getCanvasWidth() {
        return canvasWidth;
    }

    public int getCanvasHeight() {
        return canvasHeight;
    }

    public int getCirclesQuantity() {
        return circlesQuantity;
    }

    public int getDiameterSpan() {
        return diameterSpan;
    }

    public boolean isKeyCtrl() {
        return keyCtrl;
    }

    public void setKeyCtrl(boolean keyCtrl) {
        this.keyCtrl = keyCtrl;
    }

    public boolean isKeyShift() {
        return keyShift;
    }

    public void setKeyShift(boolean keyShift) {
        this.keyShift = keyShift;
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
}
