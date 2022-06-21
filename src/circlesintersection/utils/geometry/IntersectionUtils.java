package circlesintersection.utils.geometry;

import circlesintersection.Settings;
import circlesintersection.models.Arc;
import circlesintersection.models.CircleWithArcs;
import circlesintersection.utils.Logger;

import java.util.List;

import static circlesintersection.Settings.DEBUG_ENABLED;
import static circlesintersection.utils.ArcsUtils.*;
import static circlesintersection.utils.geometry.CirclesGeometryUtils.convertAnglesRadToGrad;
import static circlesintersection.utils.geometry.GeometryUtils.*;

/**
 * Helper methods related to computing intersections of circles list ({@link List<CircleWithArcs>})
 */
public class IntersectionUtils {

    /**
     * Perform required computations to draw an arcs.
     *
     * @param circlesList List of circles
     * @param deltaX      Some shift of x ordinate (used to perform mouse dragging)
     * @param deltaY      Some shift of y ordinate (used to perform mouse dragging)
     */
    public static void computeIntersections(List<CircleWithArcs> circlesList, int deltaX, int deltaY) {
        checkIntersection(circlesList);
        removeRedundantArcs(circlesList);
        invertArcsAndCreateArcs2D(circlesList, deltaX, deltaY);
    }

    /**
     * Check if circles intersect with each other and creates a lists of arcs that reside in areas different to the
     * intersections.
     *
     * @param circlesList List of circles
     */
    private static void checkIntersection(List<CircleWithArcs> circlesList) {

        double distance;
        boolean intersected;

        excludeInnerCircles(circlesList);

        /*
         * Adding a new pair of angles that arc is to be drawn by, i.e.
         * [20,50] - that means an arc is to be drawn from 20 to 50 degrees.
         */
        Arc arc = new Arc();

        // Passing through all the circles
        for (int i = 0; i < circlesList.size(); i++) {
            intersected = false;
            // If circle(arc) is not excluded
            if (!circlesList.get(i).isExcluded()) {
                // Passing through all the circles
                for (int j = 0; j < circlesList.size(); j++) {
                    if (i != j && !circlesList.get(j).isExcluded()) {
                        distance = computeDistance(
                                circlesList.get(i).getX(), circlesList.get(i).getY(),
                                circlesList.get(j).getX(), circlesList.get(j).getY());
                        intersected = createArcsAndMarkIntersected(circlesList, distance, arc, i, j);
                    }
                }
            }

            /*
             * If a circle doesn't intersect with any other, then make it to be a circle, not an arc, i.e. make its
             * angles to be [0;360] (full circle).
             */
            if (!intersected
                    && Settings.DRAW_NOT_INTERSECTED_SOLID) {
                circlesList.get(i).addArc(
                        getNotIntersectedCircle(arc, circlesList.get(i))
                );
                arc = new Arc();
            }
        }

        if (DEBUG_ENABLED) {
            Logger.printArcs(circlesList);
        }
        sortArcs(circlesList);
        if (DEBUG_ENABLED) {
            Logger.printArcs(circlesList);
        }
    }

    private static boolean createArcsAndMarkIntersected(List<CircleWithArcs> circlesList, double distance, Arc arc, int i, int j) {
        /*
         * If a sum of radii of two regarded circles less
         * than distance among them, i.e. if a circles intersect
         */
        if ((circlesList.get(i).getDiameter() / 2 + circlesList.get(j).getDiameter() / 2 > distance)) {
            convertAnglesRadToGrad(arc,
                    circlesList.get(i),
                    circlesList.get(j),
                    distance);
            createAndAddRefinedArcs(circlesList.get(i).getArcsList(), arc);
            return true;
        }
        return false;
    }

    /**
     * Exclude circles that are placed inside any other circles from a list of regarded circles
     *
     * @param circlesList list of {@link CircleWithArcs}
     */
    private static void excludeInnerCircles(List<CircleWithArcs> circlesList) {
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
