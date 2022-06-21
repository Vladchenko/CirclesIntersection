package circlesintersection.utils.geometry;

import circlesintersection.models.AngleKind;
import circlesintersection.models.Arc;
import circlesintersection.models.CircleWithArcs;

import java.util.List;

import static circlesintersection.utils.CircleUtils.prepareCirclesWhenRotated;
import static circlesintersection.utils.geometry.GeometryUtils.*;

/**
 * Circles geometry utils methods, such as scaling, rotating, diameter changing.
 */
public class CirclesGeometryUtils {

    private CirclesGeometryUtils() {
        throw new RuntimeException("Utils class should not be instantiated");
    }

    /**
     * Scale circles, relatively to last one in a list.
     *
     * @param circlesList        List of circles with arcs
     * @param wheelRotationValue mouse wheel moved value
     */
    public static void scaleCircles(List<CircleWithArcs> circlesList, int wheelRotationValue) {
        double newAngle;
        double distance;
        CircleWithArcs lastCircle = circlesList.get(circlesList.size() - 1);
        for (int i = 0; i < circlesList.size() - 1; i++) {
            circlesList.get(i).setExcluded(false);
            newAngle = computeAngle(lastCircle, circlesList.get(i), AngleKind.RAD);
            distance = computeDistance(
                    circlesList.get(i).getX(), circlesList.get(i).getY(),
                    lastCircle.getX(), lastCircle.getY());
            if (wheelRotationValue > 0) {
                distance += distance / 15;
            } else {
                distance -= distance / 15;
            }
            computeAndSetDekartCoordinates(
                    distance, newAngle,
                    circlesList.get(i),
                    lastCircle);
        }
    }

    /**
     * Rotate relatively to last one in a list.
     *
     * @param circlesList        List of circles with arcs
     * @param wheelRotationValue mouse wheel moved value
     */
    public static void rotateCircles(List<CircleWithArcs> circlesList, int wheelRotationValue) {
        double newAngle;
        double extraAngle;
        double distance;
        prepareCirclesWhenRotated(circlesList);
        if (wheelRotationValue > 0) {
            extraAngle = 0.04;
        } else {
            extraAngle = -0.04;
        }
        CircleWithArcs lastCircle = circlesList.get(circlesList.size() - 1);
        for (int i = 0; i < circlesList.size() - 1; i++) {
            newAngle = computeAngle(lastCircle, circlesList.get(i), AngleKind.RAD) + extraAngle;
            distance = computeDistance(
                    circlesList.get(i).getX(), circlesList.get(i).getY(),
                    lastCircle.getX(), lastCircle.getY());
            computeAndSetDekartCoordinates(
                    distance, newAngle,
                    circlesList.get(i),
                    lastCircle
            );
        }
    }

    /**
     * Change circle's diameter, depending on a wheel rotation value.
     *
     * @param circle             to change diameter of
     * @param wheelRotationValue value for mouse wheel rotation
     */
    public static void changeCircleDiameter(CircleWithArcs circle, int wheelRotationValue) {
        if (wheelRotationValue < 0
                && circle.getDiameter() > 20) {
            circle.setDiameter(
                    circle.getDiameter() - 10);
        }
        if (wheelRotationValue > 0) {
            circle.setDiameter(
                    circle.getDiameter() + 10);
        }
    }

    /**
     * Convert a beginning and ending angles of an intersected circles from RAD to GRAD and put them to {@link Arc}.
     *
     * @param arc model to keep computed angles
     * @param circle    circle(arc) #1
     * @param circle2   circle(arc) #2 to compute
     * @param distance  between circles(arcs)
     */
    public static void convertAnglesRadToGrad(Arc arc, CircleWithArcs circle, CircleWithArcs circle2, double distance) {
        double angle = computeAngle(circle, circle2, AngleKind.RAD);
        double l1 = (Math.pow(circle.getDiameter() / 2, 2) - Math.pow(circle2.getDiameter() / 2, 2) + distance * distance) / (2 * distance);
        double alpha = Math.acos(l1 / (circle.getDiameter() / 2));
        double beta = angle - alpha;
        double gamma = angle + alpha;
        if (angle + alpha >= Math.PI * 2) {
            gamma -= Math.PI * 2;
        }
        arc.setAngleBegin(convertRadToGrad(beta));
        arc.setAngleEnd(convertRadToGrad(gamma));
    }
}
