package circlesintersection.computation.geometry;

import circlesintersection.IllegalInstantiationException;
import circlesintersection.models.Arc;
import circlesintersection.models.CircleWithArcs;
import circlesintersection.utils.CirclesLogger;

import java.util.List;

import static circlesintersection.presentation.GraphicsSettings.DEBUG_ENABLED;
import static circlesintersection.computation.ArcsUtils.*;
import static circlesintersection.computation.CircleUtils.excludeInnerCircles;
import static circlesintersection.computation.geometry.CirclesGeometryUtils.convertAnglesRadToGrad;
import static circlesintersection.computation.geometry.GeometryUtils.*;

/**
 * Helper methods related to computing intersections of circles list ({@link List<CircleWithArcs>})
 */
public class IntersectionUtils {

    // Draw circles that do not intersect with any other circles with a solid line, not a dashed one.
    private static final boolean DRAW_NOT_INTERSECTED_SOLID = true;

    private IntersectionUtils() {
        throw new IllegalInstantiationException("IntersectionUtils should not be instantiated");
    }

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

        boolean intersected;

        excludeInnerCircles(circlesList);

        // Passing through all the circles
        for (int i = 0; i < circlesList.size(); i++) {
            intersected = false;
            // If circle(arc) is not excluded
            if (!circlesList.get(i).isExcluded()) {
                // Passing through all the circles
                intersected = checkIfCircleIsIntersectedWithOthers(circlesList, intersected, i);
            }

            /*
             * If a circle doesn't intersect with any other, then make it to be a circle, not an arc, i.e. make its
             * angles to be [0;360] (full circle).
             */
            if (!intersected
                    && DRAW_NOT_INTERSECTED_SOLID) {
                circlesList.get(i).addArc(
                        getNotIntersectedCircle(circlesList.get(i))
                );
            }
        }

        if (DEBUG_ENABLED) {
            CirclesLogger.printArcs(circlesList);
        }
        sortArcs(circlesList);
        if (DEBUG_ENABLED) {
            CirclesLogger.printArcs(circlesList);
        }
    }

    private static boolean checkIfCircleIsIntersectedWithOthers(List<CircleWithArcs> circlesList, boolean intersected, int i) {
        double distance;
        for (int j = 0; j < circlesList.size(); j++) {
            if (i != j && !circlesList.get(j).isExcluded()) {
                distance = computeDistance(
                        circlesList.get(i).getX(), circlesList.get(i).getY(),
                        circlesList.get(j).getX(), circlesList.get(j).getY());
                intersected = createArcsAndMarkIntersected(circlesList, distance, i, j);
            }
        }
        return intersected;
    }

    private static boolean createArcsAndMarkIntersected(List<CircleWithArcs> circlesList, double distance, int i, int j) {
        /*
         * If a sum of radii of two regarded circles less
         * than distance among them, i.e. if a circles intersect
         */
        if ((circlesList.get(i).getDiameter() / 2 + circlesList.get(j).getDiameter() / 2 > distance)) {
            Arc arc = convertAnglesRadToGrad(
                    circlesList.get(i),
                    circlesList.get(j),
                    distance);
            createAndAddRefinedArcs(circlesList.get(i).getArcsList(), arc);
            return true;
        }
        return false;
    }
}
