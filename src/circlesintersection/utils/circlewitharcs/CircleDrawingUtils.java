package circlesintersection.utils.circlewitharcs;

import circlesintersection.models.CircleWithArcs;

import java.awt.*;
import java.util.List;

/**
 * Utils to draw circles.
 */
public class CircleDrawingUtils {

    /**
     * Private constructor.
     */
    private CircleDrawingUtils() {
        throw new RuntimeException("CircleDrawingUtils should not be instantiated!");
    }

    /**
     * Draw a center of a circle.
     *
     * @param g2d Graphics component to perform drawing.
     */
    public static void drawCircleCenter(Graphics2D g2d, CircleWithArcs circleWithArcs, int deltaX, int deltaY) {
        g2d.drawOval(
                (int)(circleWithArcs.getX() - deltaX),
                (int)(circleWithArcs.getY() - deltaY),
                1, 1);
    }

    /**
     * Draw a circle.
     *
     * @param g2d Graphics component to perform drawing.
     */
    public static void drawCircle(Graphics2D g2d, CircleWithArcs circleWithArcs, int mouseX, int mouseY) {
        g2d.drawOval((int)(circleWithArcs.getX() - mouseX) - (int) (circleWithArcs.getDiameter() / 2),
                (int)(circleWithArcs.getY() - mouseY) - (int) (circleWithArcs.getDiameter() / 2),
                (int) circleWithArcs.getDiameter(), (int) circleWithArcs.getDiameter());
    }

    /**
     * Drawing a circled gradient at the center of circle
     *
     * @param g2d    Graphics component to perform drawing.
     * @param deltaX Extra X ordinate pixels.
     * @param deltaY Extra Y ordinate pixels.
     * @param color  Circle's gradient color
     */
    public static void drawCircleGradient(Graphics2D g2d,
                                   CircleWithArcs circleWithArcs,
                                   int deltaX, int deltaY,
                                   Color color) {
        RadialGradientPaint gradientPaint = new RadialGradientPaint(
                (float) circleWithArcs.getX() - deltaX,
                (float) circleWithArcs.getY() - deltaY,
                (float) (circleWithArcs.getDiameter() / 2),
                new float[]{(float) 0.0, (float) 1.0},
                new Color[]{color, new Color(0, 0, 0, 0)});
        g2d.setPaint(gradientPaint);
        g2d.fillOval((int)(circleWithArcs.getX() - deltaX) - (int) (circleWithArcs.getDiameter() / 2),
                (int)(circleWithArcs.getY() - deltaY) - (int) (circleWithArcs.getDiameter() / 2),
                (int) circleWithArcs.getDiameter(), (int) circleWithArcs.getDiameter());
    }
}
