package circlesintersection.utils;

import circlesintersection.models.CircleWithArcs;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static circlesintersection.utils.geometry.GeometryUtils.computeDistance;

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
    public static void prepareCirclesWhenRotatedOrScaled(List<CircleWithArcs> circlesList) {
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

    /**
     * Exclude circles that are placed inside any other circles from a list of regarded circles
     *
     * @param circlesList list of {@link CircleWithArcs}
     */
    public static void excludeInnerCircles(List<CircleWithArcs> circlesList) {
        /*
         * This O(n^2) loop checks if there is any circle that thoroughly immersed
         * inside any of the other circles and marks it as an excluded from
         * being treated as valid for checking an intersection with it
         */
        for (int i = 0; i < circlesList.size(); i++) {
            for (int j = 0; j < circlesList.size(); j++) {
                if (i != j) {
                    excludeCircleIfInsideAnotherOne(i, j, circlesList);
                }
            }
        }
    }

    /**
     * Exclude circle if it is located inside the other one.
     *
     * @param circleIndex1 index of a first circle
     * @param circleIndex2 index of a second circle
     * @param circlesList  array of {@link CircleWithArcs}
     */
    private static void excludeCircleIfInsideAnotherOne(int circleIndex1, int circleIndex2, List<CircleWithArcs> circlesList) {
        /*
         * If a half of a diameter of circle [circleIndex1] is larger than
         * half of a diameter of circle [circleIndex2] + computeDistance among these
         * circles, then it means that a circle [circleIndex2] is immersed
         * into (or placed inside) of a circle [circleIndex1]. If that is so,
         * then circle [circleIndex2] is marked "Excluded". It also means that
         * this circle will not be regarded as the one to be checked
         * an intersections with.
         */
        if (circlesList.get(circleIndex1).getDiameter() / 2 >
                computeDistance(
                        circlesList.get(circleIndex1).getX(), circlesList.get(circleIndex1).getY(),
                        circlesList.get(circleIndex2).getX(), circlesList.get(circleIndex2).getY())
                        + circlesList.get(circleIndex2).getDiameter() / 2) {
            circlesList.get(circleIndex2).setExcluded(true);
        }
    }
}
