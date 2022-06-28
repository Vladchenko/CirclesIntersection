package circlesintersection.utils;

import circlesintersection.models.Arc;
import circlesintersection.models.CircleWithArcs;

import java.awt.geom.Arc2D;
import java.util.List;

/**
 * Provides logging methods for debug.
 */
public final class Logger {

    /**
     * Private constructor, since no instance
     */
    private Logger() {
        throw new RuntimeException("Utils class should not be instantiated");
    }

    /**
     * Prints arcs of every circle to console.
     *
     * @param circleWithArcsList list of circles that consists list of intersections (arcs).
     */
    public static void printArcs(List<CircleWithArcs> circleWithArcsList) {
        System.out.println("All arcs for all circles: ");
        for (int i = 0; i < circleWithArcsList.size(); i++) {
            System.out.print(i + ":  ");
            for (Arc arc : circleWithArcsList.get(i).getArcsList()) {
                System.out.format("[%.2f:%.2f] ", arc.getAngleBegin(), arc.getAngleEnd());
            }
            System.out.println();
        }
    }

    /**
     * Prints resulting list of arcs ({@link Arc2D} list) to console.
     *
     * @param circleWithArcsList list of circles that consists list of intersections (arcs).
     */
    public static void printArcs2DAngles(List<CircleWithArcs> circleWithArcsList) {
        System.out.println("All Arc2D for all circles: ");
        for (int i = 0; i < circleWithArcsList.size(); i++) {
            System.out.print(i + ":  ");
            for (Arc2D arc2D : circleWithArcsList.get(i).getArcs2D()) {
                System.out.format("[%.2f:%.2f] ", arc2D.getAngleStart() , arc2D.getAngleExtent());
            }
            System.out.println();
        }
    }
}
