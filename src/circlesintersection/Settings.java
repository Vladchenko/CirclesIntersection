package circlesintersection;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

/**
 * Represents value needed for the domain.
 */
public class Settings {

    // Switching a debug method
    public static final boolean DEBUG_ENABLED = false;
    public static final String APPLICATION_TITLE = "Circles Intersecting v1.2";

    //<editor-fold defaultstate="collapsed" desc="Fields">
    /** 
     * Include circles that do not intersect with any other circles into 
     * multiplicity of drawing circles drawn with solid line, not dashed.
     */
    private static final boolean INCLUDE_NOT_INTERSECTED = true;

    private final int populationMode = 0;  // Circles population mode
    // Number of circles present on a screen
    private final int circlesQuantity = 80;
    // Defines a range that a "defaultDiameter" field to be changed at.
    private final int diameterSpan = 150;
    private final int defaultDiameter = 200;
    private final int angleBeginSpan = 180;
    private final int angleValidSpan = 360;
    private final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private final int canvasWidth = (int) screenSize.getWidth();// - 10; //- (int) screenSize.getWidth() / 12;
    private final int canvasHeight = (int) screenSize.getHeight();// - 8; //- (int) screenSize.getHeight() / 9;
    private final int canvasWidthHalf = canvasWidth / 2;// - 10;
    private final int canvasHeightHalf = canvasHeight / 2;// - 24;

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
    private DrawKind drawKind = DrawKind.both;
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
    
    public int getDefaultDiameter() {
        return defaultDiameter;
    }
    
    public int getCanvasWidthHalf() {
        return canvasWidthHalf;
    }
    
    public int getCanvasHeightHalf() {
        return canvasHeightHalf;
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
    
    public int getPopulationMode() {
        return populationMode;
    }
    
    public int getAngleBeginSpan() {
        return angleBeginSpan;
    }
    
    public int getAngleValidSpan() {
        return angleValidSpan;
    }
    
    public static boolean isINCLUDE_NOT_INTERSECTED() {
        return INCLUDE_NOT_INTERSECTED;
    }
    //</editor-fold>

    /**
     * Circle through a mode for drawing.
     */
    public void changeDrawingMode() {
        if (drawKind == DrawKind.arcs) {
            drawKind = DrawKind.circles;
        } else
        if (drawKind == DrawKind.circles) {
            drawKind = DrawKind.both;
        } else
        if (drawKind == DrawKind.both) {
            drawKind = DrawKind.arcs;
        }
    }
}
