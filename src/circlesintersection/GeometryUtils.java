package circlesintersection;

import circlesintersection.models.Arc;

/**
 * Geometry utility class.
 */
public final class GeometryUtils {

    private GeometryUtils() {
        throw new RuntimeException("Utils class cannot be instantiated");
    }

    /**
     * Convert radian value to grad.
     *
     * @param value radian representation
     * @return grad value
     */
    static double convertRadToGrad(double value) {
        return (value / Math.PI * 180);
    }

    /**
     * Convert grad value to radian.
     *
     * @param value grad representation
     * @return radian value
     */
    static double convertGradToRad(double value) {
        return (value / 180 * Math.PI);
    }

    /**
     * @param arc   {@link Arc} to compute a distance from.
     * @param arc2  {@link Arc} to compute a distance to.
     *
     * @return distance between arcs
     */
    static double computeDistance(Arc arc, Arc arc2) {
        return Math.sqrt(Math.pow(arc.getX() - arc2.getX(), 2)
                + Math.pow(arc.getY() - arc2.getY(), 2));
    }

    /**
     * @param arc   Center coordinate that angle is compute relatively to
     * @param arc2  Coordinate that angle is computed for
     * @param angleKind Radian or Grad angle kind
     *
     * @return compute an angle between the centers of a circles. arc stands for a central dot.
     */
    static double computeAngle(Arc arc, Arc arc2, AngleKind angleKind) {
        double angle;
        double deltaX = arc2.getX() - arc.getX();
        double deltaY = arc2.getY() - arc.getY();
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
     * @param distance  between a selected arc and a considered arc
     * @param angle     between a selected arc and a considered arc
     * @param arc       considered arc
     * @param arcSelected   selected arc
     */
    static void computeAndSetDekartCoordinates(double distance, double angle, Arc arc, Arc arcSelected) {
        arc.setX(arcSelected.getX() + Math.cos(angle) * distance);
        arc.setY(arcSelected.getY() - Math.sin(angle) * distance);
    }
}
