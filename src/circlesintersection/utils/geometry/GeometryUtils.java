package circlesintersection.utils.geometry;

import circlesintersection.models.AngleKind;
import circlesintersection.models.CircleWithArcs;

import java.util.List;

import static circlesintersection.utils.CircleUtils.prepareCirclesWhenRotated;

/**
 * Geometry utility class.
 */
public final class GeometryUtils {

    private GeometryUtils() {
        throw new RuntimeException("Utils class should not be instantiated");
    }

    /**
     * Convert radian value to grad.
     *
     * @param radValue radian representation
     * @return grad value
     */
    public static double convertRadToGrad(double radValue) {
        return (radValue / Math.PI * 180);
    }

    /**
     * Convert grad value to radian.
     *
     * @param gradValue grad representation
     * @return radian value
     */
    public static double convertGradToRad(double gradValue) {
        return (gradValue / 180 * Math.PI);
    }

    /**
     * @param x  first ordinate of object to compute a distance from.
     * @param y  second ordinate of object to compute a distance from.
     * @param x2 first ordinate of object to compute a distance to.
     * @param y2 second ordinate of object to compute a distance to.
     * @return distance between object by given coordinates
     */
    public static double computeDistance(double x, double y, double x2, double y2) {
        return Math.sqrt(Math.pow(x - x2, 2)
                + Math.pow(y - y2, 2));
    }

    /**
     * @param circle    Center coordinate that angle is compute relatively to
     * @param circle2   Coordinate that angle is computed for
     * @param angleKind Radian or Grad angle kind
     * @return angle between the centers of a circles.
     */
    public static double computeAngle(CircleWithArcs circle, CircleWithArcs circle2, AngleKind angleKind) {
        double angle;
        double deltaX = circle2.getX() - circle.getX();
        double deltaY = circle2.getY() - circle.getY();
        angle = Math.atan(Math.abs(deltaY) / Math.abs(deltaX));
        // Adjusting the angle
        if (deltaX < 0 && deltaY < 0) {
            angle = -angle + Math.PI;
        }
        if (deltaX < 0 && deltaY > 0) {
            angle = angle + Math.PI;
        }
        if (deltaX > 0 && deltaY > 0) {
            angle = -angle + Math.PI * 2;
        }
        if (deltaX == 0 && deltaY > 0) {
            angle = Math.PI * 1.5;
        }
        if (deltaX == 0 && deltaY < 0) {
            angle = Math.PI / 2;
        }
        if (deltaX < 0 && deltaY == 0) {
            angle = Math.PI;
        }
        if (deltaX > 0 && deltaY == 0) {
            angle = 0;
        }
        return (angleKind == AngleKind.RAD) ? angle : angle * 180 / Math.PI;
    }

    /**
     * Compute dekart coordinates for arcs(circles)
     *
     * @param distance       between a selected circle and a considered circle
     * @param angle          between a selected circle and a considered circle
     * @param circle         considered circle
     * @param circleSelected selected circle
     */
    public static void computeAndSetDekartCoordinates(double distance,
                                                      double angle,
                                                      CircleWithArcs circle,
                                                      CircleWithArcs circleSelected) {
        circle.setX(circleSelected.getX() + Math.cos(angle) * distance);
        circle.setY(circleSelected.getY() - Math.sin(angle) * distance);
    }
}
