package circlesintersection.presentation.drawing;

import circlesintersection.IllegalInstantiationException;

import java.awt.*;

/**
 * Utils to draw circles.
 */
public class CircleDrawingUtils {

    /**
     * Private constructor.
     */
    private CircleDrawingUtils() {
        throw new IllegalInstantiationException("CircleDrawingUtils should not be instantiated!");
    }

    /**
     * Draw a center of a circle.
     *
     * @param g2d Graphics component to perform drawing.
     */
    public static void drawCircleCenter(Graphics2D g2d, int x, int y) {
        g2d.drawOval(x, y, 1, 1);
    }

    /**
     * Draw a circle.
     *
     * @param g2d Graphics component to perform drawing.
     */
    public static void drawCircle(Graphics2D g2d, int diameter, int x, int y) {
        g2d.drawOval(x - (diameter / 2), y - (diameter / 2), diameter, diameter);
    }

    /**
     * Drawing a circled gradient at the center of circle
     *
     * @param g2d    Graphics component to perform drawing.
     * @param x Extra X ordinate pixels.
     * @param y Extra Y ordinate pixels.
     * @param color  Circle's gradient color
     */
    public static void drawCircleGradient(Graphics2D g2d, int diameter, int x, int y, Color color) {
        RadialGradientPaint gradientPaint = new RadialGradientPaint(
                x,
                y,
                diameter / 2f,
                new float[]{(float) 0.0, (float) 1.0},
                new Color[]{color, new Color(0, 0, 0, 0)});
        g2d.setPaint(gradientPaint);
        g2d.fillOval(x - diameter / 2, y - diameter / 2, diameter, diameter);
    }
}
