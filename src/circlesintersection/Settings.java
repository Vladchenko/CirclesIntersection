package circlesintersection;

import circlesintersection.models.DrawKind;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

/**
 * Represents value needed for the domain.
 */
public class Settings {

    //<editor-fold defaultstate="collapsed" desc="Fields">
    // Switching a debug method
    public static final boolean DEBUG_ENABLED = false;
    public static final String APPLICATION_TITLE = "Circles Intersecting v1.3";
    /*
     * Draw circles that do not intersect with any other circles with a solid line, not a dashed one.
     */
    public static final boolean DRAW_NOT_INTERSECTED_SOLID = true;

    // Number of circles present on a screen
    private final int circlesQuantity = 80;
    // Range that a "defaultDiameter" field to be changed at.
    private final int diameterSpan = 150;
    private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private final int canvasWidth = (int) screenSize.getWidth();// - 10; //- (int) screenSize.getWidth() / 12;
    private final int canvasHeight = (int) screenSize.getHeight();// - 8; //- (int) screenSize.getHeight() / 9;

    // To use gradient for drawing
    private boolean gradientEnabled = true;
    // To use antialiasing for drawing
    private boolean antiAliasingEnabled = false;
    // Time stamp of the moment of the beginning of a rendering
    private long timeBegin;
    // Time stamp of the moment of the ending of a rendering
    private long timeTemp;
    // Value that stands for an increment for a diameter of a circles.
    private final int increment = 50;
    // Color for an areas where the arcs are absent (for an array of circles)
    private final Color fadedArcsColor = new Color(25, 61, 25);
    // Color for an areas where the arcs are present (for an array of circles)
    private final Color arcsColor = new Color(100, 255, 100);
    // Color for an areas where the arcs are absent (for a subject circle)
    private final Color fadedSubjectCircleColor = new Color(25, 50, 61);
    // Color for an areas where the arcs are present (for a subject circle)
    private final Color subjectCircleColor = new Color(100, 200, 255);
    private static Settings settings;
    // Kind of drawing of the circles
    private DrawKind drawKind = DrawKind.BOTH;
    // Flag checks if a Ctrl key is pressed.
    private boolean keyCtrl = false;
    // Flag checks if a Shift key is pressed.
    private boolean keyShift = false;
//</editor-fold>

    // Utility class should not be instantiated through a constructor.
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

    //<editor-fold defaultstate="collapsed" desc="Getters & Setters">
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
    //</editor-fold>

    /**
     * Circle through a mode for drawing.
     */
    public void changeDrawingMode() {
        if (drawKind == DrawKind.ARCS) {
            drawKind = DrawKind.CIRCLES;
        } else
        if (drawKind == DrawKind.CIRCLES) {
            drawKind = DrawKind.BOTH;
        } else
        if (drawKind == DrawKind.BOTH) {
            drawKind = DrawKind.ARCS;
        }
    }
}
