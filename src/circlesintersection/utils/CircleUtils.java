package circlesintersection.utils;

import circlesintersection.models.CircleWithArcs;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Utils to assist circles' arcs computation.
 */
public class CircleUtils {

    /**
     * Populate circles with its instances.
     *
     * @param circlesList     List of circles
     * @param circlesQuantity number of circles to be present in a list
     */
    public static void populateCircles(List<CircleWithArcs> circlesList, int circlesQuantity) {
        for (int i = 0; i < circlesQuantity; i++) {
            circlesList.add(new CircleWithArcs());
        }
    }

    /**
     * Randomly locates circles on a screen and defines its diameters.
     *
     * @param circlesList       list of a circles
     * @param canvasWidth       width at which x ordinate of a circle is randomly defined
     * @param canvasHeight      height at which y ordinate of a circle is randomly defined
     * @param diameterSpan      value within which a circle's diameter is defined
     * @param diameterIncrement diameter scale
     */
    public static void randomizeCircles(List<CircleWithArcs> circlesList,
                                        int canvasWidth,
                                        int canvasHeight,
                                        int diameterSpan,
                                        int diameterIncrement) {
        for (CircleWithArcs circle : circlesList) {
            circle.setX((int) (Math.random() * (canvasWidth - diameterSpan * 2) + diameterSpan));
            circle.setY((int) (Math.random() * (canvasHeight - diameterSpan * 2) + diameterSpan));
            circle.setDiameter(Math.random() * diameterSpan + diameterIncrement);
        }
        setCircleToMousePosition(circlesList.get(circlesList.size() - 1));
    }

    /**
     * When circles rotated, arcs have to be recomputed.
     *
     * @param circlesList list of a circles
     */
    public static void prepareCirclesWhenRotated(List<CircleWithArcs> circlesList) {
        initiateCircles(circlesList);
        setCircleToMousePosition(circlesList.get(circlesList.size() - 1));
    }

    /**
     * When circles dragged, arcs have to be recomputed.
     *
     * @param circlesList List of circles
     */
    public static void prepareCirclesWhenDragged(List<CircleWithArcs> circlesList) {
        initiateCircles(circlesList);
    }

    /**
     * Set initial state for every circle {@link CircleWithArcs}
     *
     * @param circlesList List of circles
     */
    public static void initiateCircles(List<CircleWithArcs> circlesList) {
        for (CircleWithArcs circle : circlesList) {
            circle.setArcsList(new ArrayList<>());
            circle.setArcs2D(new ArrayList<>());
            circle.setExcluded(false);
        }
    }

    /**
     * Locate circle to a mouse cursor position
     *
     * @param circle to locate to mouse position
     */
    public static void setCircleToMousePosition(CircleWithArcs circle) {
        PointerInfo a = MouseInfo.getPointerInfo();
        Point b = a.getLocation();
        circle.setX((int) b.getX());
        circle.setY((int) b.getY());
    }
}
